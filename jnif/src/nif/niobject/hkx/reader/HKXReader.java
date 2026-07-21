package nif.niobject.hkx.reader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

import nif.niobject.hkx.hkBaseObject;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**
 * Reads the content of a {@link File} or {@link ByteBuffer}, containing

 */
public class HKXReader {
	private final transient ByteBuffer hkxBB;

	/**
	 * Creates a {@link HKXReader}.
	 * 
	 * @param hkxByteBuffer     the {@link ByteBuffer} to read data from.
	
	 */
	public HKXReader(final ByteBuffer hkxByteBuffer) {
		this.hkxBB = hkxByteBuffer;
	}

	/**
	 * Read data from this {@link HKXReader}'s {@link File} or {@link ByteBuffer}.
	 * 
	
	 * @throws IOException              if there was a problem accessing the file.
	 * @throws InvalidPositionException if there was a positioning problem while
	 *                                  reading the file.
	 */
	public HKXContents read() throws IOException, InvalidPositionException {

		// Connect the connector to the file.
		HKXReaderConnector connector = new HKXReaderConnector(hkxBB);

		// unsupportedd file format :(
		if (connector.header == null)
			return null;

		// Retrieve useful data and interfaces from the header
		Data3Interface data3 = connector.data3;

		int pos = 0;

		// Create additional connectors.
		HeaderData header = connector.header;
		ClassnamesData classConverter = connector.classnamesdata;

		// Create the return object
		HKXContents content = new HKXContents(header);

		// note all calls must be absolute to allow any multi-thread, so the "from" relative position pointer is unuseful
		// also many slice operations just make for memory burn nightmare
		// notice we must slice to embed the dateHeader.offset into the position of this buffer
		ByteBuffer stream = connector.data.setup(0).slice().order(ByteOrder.LITTLE_ENDIAN);

		// Retrieve the actual data
		while (data3.hasReadPos(pos)) {
			// Get the next data3 object
			DataExternal currentClass = data3.read(pos++);

			// Resolve the object's class into a HKXDescriptor
			Classname classObj = classConverter.get(currentClass.to);
			if (classObj == null) {
				throw new IOException("Illegal linked Classname position (" + currentClass.from + "//" + currentClass.to
										+ "). Ignoring.");
			} else {
				String className = classObj.name;

				//NOTE! it is very bad not loading things, as the fixup section doesn't get moved along like it should
				if (className.equals("none") //
				) {
					System.out.println("skipped " + className);
					continue;
				}

				//System.out.println("is64bit "+hkxContents.getHeaderData().is64bit);// if false only use the skyrim folder
				hkBaseObject obj = constructHKXObject(className);

				// Check for an unknown block type
				if (obj == null) {
					System.out.println("Unknown object type encountered during file read:  " + className);
					continue;
				}

				//String objectName = generator.get(currentClass.from);				
				boolean success = obj.readFromStream(connector, stream, (int)currentClass.from);

				if (!success) {
					new Throwable("bum read").printStackTrace();
				}

				content.add(currentClass.from, obj);
			}
		}

		return content;

	}

	private static HashMap<String, Constructor<?>>	typeToClass	= new HashMap<String, Constructor<?>>();

	private static Object[]							noArgs		= new Object[] {};

	private static hkBaseObject constructHKXObject(String objectType) {
		if (objectType == null || objectType.length() == 0) {
			System.out.println("Bad objectType [" + objectType + "]");
			return null;
		}

		// FO4 has introduced inner class names, so swap marker
		objectType = objectType.replace("::", "$");

		// let's see if we've got it already shall we?

		Constructor<?> preCons = typeToClass.get(objectType);
		if (preCons != null) {
			try {
				Object obj = preCons.newInstance(noArgs);
				return (hkBaseObject)obj;
			} catch (Exception e) {
				e.printStackTrace();
				// bummer just fail out then
			}
		} else {
			try {
				Class<?> newClass = Class.forName("nif.niobject.hkx." + objectType);
				Constructor<?> cons = newClass.getConstructors()[0];
				typeToClass.put(objectType, cons);
				Object obj = cons.newInstance(noArgs);
				return (hkBaseObject)obj;
			} catch (Exception e3) {
				try {
					Class<?> newClass = Class.forName("nif.niobject.hkx.animation." + objectType);
					Constructor<?> cons = newClass.getConstructors()[0];
					typeToClass.put(objectType, cons);
					Object obj = cons.newInstance(noArgs);
					return (hkBaseObject)obj;
				} catch (Exception e4) {
					/*try {
						Class<?> newClass = Class.forName("nif.niobject.particle." + objectType);
						Constructor<?> cons = newClass.getConstructors()[0];
						typeToClass.put(objectType, cons);
						Object obj = cons.newInstance(noArgs);
						return (NiObject)obj;
					} catch (Exception e5) {
					etc see NifFileReader
					*/

					// ok give up
					System.out.println("class for objectType " + objectType + " not found");
					System.out.print("Searched in these locations: ");
					System.out.print("nif.niobject.hkx." + objectType + ", ");
					System.out.print("nif.niobject.hkx.animation." + objectType + " ");
					System.out.println("");
				}
			}
		}
		System.out.println("unknown block type " + objectType);
		return null;
	}

