package nif;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.NifFooter;
import nif.compound.NifHeader;
import nif.niobject.NiNode;
import nif.niobject.NiObject;
import nif.niobject.controller.NiObjectNET;

/**
 * Notes on how to extend version support, for untested versions (and bugs)
 * 
 * If you encounter an error similar to this "blocks[i].readFromStream for i=17 type= NiParticleSystem should have read
 * off 156 but in fact read off 8186"
 * 
 * It most likely is either a version that isn't supported in the code or a bug in a version decoding step.
 * 
 * Both issues are very easy to fix.
 * 
 * Open file with notepad and see text like this at the start "Gamebryo File Format, Version 20.3.0.9" This tells you it
 * is a valid nif file and the version number of the file. Look at NifVer.java to see the list of version numbers and
 * games they match.
 * 
 * You will need the definition of Nif file formats Human readable version here:
 * http://niftools.sourceforge.net/doc/nif/ Note this is not perfectly update in all cases
 * 
 * You will also want NifSkope a tool for opening and viewing the files (this isn't mandatory but helps a lot)
 * http://niftools.sourceforge.net/wiki/NifSkope
 * 
 * Setting IS_DEBUG = true below helps a lot.
 * 
 * So for my example case I see a message stating that i=56 NiParticleSystem has read badly In most cases this suggests
 * i=55 may be the culprit and I need to start from there and examine the code versus the specification It is a
 * NiZBufferProperty, but a quick check of the code shows nothing interesting or out of place So now I example
 * NiParticleSystem and immediately seen odd 20.3.0.9 version decoding code.
 * 
 * So I carefully implements the spec as seen in nifskope and at the docs site.
 * 
 * And this now leads to a new bug in i=69...
 * 
 * Meshes\characters\_male\idleanims\deathposes.psa fallout?
 * 
 * @author phil
 *
 */
public class NifFileReader {
	// NOTE!!! NifFile readNif(String fileName, ByteBuffer in) must be set synchronized if this is set true
	public static boolean	REVIEW_REFS_POST_LOAD	= false;

	public static boolean	IS_DEBUG				= false;

