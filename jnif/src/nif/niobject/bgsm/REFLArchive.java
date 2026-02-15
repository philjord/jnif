package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class REFLArchive {

	protected ReflectionData reflectionData;

	public REFLArchive(ByteBuffer in) {

		reflectionData = new ReflectionData(in);
		System.out.println("Cdb loaded without issue?");

	}

	public static class ReflectionData {

		static ChunkSource			chunkSource;
		static BETH					BETH;
		static STRT					STRT;									// for debug made static so I check easy print strings
		static TYPE					TYPE;

		static SparseArray<CLAS>	CLASLookup	= new SparseArray<CLAS>();

		public ReflectionData(ByteBuffer reflIn) {

			try {

				chunkSource = new ChunkSource(reflIn);

				BETH = new BETH(chunkSource.nextChunk());
				STRT = new STRT(chunkSource.nextChunk());
				TYPE = new TYPE(chunkSource.nextChunk());
				for (int i = 0; i < TYPE.numCLAS; i++) {
					CLAS c = new CLAS(chunkSource.nextChunk());
					CLASLookup.put(c.nameOffset, c);
					//debug print
					//System.out.println("CLAS  " + i + " version " + c.versionID + " flags " + c.flags + " "
					//					+ STRT.getString(c.nameOffset));
				}

				//so now I think I'd get each of these by requesting via an offset
				// however for now I'll just run through and load them all up

				int datachunkcount = BETH.numChunks - (3 + TYPE.numCLAS);// total chunk minus the ones rad above
				for (int i = 0; i < datachunkcount; i++) {
					Chunk chunk = chunkSource.nextChunk();

					if (chunk != null) {
						REFL refl = null;
						if (chunk.type.equals("OBJT")) {
							refl = new OBJT(chunk.chunkBB);
						} else if (chunk.type.equals("DIFF")) {
							refl = new DIFF(chunk.chunkBB);
						} else {
							throw new IOException("opps not OBJT, DIFF read type " + chunk.type);
						}
					} else {
						// the numChunks from beth includes all the MAPC etc so it not the right number to iterate for OBJT and DIFF
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static class ChunkSource {
			ByteBuffer cdb;

			public ChunkSource(ByteBuffer in) {
				this.cdb = in;
			}

			public Chunk nextChunk() throws IOException {
				if (cdb.hasRemaining())
					return new Chunk(cdb);
				else
					return null;
			}
		}

		public static class Chunk {
			String		type;	// note 4 byte read so could be compressed to the int equivalent and statics comparison used
			int			size;
			ByteBuffer	chunkBB;

			public Chunk(ByteBuffer cdb) throws IOException {
				type = new String(ByteConvert.readBytes(4, cdb));
				size = ByteConvert.readInt(cdb);
				chunkBB = cdb.slice();
				chunkBB.order(cdb.order());
				chunkBB.limit(size);

				// just for debug
				//System.out.println("Chunk read " + type + " of size " + size);

				// move cdb forward ready for the next chunk read
				cdb.position(cdb.position() + size);
			}
		}

		/*
		   https://forums.nexusmods.com/topic/13361451-starfields-cdb-material-database/
		
		For reference, here is a short description of the reflection data format. It consists of a set of chunks that begin 
		with the chunk type (4 bytes, BETH, STRT, TYPE, CLAS, OBJT, DIFF, LIST, MAPC, USER or USRD) 
		followed by the data size as a 32-bit integer, then the chunk data. 
		
		The first three chunks are always BETH, STRT and TYPE, in this order:
		
		BETH: Header, the size is always 8 bytes, and the data consists of two 32-bit integers, a version number that is currently always 4, 
		and the total number of chunks in the stream (this includes the BETH itself).
		STRT: String table, it is a set of C style NUL terminated strings concatenated to a single data block. 
		In the rest of the stream, type and variable names are 32-bit signed integer offsets into the string table. 
		There is also a set of pre-defined types that can be referenced with negative string table offsets (see below).
		TYPE: It contains a single 32-bit integer that is the number of CLAS chunks to follow.
		*/
		public static class BETH {
			int	version;
			int	numChunks;

			public BETH(Chunk chunk) throws IOException {

				if (!chunk.type.equals("BETH")) {
					throw new IOException("opps bad BETH read type " + chunk.type);
				}

				//always 2 ints
				version = ByteConvert.readInt(chunk.chunkBB);// 4 
				numChunks = ByteConvert.readInt(chunk.chunkBB);//1.4million
			}
		}

		public static class STRT {
			byte[] nameBuffer;

			public STRT(Chunk chunk) throws IOException {

				if (!chunk.type.equals("STRT")) {
					throw new IOException("opps bad STRT read type " + chunk.type);
				}

				//load fileNameBlock into a fat byte array for fast look up
				nameBuffer = new byte[chunk.size];
				chunk.chunkBB.get(nameBuffer);
			}

			//TODO: store as just the int, anyone else can do there own lookup
			//TODO: in fact better, little isOBJT method that looks up the integer the first time then records it and 
			// does a straight ==

			public String getString(int offset) {
				if (offset >= 0) {
					int startIndex = offset;
					// search through for the end of the filename
					int bufferIndex = startIndex;
					for (; bufferIndex < nameBuffer.length && nameBuffer[bufferIndex] != 0; bufferIndex++) {
						;
					}

					return new String(nameBuffer, startIndex, bufferIndex - startIndex);
				} else {
					return primTypeDesc(offset);
				}
			}

			public static String primTypeDesc(int offset) {
				switch (offset) {
					case -255:
						return "null";//"0xFFFFFF01 (-255): Null, no data.";
					case -254:
						return "String";//"0xFFFFFF02 (-254): String, a 16-bit length value followed by the C style string data (including a terminating NUL character).";
					case -253:
						return "List";//"0xFFFFFF03 (-253): List, requires a separate LIST chunk.";
					case -252:
						return "Map";//"0xFFFFFF04 (-252): Map, requires a separate MAPC chunk.";
					case -251:
						return "Pointer";//"0xFFFFFF05 (-251): Pointer/reference to anything, stored as a pair of type and data (the type is a string table offset).";
					case -248:
						return "byte";//"0xFFFFFF08 (-248): 8-bit signed integer.";
					case -247:
						return "ubyte";//"0xFFFFFF09 (-247): 8-bit unsigned integer.";
					case -246:
						return "short";//"0xFFFFFF0A (-246): 16-bit signed integer.";
					case -245:
						return "ushort";//"0xFFFFFF0B (-245): 16-bit unsigned integer.";
					case -244:
						return "int";//"0xFFFFFF0C (-244): 32-bit signed integer.";
					case -243:
						return "uint";//"0xFFFFFF0D (-243): 32-bit unsigned integer.";
					case -242:
						return "long";//"0xFFFFFF0E (-242): 64-bit signed integer.";
					case -241:
						return "ulong";//"0xFFFFFF0F (-241): 64-bit unsigned integer.";
					case -240:
						return "bool";//"0xFFFFFF10 (-240): Boolean (0 or 1 as an 8-bit integer).";
					case -239:
						return "float";//"0xFFFFFF11 (-239): 32-bit float.";
					case -238:
						return "double";//"0xFFFFFF12 (-238): 64-bit float.";
					default:
						return "unknown type: " + offset;
				}
			}
		}

		public static class TYPE {
			int numCLAS;

			public TYPE(Chunk chunk) throws IOException {

				if (!chunk.type.equals("TYPE")) {
					throw new IOException("opps bad TYPE read type " + chunk.type);
				}
				numCLAS = ByteConvert.readInt(chunk.chunkBB);
			}
		}

		/*
		These are followed by class definitions (CLAS), the same number as specified in TYPE. The format of CLAS data is:
		
		Class name as string table offset.
		Class version/ID as a 32-bit integer, typically 0, 1 or 2.
		Flags as 16-bit integer, if bit 2 (0x0004) is set, then a USER or USRD chunk will be used to store the structure data. 
		Bit 3 (0x0008) is set on certain structures, but its exact purpose is unknown. Other flag bits seem to be currently unused.
		Number of field definitions to follow (16-bit integer).
		The definition of a single field consists of the name and type (both as 32-bit string table offsets), then the data offset
		and size as 16-bit integers. The latter two refer to how the structure data is stored in memory in the game
		(with alignment etc. taken into account), and are not required for decoding.
		*/
		public static class CLAS {
			public int		nameOffset;
			public int		versionID;
			public short	flags;
			public boolean	isUSER;

			public static class CLASField {
				int	fieldName;
				int	fieldType;
				int	fieldDataOffset;
				int	fieldSize;
			}

			public CLASField[] fields;

			public CLAS(Chunk chunk) throws IOException {

				if (!chunk.type.equals("CLAS")) {
					throw new IOException("opps bad CLAS read type " + chunk.type);
				}

				nameOffset = ByteConvert.readInt(chunk.chunkBB);
				versionID = ByteConvert.readInt(chunk.chunkBB);
				flags = ByteConvert.readShort(chunk.chunkBB);
				isUSER = ((flags & 0x0004) != 0);
				int numFields = ByteConvert.readUnsignedShort(chunk.chunkBB);
				fields = new CLASField[numFields];
				//System.out.println(STRT.getString(nameOffset) + " {");
				for (int i = 0; i < numFields; i++) {
					fields[i] = new CLASField();
					fields[i].fieldName = ByteConvert.readInt(chunk.chunkBB);
					fields[i].fieldType = ByteConvert.readInt(chunk.chunkBB);
					fields[i].fieldDataOffset = ByteConvert.readUnsignedShort(chunk.chunkBB);
					fields[i].fieldSize = ByteConvert.readUnsignedShort(chunk.chunkBB);
					//System.out.println("\t"+STRT.getString(fields[i].fieldType) + " " + STRT.getString(fields[i].fieldName));
				}
				//System.out.println("}");
			}

			@Override
			public String toString() {
				return "CLAS "	+ STRT.getString(nameOffset) + " versionID " + versionID + " flags " + flags + " isUSER "
						+ isUSER + " numFields " + fields.length;
			}
		}

		/**
		 * header of OBJT, DIFF, USER, USRD
		 * 
		 */
		public static class REFL {
			public int classID;

			//public ArrayList<STRU> children = new ArrayList<STRU>();
			//TODO: hang on to field data too
		}

		/*
		The class definitions are followed by the actual data. Each object is stored as an OBJT or DIFF chunk, which begin with the
		data type (as string table offset). The difference between OBJT and DIFF is that the former just contains all data fields 
		as defined in the CLAS, while the latter uses a "differential" format that allows for encoding only a subset of the fields.
		*/
		public static class OBJT extends REFL {

			public OBJT(ByteBuffer in) throws IOException {
				classID = ByteConvert.readInt(in);
				CLAS clas = CLASLookup.get(classID);
				//System.out.println("OBJT " + clas);
				readFields(clas, in, false);
			}
		}

		/*In DIFF, each field is stored as a 16-bit signed field number (0 = first) followed by the data. A negative field number
				denotes the end of the structure. The differential format is not used within simple built-in types (like integers and floats)
				that are not structures, but it is inherited by sub-structures that are stored in separate LIST, MAPC, USER or USRD chunks.
				*/
		public static class DIFF extends REFL {
			public DIFF(ByteBuffer in) throws IOException {
				classID = ByteConvert.readInt(in);
				CLAS clas = CLASLookup.get(classID);
				//System.out.println("DIFF " + clas);

				readFields(clas, in, true);

				//debugging code example left in
				/*	if (in.hasRemaining()) {
				
						System.out.println("****DIFF type " + STRT.getString(classID) + (clas.isUSER ? " isUSER " : "")
											+ " size=" + in.limit());
				
						in.position(0);// reset for a re-read
						classID = ByteConvert.readInt(in);
				
						int fieldIdx = ByteConvert.readShort(in);
						while (fieldIdx >= 0 && fieldIdx < clas.fields.length) {
				
							CLAS.CLASField field = clas.fields[fieldIdx];
							readField(field.fieldType, in, true);
				
							// fo76utils suggests we can stop in the amount read is at the end of the size!
							if (!in.hasRemaining())
								break;
				
							fieldIdx = ByteConvert.readShort(in);
						}
				
						System.out.println("DIFF Home to mr cocks up "	+ STRT.getString(classID) + " size=" + in.limit()
											+ " read off=" + in.position());
					}*/

			}
		}

		/*
		Regular structures can be nested within OBJT and DIFF, however, certain data types require additional chunks, which are 
		stored separately after the parent object. These special types include:
		
		   LIST: A list of objects, begins with the element type (string table offset) and the number of elements (32-bit integer),
		followed by the element data.
		   MAPC: A map of objects, similar to LIST, but it contains key, value pairs, and it begins with the key and value types 
		   (two string table offsets) and the number of pairs.
		  */
		public static class LIST {

			public int	elementType;
			public int	numElements;

			public LIST(ByteBuffer in, boolean isDiff) throws IOException {
				elementType = ByteConvert.readInt(in);
				numElements = ByteConvert.readInt(in);
				//System.out.println("LIST elementType " + STRT.getString(elementType) + " numElements " + numElements);

				if (numElements > 0) {
					for (int i = 0; i < numElements; i++) {
						readField(elementType, in, isDiff);
					}
				}
			}
		}

		public static class MAPC {
			public int	keyType;
			public int	valueType;
			public int	numPairs;

			public MAPC(ByteBuffer in, boolean isDiff) throws IOException {

				keyType = ByteConvert.readInt(in);
				valueType = ByteConvert.readInt(in);
				numPairs = ByteConvert.readInt(in);
				//System.out.println("MAPC key "	+ STRT.getString(keyType) + " value " + STRT.getString(valueType)
				//					+ " numPairs " + numPairs);

				if (numPairs > 0) {
					for (int i = 0; i < numPairs; i++) {
						readField(keyType, in, isDiff);
						readField(valueType, in, isDiff);
					}
				}
			}
		}

		/*
		 USER, USRD: Used for sub-structures with the "user" (0x0004) flag set, USER if the parent is OBJT, and USRD if it is DIFF.
		These allow for type conversions, and always begin with the class name (similarly to OBJT and DIFF) 
		and end with an unknown 32-bit integer that seems to be always a small non-negative value, typically 0, 1 or 2. 
		After the class name, the data is stored as type, value pairs. If the type is the same as the class name 
		(no actual conversion is performed), then the encoding of the data is identical to OBJT and DIFF. 
		Otherwise, a type, value pair is stored for each field of the structure, and the type seems to be always a built-in one 
		(negative string table offset) based on the data I could test in the ESM and CDB. Note that in this case,
		 there seems to be no difference between USER and USRD, and a value can be assigned even to an empty structure (0 fields).
		*/
		public static class USER extends REFL {
			public USER(ByteBuffer in, int dataType) throws IOException {

				classID = ByteConvert.readInt(in);
				if (classID != dataType) {
					throw new IOException("USER dataType " + dataType + " != classIdx " + classID);
				}

				CLAS clas = CLASLookup.get(classID);
				//System.out.println("USER " + clas);

				int type = ByteConvert.readInt(in);
				if (type == classID) {
					readFields(clas, in, false);
					ByteConvert.readInt(in);//always a small non-negative value, typically 0, 1 or 2. 

				} else {
					int fieldCnt = clas.fields.length;
					int n = 0;
					do {
						readField(type, in, true);
						type = ByteConvert.readInt(in);
					} while (++n < fieldCnt && type < 0);// only primitives allowed					
				}

			}
		}

		public static class USRD extends REFL {
			public USRD(ByteBuffer in, int dataType) throws IOException {

				classID = ByteConvert.readInt(in);
				if (classID != dataType) {
					throw new IOException("USRD dataType " + dataType + " != classIdx " + classID);
				}
				CLAS clas = CLASLookup.get(classID);
				//System.out.println("USRD " + clas);

				int type = ByteConvert.readInt(in);
				if (type == classID) {
					readFields(clas, in, true);
					ByteConvert.readInt(in);//always a small non-negative value, typically 0, 1 or 2. 					

				} else {
					int fieldCnt = clas.fields.length;
					int n = 0;
					do {
						readField(type, in, true);
						type = ByteConvert.readInt(in);
					} while (++n < fieldCnt && type < 0);// only primitives allowed					
				}

			}
		}

		public static void readFields(CLAS clas, ByteBuffer in, boolean isDiff) throws IOException {
			if (!isDiff) {
				for (int i = 0; i < clas.fields.length; i++) {
					CLAS.CLASField field = clas.fields[i];
					readField(field.fieldType, in, isDiff);
				}
			} else {
				// https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsrefl.hpp
				int fieldIdx = ByteConvert.readShort(in);
				while (fieldIdx >= 0 && fieldIdx < clas.fields.length) {
					CLAS.CLASField field = clas.fields[fieldIdx];
					readField(field.fieldType, in, isDiff);
					fieldIdx = ByteConvert.readShort(in);
				}
			}
		}

		/**
		 * 
		 * @param dataType
		 * @param size
		 * @param in
		 * @param isDiff
		 * @param debugPrint
		 * @param field
		 * @return how many following child chunks are attached to this object or one of it's fields
		 * @throws IOException
		 */

		public static boolean debugPrint = false;

		/* 
		
		Finally, here is the table of built-in types:
		
		0xFFFFFF01 (-255): Null, no data.
		0xFFFFFF02 (-254): String, a 16-bit length value followed by the C style string data (including a terminating NUL character).
		0xFFFFFF03 (-253): List, requires a separate LIST chunk.
		0xFFFFFF04 (-252): Map, requires a separate MAPC chunk.
		0xFFFFFF05 (-251): Pointer/reference to anything, stored as a pair of type and data (the type is a string table offset).
		0xFFFFFF08 (-248): 8-bit signed integer.
		0xFFFFFF09 (-247): 8-bit unsigned integer.
		0xFFFFFF0A (-246): 16-bit signed integer.
		0xFFFFFF0B (-245): 16-bit unsigned integer.
		0xFFFFFF0C (-244): 32-bit signed integer.
		0xFFFFFF0D (-243): 32-bit unsigned integer.
		0xFFFFFF0E (-242): 64-bit signed integer.
		0xFFFFFF0F (-241): 64-bit unsigned integer.
		0xFFFFFF10 (-240): Boolean (0 or 1 as an 8-bit integer).
		0xFFFFFF11 (-239): 32-bit float.
		0xFFFFFF12 (-238): 64-bit float.
		
		The above is only a description of the general reflection data. However, the material database can be dumped in a human 
		readable format with mat_info -dump_db, which could be of help understanding the structures it uses.
		The 32-bit hashes in resource IDs use CRC32, this sample code correctly calculates them (paths are expected to use lower case 
		characters only, and backslashes as separators).
		*/
		public static void readField(int dataType, ByteBuffer in, boolean isDiff) throws IOException {

			if (dataType == -255) {
				//null
				if (debugPrint)
					System.out.println("nullType ");
			} else if (dataType == -254) {
				String str = ByteConvert.readShortString(in);
				if (debugPrint)
					System.out.println("String = " + str);
			} else if (dataType == -253) {
				//0xFFFFFF03 (-253): List, requires a separate LIST chunk.
				if (debugPrint)
					System.out.println("List");
				Chunk nextChunk = chunkSource.nextChunk();
				LIST LIST = new LIST(nextChunk.chunkBB, isDiff);
			} else if (dataType == -252) {
				//0xFFFFFF04 (-252): Map, requires a separate MAPC chunk.
				if (debugPrint)
					System.out.println("Map");
				Chunk nextChunk = chunkSource.nextChunk();
				MAPC MAPC = new MAPC(nextChunk.chunkBB, isDiff);
			} else if (dataType == -251) {
				int ptrTypeOffset = ByteConvert.readInt(in);
				if (ptrTypeOffset < 0) {
					//seems to be just 5 of these, they are all -255 hence null
				} else {
					CLAS ptrClass = CLASLookup.get(ptrTypeOffset);
					if (ptrClass != null) {
						//0xFFFFFF05 (-251): Pointer/reference to anything, stored as a pair of type and data (the type is a string table offset).
						if (debugPrint)
							System.out.println("Ptr "	+ ptrTypeOffset + " " + STRT.getString(ptrTypeOffset) + " "
												+ (ptrClass.isUSER ? " isUSER " : ""));

						if (ptrClass.isUSER) {
							//data stored in USER chunk after this one
							Chunk nextChunk = chunkSource.nextChunk();
							if (!isDiff) {
								USER USER = new USER(nextChunk.chunkBB, ptrTypeOffset);
							} else {
								USRD USRD = new USRD(nextChunk.chunkBB, ptrTypeOffset);
							}

						} else {
							readFields(ptrClass, in, isDiff);
						}
					} else {
						System.out.println("Ptr string off set of " + ptrTypeOffset + " class == null");
					}
				}
			} else if (dataType == -248) {
				byte b = ByteConvert.readByte(in);
				if (debugPrint)
					System.out.println("byte = " + b);
			} else if (dataType == -247) {
				int ub = (ByteConvert.readByte(in) & 0xFF);
				if (debugPrint)
					System.out.println("ubyte = " + ub);
			} else if (dataType == -246) {
				short s = ByteConvert.readShort(in);
				if (debugPrint)
					System.out.println("short = " + s);
			} else if (dataType == -245) {
				int us = (ByteConvert.readShort(in) & 0xFF);
				if (debugPrint)
					System.out.println("ushort  = " + us);
			} else if (dataType == -244) {
				int inte = ByteConvert.readInt(in);
				if (debugPrint)
					System.out.println("int  = " + inte);
			} else if (dataType == -243) {
				long ui = (ByteConvert.readInt(in) & 0xFFFFL);
				if (debugPrint)
					System.out.println("uint = " + ui);
			} else if (dataType == -242) {
				long l = ByteConvert.readLong(in);
				if (debugPrint)
					System.out.println("long = " + l);
			} else if (dataType == -241) {
				long ul = ByteConvert.readLong(in);
				if (debugPrint)
					System.out.println("ulong = " + ul);
			} else if (dataType == -240) {
				boolean b = (ByteConvert.readByte(in) != 0);
				if (debugPrint)
					System.out.println("bool = " + b);
			} else if (dataType == -239) {
				float f = ByteConvert.readFloat(in);
				if (debugPrint)
					System.out.println("float = " + f);
			} else if (dataType == -238) {
				double d = ByteConvert.readDouble(in);
				if (debugPrint)
					System.out.println("double = " + d);
			} else if (dataType >= 0) {
				if (debugPrint)
					System.out.println("complex " + STRT.getString(dataType));

				String type = STRT.getString(dataType);

				CLAS fieldClass = CLASLookup.get(dataType);
				if (fieldClass != null) {
					if (fieldClass.isUSER) {
						//data stored in USER chunk after this one						 
						Chunk nextChunk = chunkSource.nextChunk();
						if (!isDiff) {
							USER USER = new USER(nextChunk.chunkBB, dataType);
							//System.out.println("	child type2 " + type + " " + STRT.getString(USER.classID));
						} else {
							USRD USRD = new USRD(nextChunk.chunkBB, dataType);
							//System.out.println("	child type2 " + type + " " + STRT.getString(USRD.classID));
						}
					} else {
						readFields(fieldClass, in, isDiff);
					}

					return;
				} else {
					System.out.println("Field string off set of " + dataType + " class == null");
				}

			} else {
				if (debugPrint)
					System.out.println("bad data type " + dataType);
			}
		}
	}

}