	/**
	 * Note this takes an input array, gets the 4 bytes at indexes 8,9,10,11 and reads them as an int
	 * It does this all on the stack so no speed issues
	 * @param arrayBaseBytes
	 * @return
	 */
	public static int getSizeComponent(final byte[] arrayBaseBytes) {
		// was in efficient 
		//byte[] sizeSpecificBytes = new byte[] {arrayBaseBytes[8], arrayBaseBytes[9], arrayBaseBytes[10],
		//	arrayBaseBytes[11]};
		//return ByteUtils.getUInt(sizeSpecificBytes);

		//see ULongByteUtils.getLong (notice 4 and +8 inserted)
		final int len = 4;
		int accu = 1;
		int res = 0;
		for (int i = 0; i < len; i++) {
			res += (arrayBaseBytes[i + 8] & 0xFF) * accu;
			accu *= 256;
		}
		return res;

	}

	// util for array size getting
	// offset should be classOffset+something
	protected static int getArraySize(HKXReaderConnector connector, int offset) throws InvalidPositionException {
		ByteBuffer stream = connector.data.setup(offset);
		byte[] sizeSpecificBytes = new byte[4];
		for (int i = 0; i < sizeSpecificBytes.length; i++)
			sizeSpecificBytes[i] = stream.get(offset + 8 + i);//8,9,10,11 are an uint of array size		 
		return ByteUtils.getUInt(sizeSpecificBytes);
		// SEE return HKXReader.getSizeComponent(baseArrayBytes);
	}

	// helper for string pointers
	public static String hkStringPtr(HKXReaderConnector connector, int classOffset) {
		String ret = "";
		try {
			DataInternal di = connector.data1.readNext();
			if (di.from == classOffset) {
				ByteBuffer file = connector.data.setup(di.to);
				ret = ByteUtils.readString(file);
			} else {
				connector.data1.backtrack();
			}
		} catch (InvalidPositionException e) {
			// NO OP. Met when the last item of the HKX file is a String and is empty.
			ret = "";
		}
		return ret;
	}

	// helper for array of string pointers
	public static String[] hkStringArray(HKXReaderConnector connector, int classOffset) {
		try {
			ByteBuffer file = connector.data.setup(classOffset);
			byte[] baseArrayBytes = new byte[0X10];
			file.get(baseArrayBytes);
			int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
			if (arrSize > 0) {
				Data1Interface data1 = connector.data1;
				DataInternal arrValue = data1.readNext();
				assert arrValue.from == classOffset;
				String[] ret = new String[arrSize];
				for (int i = 0; i < arrSize; i++) {
					try {
						ByteBuffer file2 = connector.data.setup(arrValue.to);
						ret[i] = ByteUtils.readString(file2);
					} catch (InvalidPositionException e) {
						// NO OP. Met when the last item of the HKX file is a String and is empty.
						ret[i] = "";
					}
					// example of this working is:
					//ret[i] = new String(connector, stream, (int)arrValue.to + (i*hknpMaterial.size));
				}
				return ret;
			}
		} catch (InvalidPositionException e) {
			// not sure if possible
		}
		return null;
	}

	public static long getPointer(HKXReaderConnector connector, long classOffset) throws InvalidPositionException {
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset) {
			return data.to;
		} else {
			connector.data2.backtrack();
		}
		return -1;
	}
}
