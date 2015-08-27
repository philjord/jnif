package nif;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.NifFooter;
import nif.compound.NifHeader;
import nif.niobject.NiNode;
import nif.niobject.NiObject;
import nif.niobject.controller.NiObjectNET;

public class NifFileReader
{

	public static boolean IS_DEBUG = true;

	/**
	 * NON caching!
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static NifFile readNif(File file) throws IOException
	{
		BufferedInputStream nifIn = new BufferedInputStream(new FileInputStream(file));

		NifFile nifFile = readNif(file.getCanonicalPath(), nifIn);
		nifIn.close();
		return nifFile;
	}

	/** Reads the given input stream and returns a vector of block references	
	 * NON caching!
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static synchronized NifFile readNif(String fileName, InputStream inStr) throws IOException
	{
		ProgressInputStream in = new ProgressInputStream(inStr);

		NifHeader header = new NifHeader();

		boolean goodHeader = header.readFromStream(in);
		if (!goodHeader)
		{
			System.out.println("could not load file " + fileName + " due to bad header");
			return null;
		}
		if (IS_DEBUG)
		{
			System.out.println("fileName " + fileName);
			System.out.println("Header " + header);
		}

		NifVer nifVer = new NifVer(fileName, header.version, header.userVersion, header.userVersion2);

		if (nifVer.LOAD_VER < NifVer.VER_4_0_0_2)
		{
			System.out.println("NIF VERSION TOO LOW!! < NifVer.VER_4_0_0_2 " + nifVer.LOAD_VER + "  min = " + NifVer.VER_4_0_0_2);
			return null;
		}

		//set the byteconvert string list static if required for index strings
		if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			nifVer.indexStrings = header.strings;
		}

		int numBlocks = header.numBlocks;
		boolean unknownBlocksFound = false;

		NiObject[] blocks = new NiObject[numBlocks];
		String objectType = null;
		for (int i = 0; i < numBlocks; i++)
		{
			// There are two ways to read blocks, one before version 5.0.0.1 and one after that, ignore the one before
			if (nifVer.LOAD_VER >= NifVer.VER_5_0_0_1)
			{
				// From version 5.0.0.1 to version 10.0.1.0 there is a zero byte at the begining of each block
				if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_106)
				{
					int checkValue = ByteConvert.readInt(in);
					if (checkValue != 0)
					{
						System.out.println(fileName + " i = " + i + "  previous object type was " + objectType);
						throw new IOException("Read failue - Bad object position  zero byte at the begining of each block ");
					}
				}

				// Find which block type this is by using the header arrays
				objectType = header.blockTypes[header.blockTypeIndex[i]];

			}
			else
			{
				objectType = ByteConvert.readSizedString(in);

				if (header.version < NifVer.VER_3_3_0_13)
				{
					//There can be special commands instead of object names
					//in these versions
					if (objectType == "Top Level Object")
					{
						//Just continue on to the next object
						continue;
					}

					if (objectType == "End Of File")
					{
						//File is finished
						break;
					}
				}

			}

			// Create Block of the type that was found
			if (IS_DEBUG)
				System.out.println("Loading: " + i + " " + objectType);

			NiObject obj = constructNiObject(objectType);

			// Check for an unknown block type
			if (obj == null)
			{
				System.out.println("Unknown object type encountered during file read:  " + objectType + " i = " + i + " in " + fileName);

				unknownBlocksFound = true;
				continue;
			}

			int index = -1;
			if (header.version < NifVer.VER_3_3_0_13)
			{
				//These old versions have a pointer value after the name
				//which is used as the index
				index = ByteConvert.readInt(in);
			}
			else
			{
				//These newer verisons use their position in the file as their index
				index = i;
			}

			blocks[index] = obj;

			try
			{

				// set the positon to the current based on whatever the header read off
				long prevBytePos = in.getBytesRead();

				// mark in case of over read
				in.mark(1000000);
				obj.readFromStream(in, nifVer);

				long bytesReadOff = in.getBytesRead() - prevBytePos;

				// only games after fallout have block sizes
				if (header.blockSizes != null && bytesReadOff != header.blockSizes[i])
				{
					System.out.println("blocks[i].readFromStream for i=" + i + " type= " + header.blockTypes[header.blockTypeIndex[i]]
							+ " should have read off " + header.blockSizes[i] + " but in fact read off " + bytesReadOff);

					int diff = (int) (header.blockSizes[i] - bytesReadOff);
					// let's try desperately to correct
					if (diff > 0)
					{
						in.skip(diff);
					}
					else
					{
						// We've read parts of the next block too, let's reset it and reread what it should have been
						in.reset();
						in.skip(header.blockSizes[i]);
					}
				}

				if (IS_DEBUG && obj instanceof NiObjectNET)
				{
					System.out.println("name: " + ((NiObjectNET) obj).name);
				}
			}
			catch (Exception e)
			{
				System.out.println("Exception occured in file " + fileName);
				e.printStackTrace();
				return null;
			}
			catch (Error err)
			{
				System.out.println("Error whilst loading block num " + i + " in " + fileName);
				throw (err);
			}

		}

		// --Read Footer--//
		NifFooter footer = new NifFooter();
		footer.readFromStream(in);

		// This should fail, and trigger the in.eof() flag
		if (in.read() != -1)
		{
			throw new IOException("End of file not reached.  This NIF may be corrupt or improperly supported.");
		}

		// ******************
		// Now to do checks and fix ups etc

		// set reverse look up for NIAVObject
		for (int i = 0; i < blocks.length; i++)
		{
			NiObject niObject = blocks[i];
			if (niObject instanceof NiNode)
			{
				((NiNode) niObject).setChildToParentPointers(blocks);
			}
		}

		if (!unknownBlocksFound)
		{
			int countOfErrorsReported = 0;// after 10 we'll fail out and return null

			// ensure NPRefs and NPPtrs point to valid objects and correct types
			for (int i = 0; i < NifRef.allRefs.size(); i++)
			{
				NifRef ref = NifRef.allRefs.elementAt(i);
				if (ref.ref > -1 && ref.ref < blocks.length)
				{
					NiObject actualNiObject = blocks[ref.ref];
					if (actualNiObject == null)
					{
						countOfErrorsReported++;
						System.out.println("NifRef ref:" + ref.ref + " is ref.refType = " + ref.refType
								+ " but the blocks[ref.ref] is null (likely bad stream read in block loading); in file " + fileName);
					}
					else if (!ref.refType.isInstance(actualNiObject))
					{
						countOfErrorsReported++;
						System.out.println("NifRef ref:" + ref.ref + " is type " + actualNiObject.getClass() + " but ref.refType = "
								+ ref.refType + "; in file " + fileName);
					}
				}
				else if (ref.ref == -1)
				{
					// no ref
				}
				else
				{
					countOfErrorsReported++;
					System.out.println("bad ref.ref " + ref.ref + " in file " + fileName);
				}

				if (countOfErrorsReported > 10)
					return null;
				// TODO: and make sure they are down the heihrachy only somehow?
			}
			NifRef.allRefs.clear();

			for (int i = 0; i < NifPtr.allPtrs.size(); i++)
			{
				NifPtr ptr = NifPtr.allPtrs.elementAt(i);
				if (ptr.ptr > -1 && ptr.ptr < blocks.length)
				{
					NiObject actualNiObject = blocks[ptr.ptr];
					if (actualNiObject == null)
					{
						countOfErrorsReported++;
						System.out.println("NifPtr ptr:" + ptr.ptr + " is ptr.ptrType = " + ptr.ptrType
								+ " but the blocks[ptr.ptr] is null (likely bad stream read in block loading); in file " + fileName);
					}
					else if (!ptr.ptrType.isInstance(actualNiObject))
					{
						countOfErrorsReported++;
						System.out.println("NifPtr ptr:" + ptr.ptr + " is type " + actualNiObject.getClass() + " but ptr.ptrType = "
								+ ptr.ptrType + "; in file " + fileName);
					}
				}
				else if (ptr.ptr == -1)
				{
					// no ptr
				}
				else
				{
					countOfErrorsReported++;
					System.out.println("bad ptr.ptr " + ptr.ptr + " in file " + fileName);
				}

				if (countOfErrorsReported > 10)
					return null;
			}
			NifPtr.allPtrs.clear();
		}

		return new NifFile(header, new NiObjectList(nifVer, blocks), footer);
	}

	private static HashMap<String, Constructor<?>> typeToClass = new HashMap<String, Constructor<?>>();

	private static Object[] noArgs = new Object[] {};

	/**
	 * @param objectType
	 * @return
	 */
	private static NiObject constructNiObject(String objectType)
	{
		if (objectType == null || objectType.length() == 0)
		{
			System.out.println("Bad objectType [" + objectType + "]");
			return null;
		}
		// let's see if we've got it already shall we?

		Constructor<?> preCons = typeToClass.get(objectType);
		if (preCons != null)
		{
			try
			{
				Object obj = preCons.newInstance(noArgs);
				return (NiObject) obj;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				// bummer just fail out then
			}
		}
		else
		{
			try
			{
				Class<?> newClass = Class.forName("nif.niobject." + objectType);

				Constructor<?> cons = newClass.getConstructors()[0];
				typeToClass.put(objectType, cons);
				Object obj = cons.newInstance(noArgs);
				return (NiObject) obj;
			}
			catch (Exception e)
			{
				try
				{
					Class<?> newClass = Class.forName("nif.niobject.controller." + objectType);

					Constructor<?> cons = newClass.getConstructors()[0];
					typeToClass.put(objectType, cons);
					Object obj = cons.newInstance(noArgs);
					return (NiObject) obj;
				}
				catch (Exception e2)
				{
					try
					{
						Class<?> newClass = Class.forName("nif.niobject.bhk." + objectType);

						Constructor<?> cons = newClass.getConstructors()[0];
						typeToClass.put(objectType, cons);
						Object obj = cons.newInstance(noArgs);
						return (NiObject) obj;
					}
					catch (Exception e3)
					{
						try
						{
							Class<?> newClass = Class.forName("nif.niobject.bs." + objectType);
							Constructor<?> cons = newClass.getConstructors()[0];
							typeToClass.put(objectType, cons);
							Object obj = cons.newInstance(noArgs);
							return (NiObject) obj;
						}
						catch (Exception e4)
						{
							try
							{
								Class<?> newClass = Class.forName("nif.niobject.particle." + objectType);
								Constructor<?> cons = newClass.getConstructors()[0];
								typeToClass.put(objectType, cons);
								Object obj = cons.newInstance(noArgs);
								return (NiObject) obj;
							}
							catch (Exception e5)
							{
								try
								{
									Class<?> newClass = Class.forName("nif.niobject.fx." + objectType);
									Constructor<?> cons = newClass.getConstructors()[0];
									typeToClass.put(objectType, cons);
									Object obj = cons.newInstance(noArgs);
									return (NiObject) obj;
								}
								catch (Exception e6)
								{
									try
									{
										Class<?> newClass = Class.forName("nif.niobject.interpolator." + objectType);
										Constructor<?> cons = newClass.getConstructors()[0];
										typeToClass.put(objectType, cons);
										Object obj = cons.newInstance(noArgs);
										return (NiObject) obj;
									}
									catch (Exception e7)
									{
										//ok give up
										System.out.println("class for objectType " + objectType + " not found");
										System.out.println("Searched in these directories: ");
										System.out.println("nif.niobject." + objectType);
										System.out.println("nif.niobject.bhk." + objectType);
										System.out.println("nif.niobject.bs." + objectType);
										System.out.println("nif.niobject.controller." + objectType);
										System.out.println("nif.niobject.particle." + objectType);
										System.out.println("nif.niobject.fx." + objectType);
										System.out.println("nif.niobject.interpolator." + objectType);
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("unknown block type " + objectType);
		return null;
	}

}