	/**
	 * NON caching!
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static NifFile readNif(File file) throws IOException {
		//BufferedInputStream nifIn = new BufferedInputStream(new FileInputStream(file));
		RandomAccessFile nifIn = new RandomAccessFile(file, "r");
		ByteBuffer buf = nifIn.getChannel().map(MapMode.READ_ONLY, 0, file.length());

		NifFile nifFile = readNif(file.getCanonicalPath(), buf);
		nifIn.close();
		return nifFile;
	}

	/**
	 * Reads the given input stream and returns a vector of block references NON caching!
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static NifFile readNif(String fileName, ByteBuffer in) throws IOException {
		in.order(ByteOrder.LITTLE_ENDIAN);
		if (REVIEW_REFS_POST_LOAD) {
			// make sure these are cleared in case of exceptional exit last usage
			NifRef.allRefs.clear();
			NifPtr.allPtrs.clear();
		}

		//ProgressInputStream in = new ProgressInputStream(inStr);

		NifHeader header = new NifHeader(fileName);
		NifVer nifVer = header.nifVer;

		boolean goodHeader = header.readFromStream(in);
		if (!goodHeader) {
			System.out.println("could not load file " + fileName + " due to bad header");
			return null;
		}
		if (IS_DEBUG) {
			System.out.println("fileName " + fileName);
			System.out.println("Header " + header);
		}

		if (nifVer.LOAD_VER < NifVer.VER_3_3_0_13) {
			System.out.println("NIF VERSION TOO LOW!! < NifVer.VER_3_3_0_13 "	+ nifVer.LOAD_VER + "  min = "
								+ NifVer.VER_3_3_0_13);
			return null;
		}

		// set the byteconvert string list static if required for index strings
		if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3) {
			nifVer.indexStrings = header.strings;
		}

		int numBlocks = header.numBlocks;
		boolean unknownBlocksFound = false;

		NiObject[] blocks = new NiObject[numBlocks];
		if (REVIEW_REFS_POST_LOAD) {
			NifRef.maxRefId = numBlocks - 1;// to test on the fly
			NifPtr.maxRefId = numBlocks - 1;// to test on the fly
		}

		String objectType = null;
		for (int i = 0; i < numBlocks; i++) {
			// There are two ways to read blocks, one before version 5.0.0.1 and one after that, ignore the one before
			if (nifVer.LOAD_VER >= NifVer.VER_5_0_0_1) {
				// From version 5.0.0.1 to version 10.0.1.0 there is a zero byte at the begining of each block
				if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_106) {
					int checkValue = ByteConvert.readInt(in);
					if (checkValue != 0) {
						System.out.println(fileName + " i = " + i + "  previous object type was " + objectType);
						throw new IOException(
								"Read failue - Bad object position  zero byte at the begining of each block ");
					}
				}

				// Find which block type this is by using the header arrays
				objectType = header.blockTypes[header.blockTypeIndex[i]];

			} else {
				objectType = ByteConvert.readSizedString(in);

				if (nifVer.LOAD_VER < NifVer.VER_3_3_0_13) {
					// There can be special commands instead of object names
					// in these versions
					if (objectType == "Top Level Object") {
						// Just continue on to the next object
						continue;
					}

					if (objectType == "End Of File") {
						// File is finished
						break;
					}
				}

			}

			// Create Block of the type that was found
			if (IS_DEBUG)
				System.out.println("Loading: " + i + " " + objectType);

			NiObject obj = constructNiObject(objectType);

			// Check for an unknown block type
			if (obj == null) {
				System.out.println("Unknown object type encountered during file read:  "	+ objectType + " i = " + i
									+ " in " + fileName);

				unknownBlocksFound = true;
				continue;
			}

			int index = -1;
			if (nifVer.LOAD_VER < NifVer.VER_3_3_0_13) {
				// These old versions have a pointer value after the name
				// which is used as the index
				index = ByteConvert.readInt(in);
			} else {
				// These newer versions use their position in the file as their index
				index = i;
			}

			obj.refId = index;
			blocks[index] = obj;

			try {

				// set the position to the current based on whatever the header read off
				int prevBytePos = in.position();

				// mark in case of over read
				//in.mark(1000000);

				obj.readFromStream(in, nifVer);

				int bytesReadOff = in.position() - prevBytePos;

				if (bytesReadOff < 0)
					System.out.println("Negative bytes read! Shenanigans.");

				// only games after fallout have block sizes
				if (header.blockSizes != null && bytesReadOff != header.blockSizes[i]) {
					System.out.println("Problem  in "	+ fileName + " i=" + i + " type= "
										+ header.blockTypes[header.blockTypeIndex[i]] + " should have read off "
										+ header.blockSizes[i] + " but in fact read off " + bytesReadOff + " diff= "
										+ (header.blockSizes[i] - bytesReadOff));

					// We've read too little or parts of the next block too, let's reset it and reread what it should have been
					in.position(prevBytePos+header.blockSizes[i]);
					//byte[] dumper = new byte[header.blockSizes[i]];
					//in.get(dumper);
				}

				if (IS_DEBUG && obj instanceof NiObjectNET) {
					System.out.println("name: " + ((NiObjectNET)obj).name);
				}
			} catch (Exception e) {
				System.out.println("Exception occured in file " + fileName);
				e.printStackTrace();
				return null;
			} catch (Error err) {
				System.out
						.println("Error whilst loading block num " + i + " of type " + objectType + " in " + fileName);
				throw (err);
			}

		}

		// --Read Footer--//
		NifFooter footer = new NifFooter();
		footer.readFromStream(in);

		if (in.hasRemaining()) {
			throw new IOException("End of file not reached.  This NIF may be corrupt or improperly supported.");
		}

		// ******************
		// Now to do checks and fix ups etc

		// set reverse look up for NIAVObject
		for (int i = 0; i < blocks.length; i++) {
			NiObject niObject = blocks[i];
			if (niObject instanceof NiNode) {
				((NiNode)niObject).setChildToParentPointers(blocks);
			}
		}

		//Example of anaylyzing the nif file
		/*		for (int i = 0; i < blocks.length; i++)
				{
					NiObject niObject = blocks[i];
					if (niObject instanceof NiGeometry)
					{
		
						NiGeometry niGeometry = (NiGeometry) niObject;
						int NiTexturingPropertyC = 0;
						int BSShaderLightingPropertyC = 0;
						int BSLightingShaderPropertyC = 0;
						int BSEffectShaderPropertyC = 0;
						for (int p = 0; p < niGeometry.numProperties; p++)
						{
							int ref = niGeometry.properties[p].ref;
							if (ref > 0)
							{
								NiObject prop = blocks[niGeometry.properties[p].ref];
								if (prop instanceof NiTexturingProperty)
									NiTexturingPropertyC++;
								if (prop instanceof BSShaderLightingProperty)
									BSShaderLightingPropertyC++;
								if (prop instanceof BSLightingShaderProperty)
									BSLightingShaderPropertyC++;
								if (prop instanceof BSEffectShaderProperty)
									BSEffectShaderPropertyC++;
							}
						}
						
						//Officially ok  NiTexturingPropertyC 1 BSShaderLightingPropertyC 1 fallout3
						
						int tot = NiTexturingPropertyC + BSShaderLightingPropertyC + BSLightingShaderPropertyC + BSEffectShaderPropertyC;
						if (tot > 1&& !(NiTexturingPropertyC==1&&BSShaderLightingPropertyC==1&&BSLightingShaderPropertyC==0&&BSEffectShaderPropertyC==0))
						{
							System.err.print("Geom! ");
							if (NiTexturingPropertyC > 0)
								System.err.print(" NiTexturingPropertyC " + NiTexturingPropertyC);
							if (BSShaderLightingPropertyC > 0)
								System.err.print(" BSShaderLightingPropertyC " + BSShaderLightingPropertyC);
							if (BSLightingShaderPropertyC > 0)
								System.err.print(" BSLightingShaderPropertyC " + BSLightingShaderPropertyC);
							if (BSEffectShaderPropertyC > 0)
								System.err.print(" BSEffectShaderPropertyC " + BSEffectShaderPropertyC);
							System.err.println(" " + nifVer.fileName);
						}
					}
				}*/

