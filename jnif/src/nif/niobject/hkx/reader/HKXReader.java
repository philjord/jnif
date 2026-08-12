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

				hkBaseObject obj = constructHKXObject(className, header.is64bit);				
				
				// the following skyrim hkp objects don't have xml to decode, they only appear in skeleton.hkx
				// I only need the bones mappings from a skeleton.hkx so I'm going to hope to ignore them
				// these look like the bhk versions found in nif files
				
				//hkpRigidBody hkpPhysicsSystem hkpPhysicsData hkpShapeInfo hkpCapsuleShape
				
				// Check for an unknown block type
				if (obj == null) {
					System.out.println("Unknown object type encountered during file read:  " + className);
					
					// when we skip a load we also need to skip whatever the data1 pointer thingies are 
					// up to one that's from is on or after the next object					
					// but cos it is hard I'm going to just stop loading and pretend it's fine
					
					break;
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

	private static hkBaseObject constructHKXObject(String objectType, boolean is64Bit) {
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
	 * Note this takes an input ByteBuffer, gets the 4 bytes at indexes 8,9,10,11 and reads them as an int
	 * It does this all on the stack so no speed issues
	 * @param arrayBaseBytes
	 * @return
	 */
	public static int getSizeComponent(ByteBuffer file) {
		// was in efficient 
		//byte[] sizeSpecificBytes = new byte[] {arrayBaseBytes[8], arrayBaseBytes[9], arrayBaseBytes[10],
		//	arrayBaseBytes[11]};
		//return ByteUtils.getUInt(sizeSpecificBytes);

		//see ULongByteUtils.getLong (notice 4 and +8 inserted)

		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		final int len = 4;
		int accu = 1;
		int res = 0;
		for (int i = 0; i < len; i++) {
			res += (baseArrayBytes[i + 8] & 0xFF) * accu;
			accu *= 256;
		}
		return res;

	}

	/**
	 *  helper for string pointers
	 *  64bit has long pointer at 8bits and 32bit has dword pointers at 4 bits
	 * @param connector
	 * @param classOffset
	 * @return
	 */

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

	/**
	 *  helper for array of string pointers
	 * @param connector
	 * @param classOffset
	 * @return
	 */
	public static String[] hkStringArray(HKXReaderConnector connector, int classOffset) {
		try {
			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset));
			if (arrSize > 0) {	
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset;
				String[] ret = new String[arrSize];
				for (int i = 0; i < arrSize; i++) {
					try {
						arrValue = connector.data1.readNext();
						//assert arrValue.from == classOffset; this can match at like +4 on the first .to
						ByteBuffer file2 = connector.data.setup(arrValue.to);
						ret[i] = ByteUtils.readString(file2);						 
					} catch (InvalidPositionException e) {
						// NO OP. Met when the last item of the HKX file is a String and is empty.					
					}
				}
				return ret;
			}
		} catch (InvalidPositionException e) {
			// not sure if possible
		}
		return null;
	}

	/**
	 * 64bit has long pointer at 8bits and 32bit has dword pointers at 4 bits
	 * @param connector
	 * @param classOffset
	 * @return
	 * @throws InvalidPositionException
	 */
	public static long getPointer(HKXReaderConnector connector, long classOffset) throws InvalidPositionException {
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset) {
			return data.to;
		} else {
			connector.data2.backtrack();
		}
		return -1;
	}

	/**
	 * Note this takes an input array, gets the 4 bytes at indexes 4,5,6,7 and reads them as an int
	 * It does this all on the stack so no speed issues
	 * @param arrayBaseBytes
	 * @return
	 */
	public static int getSizeComponent32(ByteBuffer bb) {
		byte[] arrayBaseBytes = new byte[12];
		bb.get(arrayBaseBytes);

		//see ULongByteUtils.getLong (notice 4 and + 4 inserted)
		final int len = 4;
		int accu = 1;
		int res = 0;
		for (int i = 0; i < len; i++) {
			res += (arrayBaseBytes[i + 4] & 0xFF) * accu;// not normal 4 not 8
			accu *= 256;
		}
		return res;
	}

	/**
	 *  helper for array of string pointers
	 * @param connector
	 * @param classOffset
	 * @return
	 */
	public static String[] hkStringArray32(HKXReaderConnector connector, int classOffset) {
		try {
			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset));
			if (arrSize > 0) {	
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset;
				String[] ret = new String[arrSize];
				for (int i = 0; i < arrSize; i++) {
					try {
						arrValue = connector.data1.readNext();
						//assert arrValue.from == classOffset; this can match at like +4 on the first .to
						ByteBuffer file2 = connector.data.setup(arrValue.to);
						ret[i] = ByteUtils.readString(file2);						 
					} catch (InvalidPositionException e) {
						// NO OP. Met when the last item of the HKX file is a String and is empty.					
					}
				}
				return ret;
			}
		} catch (InvalidPositionException e) {
			// not sure if possible
		}
		return null;
	}
}
