package nif.niobject.hkx.reader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;

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

		// Retrieve useful data and interfaces from the header
		Data3Interface data3 = connector.data3;

		int pos = 0;

		// Create additional connectors.
		HeaderData header = connector.header;
		ClassnamesData classConverter = connector.classnamesdata;

		// Create the return object
		HKXContents content = new HKXContents(header.versionName, header.version);
		
		// Retrieve the actual data
		try {
			while (true) {
				// Get the next data3 object
				DataExternal currentClass = data3.read(pos++);

				// Resolve the object's class into a HKXDescriptor
				Classname classObj = classConverter.get(currentClass.to);
				if (classObj == null) {
					throw new IOException("Illegal linked Classname position (" + currentClass.from + "//"
							+ currentClass.to + "). Ignoring.");
				} else {
					String className = classObj.name;

					
					//2 super complex chaps that might be a bit much for now
					if(className.equals("hkpLimitedHingeConstraintData") || 
							className.equals("hkpRagdollConstraintData")) continue;
					
					hkBaseObject obj = constructHKXObject(className);

					// Check for an unknown block type
					if (obj == null) {
						System.out.println("Unknown object type encountered during file read:  "	+ className 	 ); 
						continue;
					}

					//String objectName = generator.get(currentClass.from);
					boolean success = obj.readFromStream(connector, (int)currentClass.from);	
					
					if(!success) {
						new Throwable("bum read").printStackTrace();
					}
					
					content.add(currentClass.from, obj);					
				}
			}
		} catch (InvalidPositionException e) {
			if (!e.getSection().equals("DATA_3")) {
				throw e;
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
				/*try {
					Class<?> newClass = Class.forName("nif.niobject.bs." + objectType);
					Constructor<?> cons = newClass.getConstructors()[0];
					typeToClass.put(objectType, cons);
					Object obj = cons.newInstance(noArgs);
					return (NiObject)obj;
				} catch (Exception e4) {
					try {
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
					System.out.println("Searched in these directories: ");
					System.out.println("nif.niobject.hkx." + objectType);							
				}
				
		}
		System.out.println("unknown block type " + objectType);
		return null;
	}
	
	public static int getSizeComponent(final byte[] arrayBaseBytes) {
		byte[] sizeSpecificBytes = new byte[] { arrayBaseBytes[8], arrayBaseBytes[9], arrayBaseBytes[10],
				arrayBaseBytes[11] };
		return ByteUtils.getUInt(sizeSpecificBytes);
	}
}