		if (REVIEW_REFS_POST_LOAD && !unknownBlocksFound) {
			int countOfErrorsReported = 0;// after 10 we'll fail out and return null

			// ensure NPRefs and NPPtrs point to valid objects and correct types
			for (int i = 0; i < NifRef.allRefs.size(); i++) {
				NifRef ref = NifRef.allRefs.elementAt(i);
				if (ref.ref > -1 && ref.ref < blocks.length) {
					NiObject actualNiObject = blocks[ref.ref];
					if (actualNiObject == null) {
						countOfErrorsReported++;
						System.out.println("NifRef ref:"	+ ref.ref + " is ref.refType = " + ref.refType
											+ " but the blocks[ref.ref] is null (likely bad stream read in block loading); in file "
											+ fileName);
					} else if (!ref.refType.isInstance(actualNiObject)) {
						countOfErrorsReported++;
						System.out.println("NifRef ref:"	+ ref.ref + " is type " + actualNiObject.getClass()
											+ " but ref.refType = " + ref.refType + "; in file " + fileName);
					}
				} else if (ref.ref == -1) {
					// no ref
				} else {
					countOfErrorsReported++;
					// already reported at read time of ref System.out.println("bad ref.ref " + ref.ref + " in file " + fileName);
				}

				if (countOfErrorsReported > 10)
					break;
				// TODO: and make sure they are down the hierarchy only somehow?
			}

			for (int i = 0; i < NifPtr.allPtrs.size(); i++) {
				NifPtr ptr = NifPtr.allPtrs.elementAt(i);
				if (ptr.ptr > -1 && ptr.ptr < blocks.length) {
					NiObject actualNiObject = blocks[ptr.ptr];
					if (actualNiObject == null) {
						countOfErrorsReported++;
						System.out.println("NifPtr ptr:"	+ ptr.ptr + " is ptr.ptrType = " + ptr.ptrType
											+ " but the blocks[ptr.ptr] is null (likely bad stream read in block loading); in file "
											+ fileName);
					} else if (!ptr.ptrType.isInstance(actualNiObject)) {
						countOfErrorsReported++;
						System.out.println("NifPtr ptr:"	+ ptr.ptr + " is type " + actualNiObject.getClass()
											+ " but ptr.ptrType = " + ptr.ptrType + "; in file " + fileName);
					}
				} else if (ptr.ptr == -1) {
					// no ptr
				} else {
					countOfErrorsReported++;
					// already reported at read time of ptr System.out.println("bad ptr.ptr " + ptr.ptr + " in file " + fileName);
				}

				if (countOfErrorsReported > 10)
					break;
			}
			NifRef.allRefs.clear();
			NifPtr.allPtrs.clear();
		}

		return new NifFile(header, new NiObjectList(nifVer, blocks), footer);

	}

	private static HashMap<String, Constructor<?>>	typeToClass	= new HashMap<String, Constructor<?>>();

	private static Object[]							noArgs		= new Object[] {};

	/**
	 * @param objectType
	 * @return
	 */
	private static NiObject constructNiObject(String objectType) {
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
				return (NiObject)obj;
			} catch (Exception e) {
				e.printStackTrace();
				// bummer just fail out then
			}
		} else {
			try {
				Class<?> newClass = Class.forName("nif.niobject." + objectType);

				Constructor<?> cons = newClass.getConstructors()[0];
				typeToClass.put(objectType, cons);
				Object obj = cons.newInstance(noArgs);
				return (NiObject)obj;
			} catch (Exception e) {
				try {
					Class<?> newClass = Class.forName("nif.niobject.controller." + objectType);

					Constructor<?> cons = newClass.getConstructors()[0];
					typeToClass.put(objectType, cons);
					Object obj = cons.newInstance(noArgs);
					return (NiObject)obj;
				} catch (Exception e2) {
					try {
						Class<?> newClass = Class.forName("nif.niobject.bhk." + objectType);

						Constructor<?> cons = newClass.getConstructors()[0];
						typeToClass.put(objectType, cons);
						Object obj = cons.newInstance(noArgs);
						return (NiObject)obj;
					} catch (Exception e3) {
						try {
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
								try {
									Class<?> newClass = Class.forName("nif.niobject.fx." + objectType);
									Constructor<?> cons = newClass.getConstructors()[0];
									typeToClass.put(objectType, cons);
									Object obj = cons.newInstance(noArgs);
									return (NiObject)obj;
								} catch (Exception e6) {
									try {
										Class<?> newClass = Class.forName("nif.niobject.interpolator." + objectType);
										Constructor<?> cons = newClass.getConstructors()[0];
										typeToClass.put(objectType, cons);
										Object obj = cons.newInstance(noArgs);
										return (NiObject)obj;
									} catch (Exception e7) {
										// ok give up
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