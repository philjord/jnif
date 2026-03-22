package nif.niobject.bgsm.bsmatcdb;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import nif.ByteConvert;
import nif.niobject.bgsm.bsmatcdb.BSMaterialsCDB.CDBObject;

public class REFLArchive {	

	protected ArrayList<OBJT> objts = new ArrayList<OBJT>();	 

	protected static ChunkSource		chunkSource;
	protected BETH						BETH;
	protected static STRT				STRT;
	protected TYPE						TYPE;

	/* enum {
			ChunkType_NONE = 0,
			ChunkType_BETH = 0x48544542,
			ChunkType_STRT = 0x54525453,
			ChunkType_TYPE = 0x45505954,
			ChunkType_CLAS = 0x53414C43,
			ChunkType_LIST = 0x5453494C,
			ChunkType_MAPC = 0x4350414D,
			ChunkType_OBJT = 0x544A424F,
			ChunkType_DIFF = 0x46464944,
			ChunkType_USER = 0x52455355,
			ChunkType_USRD = 0x44525355
			};
	 */
	
	//the 4 char strings as a 4 bytre integer for speed
	protected static int					OBJT		= 1414152783;				
	protected static int					DIFF		= 1179011396;				
	//private static int					LIST		= 1414744396;				
	//private static int					MAPC		= 1129333069;						
	//private static int					USER		= 1380275029;				
	//private static int					USRD		= 1146245973;				

	protected static SparseArray<CLAS>	CLASLookup	= new SparseArray<CLAS>();

	public REFLArchive(ByteBuffer in) {

		try {

			chunkSource = new ChunkSource(in);

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


			// grab all the OBJT entries and put them in a straight list to be processed later
			Chunk chunk = chunkSource.nextChunk();
			while (chunk != null) {
				 
				OBJT objt = new OBJT(chunk.chunkBB, chunk.type == DIFF);
				objts.add(objt);
					
				//System.out.println("OBJT " + STRT.getString(objt.classID));
				chunk = chunkSource.nextChunk();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Cdb loaded without issue");

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
		int			type;
		int			size;
		ByteBuffer	chunkBB;

		public Chunk(ByteBuffer cdb) throws IOException {
			type = cdb.getInt();
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
			version = ByteConvert.readInt(chunk.chunkBB);// 4 
			numChunks = ByteConvert.readInt(chunk.chunkBB);//1.4million
		}
	}

	public static class STRT {
		byte[] nameBuffer;

		public STRT(Chunk chunk) throws IOException {
			nameBuffer = new byte[chunk.size];
			chunk.chunkBB.get(nameBuffer);
			
			//DEBUG OUTPUT all strings to offset			
			/*int bufferIndex = 0;			
			while (bufferIndex < nameBuffer.length) {
				int startIndex = bufferIndex;
				for (; bufferIndex < nameBuffer.length && nameBuffer[bufferIndex] != 0; bufferIndex++) {
					;
				}
				String s = new String(nameBuffer, startIndex, bufferIndex - startIndex);
				System.out.println("STRT_String_"+s.replace("::","_")+"=" + startIndex + ",");						
				bufferIndex++;
			}*/
		}

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
				//System.out.println(
				//		"\t" + STRT.getString(fields[i].fieldType) + " " + STRT.getString(fields[i].fieldName));
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
	 * Also used for generic field data collections
	 */
	public static class REFL extends CDBObject {
		//public int			type;
		//public CDBObject[]		children;
		protected boolean	isDiff	= false;
		
		//for use by OBJT and USER only
		protected REFL() throws IOException {			
		}
 
		public REFL(int type, CDBObject[] children) {
			this.type = type;
			this.children = children;
		}

	}

	/*
	The class definitions are followed by the actual data. Each object is stored as an OBJT or DIFF chunk, which begin with the
	data type (as string table offset). The difference between OBJT and DIFF is that the former just contains all data fields 
	as defined in the CLAS, while the latter uses a "differential" format that allows for encoding only a subset of the fields.
	In DIFF, each field is stored as a 16-bit signed field number (0 = first) followed by the data. A negative field number
	denotes the end of the structure. The differential format is not used within simple built-in types (like integers and floats)
	that are not structures, but it is inherited by sub-structures that are stored in separate LIST, MAPC, USER or USRD chunks.
	*/
	public static class OBJT extends REFL {

		public OBJT(ByteBuffer in, boolean isDiff) throws IOException {
			this.isDiff = isDiff;
			type = ByteConvert.readInt(in);
			CLAS clas = CLASLookup.get(type);
			children = readFields(clas, in, isDiff);	
			//System.out.println("OBJT " + clas);

						
			// now to convert the fields into a list of CDBObjects for MaterialCDB to work with somehow
			

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
	public static class LIST extends CDBObject {
	
		public ArrayList<CDBObject> list = new ArrayList<CDBObject>(); 

		public int	elementType;
		public int	numElements;

		//public Object[]	elements;

		public LIST(ByteBuffer in, boolean isDiff) throws IOException {
			elementType = ByteConvert.readInt(in);
			numElements = ByteConvert.readInt(in);
			//System.out.println("LIST elementType " + STRT.getString(elementType) + " numElements " + numElements);

			if (numElements > 0) {
				//elements = new Object[numElements];
				list.ensureCapacity(numElements);
				for (int i = 0; i < numElements; i++) {
					//elements[i] = readField(elementType, in, isDiff);
					list.add(readField(elementType, in, isDiff));
				}
			}
		}
	}

	public static class MAPC extends CDBObject {
		public HashMap<CDBObject, CDBObject> map = new HashMap<CDBObject, CDBObject>();
	
		public int	keyType;
		public int	valueType;
		public int	numPairs;

		//public HashMap<Object, Object>	pairs;

		public MAPC(ByteBuffer in, boolean isDiff) throws IOException {

			keyType = ByteConvert.readInt(in);
			valueType = ByteConvert.readInt(in);
			numPairs = ByteConvert.readInt(in);
			//System.out.println("MAPC key "	+ STRT.getString(keyType) + " value " + STRT.getString(valueType)
			//					+ " numPairs " + numPairs);

			if (numPairs > 0) {
				//pairs = new HashMap<Object, Object>();					
				for (int i = 0; i < numPairs; i++) {
					CDBObject k = readField(keyType, in, isDiff);
					CDBObject v = readField(valueType, in, isDiff);

					//pairs.put(k, v);
					map.put(k, v);
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
		public CDBObject zerofieldsValue;

		public USER(ByteBuffer in, int dataType, boolean isDiff) throws IOException {
			this.isDiff = isDiff;
			type = ByteConvert.readInt(in);
			if (type != dataType) {
				throw new IOException("USER dataType " + dataType + " != type " + type);
			}

			CLAS clas = CLASLookup.get(type);
			//System.out.println("USER " + clas);

			int type2 = ByteConvert.readInt(in);
			if (type2 == type) {
				children = readFields(clas, in, isDiff);
				ByteConvert.readInt(in);//always a small non-negative value, typically 0, 1 or 2. 

			} else {
				int fieldCnt = clas.fields.length;
				 
				int n = 0;
				do {
					CDBObject val = readField(type2, in, true);
					if (clas.fields.length == 0)
						zerofieldsValue = val;
					else
						children[n] = val;
					type2 = ByteConvert.readInt(in);
				} while (++n < fieldCnt && type2 < 0);// only primitives allowed					
			}

		}
	}

	public static BSMaterialsCDB.CDBObject[] readFields(CLAS clas, ByteBuffer in, boolean isDiff) throws IOException {

		CDBObject[] fields = new CDBObject[clas.fields.length];

		if (!isDiff) {
			for (int i = 0; i < clas.fields.length; i++) {
				CLAS.CLASField field = clas.fields[i];
				fields[i] =  readField(field.fieldType, in, isDiff);
			}
		} else {
			// https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsrefl.hpp
			int fieldIdx = ByteConvert.readShort(in);
			while (fieldIdx >= 0 && fieldIdx < clas.fields.length) {
				CLAS.CLASField field = clas.fields[fieldIdx];
				fields[fieldIdx] = readField(field.fieldType, in, isDiff);
				fieldIdx = ByteConvert.readShort(in);
			}
		}

		return fields;
	}

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
	public static CDBObject readField(int dataType, ByteBuffer in, boolean isDiff) throws IOException {

		if (dataType == -255) {
			//null
			if (debugPrint)
				System.out.println("nullType ");
			return null;
		} else if (dataType == -254) {
			String str = ByteConvert.readShortString(in);
			if (debugPrint)
				System.out.println("String = " + str);
			return new CDBObject(dataType, str);
		} else if (dataType == -253) {
			//0xFFFFFF03 (-253): List, requires a separate LIST chunk.
			if (debugPrint)
				System.out.println("List");
			Chunk nextChunk = chunkSource.nextChunk();
			LIST LIST = new LIST(nextChunk.chunkBB, isDiff);
			return LIST;
		} else if (dataType == -252) {
			//0xFFFFFF04 (-252): Map, requires a separate MAPC chunk.
			if (debugPrint)
				System.out.println("Map");
			Chunk nextChunk = chunkSource.nextChunk();
			MAPC MAPC = new MAPC(nextChunk.chunkBB, isDiff);
			return MAPC;
		} else if (dataType == -251) {
			int ptrDataType = ByteConvert.readInt(in);
			if (ptrDataType < 0) {
				//seems to be just 5 of these, they are all -255 hence null
				return null;
			} else {
				CLAS ptrFieldClass = CLASLookup.get(ptrDataType);
				if (ptrFieldClass != null) {
					//0xFFFFFF05 (-251): Pointer/reference to anything, stored as a pair of type and data (the type is a string table offset).
					if (debugPrint)
						System.out.println("Ptr "	+ ptrDataType + " " + STRT.getString(ptrDataType) + " "
											+ (ptrFieldClass.isUSER ? " isUSER " : ""));

					if (ptrFieldClass.isUSER) {
						//data stored in USER chunk after this one
						Chunk nextChunk = chunkSource.nextChunk();
						USER USER = new USER(nextChunk.chunkBB, ptrDataType, isDiff);
						return USER;
					} else {
						return new REFL(ptrDataType, readFields(ptrFieldClass, in, isDiff));
					}
				} else {
					System.out.println("Ptr string off set of " + ptrDataType + " class == null");
					return null;
				}
			}
		} else if (dataType == -248) {
			byte b = ByteConvert.readByte(in);
			return new CDBObject(dataType, b);
		} else if (dataType == -247) {
			byte ub = ByteConvert.readByte(in);
			return new CDBObject(dataType, ub);
		} else if (dataType == -246) {
			short s = ByteConvert.readShort(in);
			return new CDBObject(dataType, s);
		} else if (dataType == -245) {
			short us = ByteConvert.readShort(in);
			return new CDBObject(dataType, us);
		} else if (dataType == -244) {
			int inte = ByteConvert.readInt(in);
			return new CDBObject(dataType, inte);
		} else if (dataType == -243) {
			int ui = ByteConvert.readInt(in);
			return new CDBObject(dataType, ui);
		} else if (dataType == -242) {
			long l = ByteConvert.readLong(in);
			return new CDBObject(dataType, l);
		} else if (dataType == -241) {
			long ul = ByteConvert.readLong(in);
			return new CDBObject(dataType, ul);
		} else if (dataType == -240) {
			boolean b = (ByteConvert.readByte(in) != 0);
			return new CDBObject(dataType, b);
		} else if (dataType == -239) {
			float f = ByteConvert.readFloat(in);
			return new CDBObject(dataType, f);
		} else if (dataType == -238) {
			double d = ByteConvert.readDouble(in);
			return new CDBObject(dataType, d);
		} else if (dataType >= 0) {
			//String type = STRT.getString(dataType);
			CLAS fieldClass = CLASLookup.get(dataType);
			if (fieldClass != null) {
				if (fieldClass.isUSER) {
					//data stored in USER chunk after this one						 
					Chunk nextChunk = chunkSource.nextChunk();
					USER USER = new USER(nextChunk.chunkBB, dataType, isDiff);
					//System.out.println("	child type2 " + type + " " + STRT.getString(USER.classID));
					return USER;
				} else {
					return new REFL(dataType, readFields(fieldClass, in, isDiff));
				}
			} else {
				System.out.println("Field string off set of " + dataType + " class == null");
				return null;
			}
		} else {
			System.out.println("bad data type " + dataType);
			return null;
		}
	}
	 
	
	
	
	  
	  
	  
	  public static final int 
	  STRT_String_BSMaterial_Internal_CompiledDB=0,
			  STRT_String_BuildVersion=33,
			  STRT_String_HashMap=46,
			  STRT_String_Collisions=54,
			  STRT_String_Circular=65,
			  STRT_String_BSResource_ID=74,
			  STRT_String_Dir=89,
			  STRT_String_File=93,
			  STRT_String_Ext=98,
			  STRT_String_BSComponentDB2_DBFileIndex=102,
			  STRT_String_ComponentTypes=130,
			  STRT_String_Objects=145,
			  STRT_String_Components=153,
			  STRT_String_Edges=164,
			  STRT_String_Optimized=170,
			  STRT_String_BSComponentDB2_DBFileIndex_ComponentTypeInfo=180,
			  STRT_String_ClassReference=227,
			  STRT_String_Class=242,
			  STRT_String_Version=248,
			  STRT_String_IsEmpty=256,
			  STRT_String_BSComponentDB2_DBFileIndex_ObjectInfo=264,
			  STRT_String_PersistentID=304,
			  STRT_String_BSComponentDB2_ID=317,
			  STRT_String_Value=336,
			  STRT_String_DBID=342,
			  STRT_String_Parent=347,
			  STRT_String_HasData=354,
			  STRT_String_BSComponentDB2_DBFileIndex_ComponentInfo=362,
			  STRT_String_ObjectID=405,
			  STRT_String_Index=414,
			  STRT_String_Type=420,
			  STRT_String_BSComponentDB2_DBFileIndex_EdgeInfo=425,
			  STRT_String_SourceID=463,
			  STRT_String_TargetID=472,
			  STRT_String_BSComponentDB_CTName=481,
			  STRT_String_Name=503,
			  STRT_String_BSMaterial_TextureSetID=508,
			  STRT_String_ID=533,
			  STRT_String_BSMaterial_MaterialOverrideColorTypeComponent=536,
			  STRT_String_BSMaterial_Color=583,
			  STRT_String_XMFLOAT4=601,
			  STRT_String_x=610,
			  STRT_String_y=612,
			  STRT_String_z=614,
			  STRT_String_w=616,
			  STRT_String_BSMaterial_Scale=618,
			  STRT_String_XMFLOAT2=636,
			  STRT_String_BSMaterial_Offset=645,
			  STRT_String_BSMaterial_TextureAddressModeComponent=664,
			  STRT_String_BSMaterial_UVStreamParamBool=704,
			  STRT_String_BSMaterial_ParamBool=734,
			  STRT_String_BSMaterial_FlipbookComponent=756,
			  STRT_String_IsAFlipbook=786,
			  STRT_String_Columns=798,
			  STRT_String_Rows=806,
			  STRT_String_FPS=811,
			  STRT_String_Loops=815,
			  STRT_String_BSMaterial_MaterialID=821,
			  STRT_String_BSMaterial_UVStreamID=844,
			  STRT_String_BSMaterial_Channel=867,
			  STRT_String_BSMaterial_MRTextureFile=887,
			  STRT_String_FileName=913,
			  STRT_String_BSMaterial_MaterialParamFloat=922,
			  STRT_String_BSMaterial_TextureReplacement=953,
			  STRT_String_Enabled=984,
			  STRT_String_Color=992,
			  STRT_String_BSMaterial_BlendModeComponent=998,
			  STRT_String_BSMaterial_ColorChannelTypeComponent=1029,
			  STRT_String_BSMaterial_TextureFile=1067,
			  STRT_String_BSMaterial_BlendParamFloat=1091,
			  STRT_String_BSMaterial_TextureResolutionSetting=1119,
			  STRT_String_ResolutionHint=1156,
			  STRT_String_BSMaterial_MipBiasSetting=1171,
			  STRT_String_DisableMipBiasHint=1198,
			  STRT_String_BSMaterial_TextureSetKindComponent=1217,
			  STRT_String_BSMaterial_LayerID=1253,
			  STRT_String_BSMaterial_ShaderModelComponent=1273,
			  STRT_String_BSMaterial_BlenderID=1306,
			  STRT_String_BSMaterial_ShaderRouteComponent=1328,
			  STRT_String_Route=1361,
			  STRT_String_BSMaterial_DetailBlenderSettingsComponent=1367,
			  STRT_String_BSMaterial_DetailBlenderSettings=1410,
			  STRT_String_IsDetailBlendMaskSupported=1444,
			  STRT_String_BSMaterial_SourceTextureWithReplacement=1471,
			  STRT_String_Texture=1512,
			  STRT_String_Replacement=1520,
			  STRT_String_DetailBlendMask=1532,
			  STRT_String_DetailBlendMaskUVStream=1548,
			  STRT_String_DetailBlenderSettings=1572,
			  STRT_String_BSMaterial_AlphaSettingsComponent=1594,
			  STRT_String_HasOpacity=1629,
			  STRT_String_AlphaTestThreshold=1640,
			  STRT_String_OpacitySourceLayer=1659,
			  STRT_String_BSMaterial_AlphaBlenderSettings=1678,
			  STRT_String_Mode=1711,
			  STRT_String_UseDetailBlendMask=1716,
			  STRT_String_UseVertexColor=1735,
			  STRT_String_VertexColorChannel=1750,
			  STRT_String_OpacityUVStream=1769,
			  STRT_String_HeightBlendThreshold=1785,
			  STRT_String_HeightBlendFactor=1806,
			  STRT_String_Position=1824,
			  STRT_String_Contrast=1833,
			  STRT_String_Blender=1842,
			  STRT_String_UseDitheredTransparency=1850,
			  STRT_String_BSMaterial_DecalSettingsComponent=1874,
			  STRT_String_IsDecal=1909,
			  STRT_String_MaterialOverallAlpha=1917,
			  STRT_String_WriteMask=1938,
			  STRT_String_IsPlanet=1948,
			  STRT_String_IsProjected=1957,
			  STRT_String_BSMaterial_ProjectedDecalSettings=1969,
			  STRT_String_UseParallaxOcclusionMapping=2004,
			  STRT_String_SurfaceHeightMap=2032,
			  STRT_String_ParallaxOcclusionScale=2049,
			  STRT_String_ParallaxOcclusionShadows=2072,
			  STRT_String_MaxParralaxOcclusionSteps=2097,
			  STRT_String_RenderLayer=2123,
			  STRT_String_UseGBufferNormals=2135,
			  STRT_String_ProjectedDecalSetting=2153,
			  STRT_String_BlendMode=2175,
			  STRT_String_AnimatedDecalIgnoresTAA=2185,
			  STRT_String_BSMaterial_EmissiveSettingsComponent=2209,
			  STRT_String_BSMaterial_EmittanceSettings=2247,
			  STRT_String_EmissiveSourceLayer=2277,
			  STRT_String_EmissiveTint=2297,
			  STRT_String_EmissiveMaskSourceBlender=2310,
			  STRT_String_EmissiveClipThreshold=2336,
			  STRT_String_AdaptiveEmittance=2358,
			  STRT_String_LuminousEmittance=2376,
			  STRT_String_ExposureOffset=2394,
			  STRT_String_EnableAdaptiveLimits=2409,
			  STRT_String_MaxOffsetEmittance=2430,
			  STRT_String_MinOffsetEmittance=2449,
			  STRT_String_Settings=2468,
			  STRT_String_BSMaterial_TranslucencySettingsComponent=2477,
			  STRT_String_BSMaterial_TranslucencySettings=2519,
			  STRT_String_Thin=2552,
			  STRT_String_FlipBackFaceNormalsInViewSpace=2557,
			  STRT_String_UseSSS=2588,
			  STRT_String_SSSWidth=2595,
			  STRT_String_SSSStrength=2604,
			  STRT_String_TransmissiveScale=2616,
			  STRT_String_TransmittanceWidth=2634,
			  STRT_String_SpecLobe0RoughnessScale=2653,
			  STRT_String_SpecLobe1RoughnessScale=2677,
			  STRT_String_TransmittanceSourceLayer=2701,
			  STRT_String_BSMaterial_FlowSettingsComponent=2726,
			  STRT_String_FlowUVOffset=2760,
			  STRT_String_FlowUVScale=2773,
			  STRT_String_FlowExtent=2785,
			  STRT_String_FlowSourceUVChannel=2796,
			  STRT_String_FlowIsAnimated=2816,
			  STRT_String_FlowSpeed=2831,
			  STRT_String_FlowMapAndTexturesAreFlipbooks=2841,
			  STRT_String_TargetUVStream=2872,
			  STRT_String_UVStreamTargetLayer=2887,
			  STRT_String_UVStreamTargetBlender=2907,
			  STRT_String_FlowMap=2929,
			  STRT_String_ApplyFlowOnANMR=2937,
			  STRT_String_ApplyFlowOnOpacity=2953,
			  STRT_String_ApplyFlowOnEmissivity=2972,
			  STRT_String_BSMaterial_EffectSettingsComponent=2994,
			  STRT_String_UseFallOff=3030,
			  STRT_String_UseRGBFallOff=3041,
			  STRT_String_FalloffStartAngle=3055,
			  STRT_String_FalloffStopAngle=3073,
			  STRT_String_FalloffStartOpacity=3090,
			  STRT_String_FalloffStopOpacity=3110,
			  STRT_String_VertexColorBlend=3129,
			  STRT_String_IsAlphaTested=3146,
			  STRT_String_NoHalfResOptimization=3160,
			  STRT_String_SoftEffect=3182,
			  STRT_String_SoftFalloffDepth=3193,
			  STRT_String_EmissiveOnlyEffect=3210,
			  STRT_String_EmissiveOnlyAutomaticallyApplied=3229,
			  STRT_String_ReceiveDirectionalShadows=3262,
			  STRT_String_ReceiveNonDirectionalShadows=3288,
			  STRT_String_IsGlass=3317,
			  STRT_String_Frosting=3325,
			  STRT_String_FrostingUnblurredBackgroundAlphaBlend=3334,
			  STRT_String_FrostingBlurBias=3372,
			  STRT_String_ZTest=3389,
			  STRT_String_ZWrite=3395,
			  STRT_String_BlendingMode=3402,
			  STRT_String_BackLightingEnable=3415,
			  STRT_String_BacklightingScale=3434,
			  STRT_String_BacklightingSharpness=3452,
			  STRT_String_BacklightingTransparencyFactor=3474,
			  STRT_String_BackLightingTintColor=3505,
			  STRT_String_DepthMVFixup=3527,
			  STRT_String_DepthMVFixupEdgesOnly=3540,
			  STRT_String_ForceRenderBeforeOIT=3562,
			  STRT_String_DepthBiasInUlp=3583,
			  STRT_String_BSMaterial_OpacityComponent=3598,
			  STRT_String_FirstLayerIndex=3627,
			  STRT_String_SecondLayerActive=3643,
			  STRT_String_SecondLayerIndex=3661,
			  STRT_String_FirstBlenderIndex=3678,
			  STRT_String_FirstBlenderMode=3696,
			  STRT_String_ThirdLayerActive=3713,
			  STRT_String_ThirdLayerIndex=3730,
			  STRT_String_SecondBlenderIndex=3746,
			  STRT_String_SecondBlenderMode=3765,
			  STRT_String_SpecularOpacityOverride=3783,
			  STRT_String_BSMaterial_HairSettingsComponent=3807,
			  STRT_String_IsSpikyHair=3841,
			  STRT_String_SpecScale=3853,
			  STRT_String_SpecularTransmissionScale=3863,
			  STRT_String_DirectTransmissionScale=3889,
			  STRT_String_DiffuseTransmissionScale=3913,
			  STRT_String_Roughness=3938,
			  STRT_String_ContactShadowSoftening=3948,
			  STRT_String_BackscatterStrength=3971,
			  STRT_String_BackscatterWrap=3991,
			  STRT_String_VariationStrength=4007,
			  STRT_String_IndirectSpecularScale=4025,
			  STRT_String_IndirectSpecularTransmissionScale=4047,
			  STRT_String_IndirectSpecRoughness=4081,
			  STRT_String_AlphaDistance=4103,
			  STRT_String_MipBase=4117,
			  STRT_String_AlphaBias=4125,
			  STRT_String_EdgeMaskContrast=4135,
			  STRT_String_EdgeMaskMin=4152,
			  STRT_String_EdgeMaskDistanceMin=4164,
			  STRT_String_EdgeMaskDistanceMax=4184,
			  STRT_String_MaxDepthOffset=4204,
			  STRT_String_DitherScale=4219,
			  STRT_String_DitherDistanceMin=4231,
			  STRT_String_DitherDistanceMax=4249,
			  STRT_String_XMFLOAT3=4267,
			  STRT_String_Tangent=4276,
			  STRT_String_TangentBend=4284,
			  STRT_String_DepthOffsetMaskVertexColorChannel=4296,
			  STRT_String_AOVertexColorChannel=4330,
			  STRT_String_BSMaterial_TerrainSettingsComponent=4351,
			  STRT_String_TextureMappingType=4388,
			  STRT_String_RotationAngle=4407,
			  STRT_String_BlendSoftness=4421,
			  STRT_String_TilingDistance=4435,
			  STRT_String_MaxDisplacement=4450,
			  STRT_String_DisplacementMidpoint=4466,
			  STRT_String_BSMaterial_EyeSettingsComponent=4487,
			  STRT_String_ScleraEyeRoughness=4520,
			  STRT_String_CorneaEyeRoughness=4539,
			  STRT_String_ScleraSpecularity=4558,
			  STRT_String_IrisSpecularity=4576,
			  STRT_String_CorneaSpecularity=4592,
			  STRT_String_IrisDepthPosition=4610,
			  STRT_String_IrisTotalDepth=4628,
			  STRT_String_DepthScale=4643,
			  STRT_String_IrisDepthTransitionRatio=4654,
			  STRT_String_IrisUVSize=4679,
			  STRT_String_LightingWrap=4690,
			  STRT_String_LightingPower=4703,
			  STRT_String_BSMaterial_DistortionComponent=4717,
			  STRT_String_Strength=4749,
			  STRT_String_UseVertexAlpha=4758,
			  STRT_String_NormalAffectsStrength=4773,
			  STRT_String_CameraDistanceFade=4795,
			  STRT_String_NearFadeValue=4814,
			  STRT_String_FarFadeValue=4828,
			  STRT_String_BlurStrength=4841,
			  STRT_String_BSMaterial_LODMaterialID=4854,
			  STRT_String_BSMaterial_LayeredEmissivityComponent=4880,
			  STRT_String_FirstLayerTint=4919,
			  STRT_String_FirstLayerMaskIndex=4934,
			  STRT_String_SecondLayerTint=4954,
			  STRT_String_SecondLayerMaskIndex=4970,
			  STRT_String_ThirdLayerTint=4991,
			  STRT_String_ThirdLayerMaskIndex=5006,
			  STRT_String_BSMaterial_LevelOfDetailSettings=5026,
			  STRT_String_NumLODMaterials=5060,
			  STRT_String_MostSignificantLayer=5076,
			  STRT_String_SecondMostSignificantLayer=5097,
			  STRT_String_MediumLODRootMaterial=5124,
			  STRT_String_LowLODRootMaterial=5146,
			  STRT_String_VeryLowLODRootMaterial=5165,
			  STRT_String_Bias=5188,
			  STRT_String_BSBind_DirectoryComponent=5193,
			  STRT_String_upDir=5220,
			  STRT_String_BSBind_Directory=5226,
			  STRT_String_Children=5244,
			  STRT_String_SourceDirectoryHash=5253,
			  STRT_String_BSBind_Multiplex=5273,
			  STRT_String_Nodes=5291,
			  STRT_String_BSMaterialBinding_MaterialPropertyNode=5297,
			  STRT_String_Binding=5337,
			  STRT_String_LayerIndex=5345,
			  STRT_String_BSBind_ControllerComponent=5356,
			  STRT_String_upControllers=5384,
			  STRT_String_BSBind_Controllers=5398,
			  STRT_String_MappingsA=5418,
			  STRT_String_UseRandomOffset=5428,
			  STRT_String_BSMaterial_ColorRemapSettingsComponent=5444,
			  STRT_String_RemapAlbedo=5484,
			  STRT_String_RemapOpacity=5496,
			  STRT_String_RemapEmissive=5509,
			  STRT_String_AlbedoPaletteTex=5523,
			  STRT_String_AlbedoTint=5540,
			  STRT_String_AlphaPaletteTex=5551,
			  STRT_String_AlphaTint=5567,
			  STRT_String_EmissivePaletteTex=5577,
			  STRT_String_BSBind_Controllers_Mapping=5596,
			  STRT_String_BSBind_Address=5625,
			  STRT_String_Path=5641,
			  STRT_String_Address=5646,
			  STRT_String_Controller=5654,
			  STRT_String_BSBind_Float2DLerpController=5665,
			  STRT_String_Duration=5695,
			  STRT_String_Loop=5704,
			  STRT_String_Start=5709,
			  STRT_String_End=5715,
			  STRT_String_Easing=5719,
			  STRT_String_Mask=5726,
			  STRT_String_BSMaterial_LayeredEdgeFalloffComponent=5731,
			  STRT_String_FalloffStartAngles=5771,
			  STRT_String_FalloffStopAngles=5790,
			  STRT_String_FalloffStartOpacities=5808,
			  STRT_String_FalloffStopOpacities=5830,
			  STRT_String_ActiveLayersMask=5851,
			  STRT_String_BSMaterialBinding_MaterialUVStreamPropertyNode=5868,
			  STRT_String_StreamID=5916,
			  STRT_String_BindingType=5925,
			  STRT_String_BSBind_Float2DCurveController=5937,
			  STRT_String_BSFloat2DCurve=5968,
			  STRT_String_BSFloatCurve=5983,
			  STRT_String_Controls=5996,
			  STRT_String_MaxInput=6005,
			  STRT_String_MinInput=6014,
			  STRT_String_InputDistance=6023,
			  STRT_String_MaxValue=6037,
			  STRT_String_MinValue=6046,
			  STRT_String_DefaultValue=6055,
			  STRT_String_Edge=6068,
			  STRT_String_IsSampleInterpolating=6073,
			  STRT_String_XCurve=6095,
			  STRT_String_YCurve=6102,
			  STRT_String_Curve=6109,
			  STRT_String_BSFloatCurve_Control=6115,
			  STRT_String_Input=6137,
			  STRT_String_BSBind_ColorCurveController=6143,
			  STRT_String_BSColorCurve=6172,
			  STRT_String_RedChannel=6185,
			  STRT_String_GreenChannel=6196,
			  STRT_String_BlueChannel=6209,
			  STRT_String_AlphaChannel=6221,
			  STRT_String_BSBind_FloatCurveController=6234,
			  STRT_String_BSBind_FloatLerpController=6263,
			  STRT_String_BSBind_Float3DCurveController=6291,
			  STRT_String_BSFloat3DCurve=6322,
			  STRT_String_ZCurve=6337,
			  STRT_String_BSMaterial_StarmapBodyEffectComponent=6344,
			  STRT_String_BSMaterial_WaterSettingsComponent=6383,
			  STRT_String_WaterEdgeFalloff=6418,
			  STRT_String_WaterWetnessMaxDepth=6435,
			  STRT_String_WaterEdgeNormalFalloff=6456,
			  STRT_String_WaterDepthBlur=6479,
			  STRT_String_WaterRefractionMagnitude=6494,
			  STRT_String_PhytoplanktonReflectanceColorR=6519,
			  STRT_String_PhytoplanktonReflectanceColorG=6550,
			  STRT_String_PhytoplanktonReflectanceColorB=6581,
			  STRT_String_SedimentReflectanceColorR=6612,
			  STRT_String_SedimentReflectanceColorG=6638,
			  STRT_String_SedimentReflectanceColorB=6664,
			  STRT_String_YellowMatterReflectanceColorR=6690,
			  STRT_String_YellowMatterReflectanceColorG=6720,
			  STRT_String_YellowMatterReflectanceColorB=6750,
			  STRT_String_MaxConcentrationPlankton=6780,
			  STRT_String_MaxConcentrationSediment=6805,
			  STRT_String_MaxConcentrationYellowMatter=6830,
			  STRT_String_ReflectanceR=6859,
			  STRT_String_ReflectanceG=6872,
			  STRT_String_ReflectanceB=6885,
			  STRT_String_LowLOD=6898,
			  STRT_String_PlacedWater=6905,
			  STRT_String_BSMaterial_WaterFoamSettingsComponent=6917,
			  STRT_String_MaskDistanceFromShoreStart=6956,
			  STRT_String_MaskDistanceFromShoreEnd=6983,
			  STRT_String_MaskDistanceRampWidth=7008,
			  STRT_String_WaveShoreFadeInnerDistance=7030,
			  STRT_String_WaveShoreFadeOuterDistance=7057,
			  STRT_String_WaveSpawnFadeInDistance=7084,
			  STRT_String_MaskNoiseAmp=7108,
			  STRT_String_MaskNoiseFreq=7121,
			  STRT_String_MaskNoiseBias=7135,
			  STRT_String_MaskNoiseAnimSpeed=7149,
			  STRT_String_MaskNoiseGlobalScale=7168,
			  STRT_String_MaskWaveParallax=7189,
			  STRT_String_BlendMaskPosition=7206,
			  STRT_String_BlendMaskContrast=7224,
			  STRT_String_FoamTextureScrollSpeed=7242,
			  STRT_String_FoamTextureDistortion=7265,
			  STRT_String_WaveSpeed=7287,
			  STRT_String_WaveAmplitude=7297,
			  STRT_String_WaveScale=7311,
			  STRT_String_WaveDistortionAmount=7321,
			  STRT_String_WaveParallaxFalloffBias=7342,
			  STRT_String_WaveParallaxFalloffScale=7366,
			  STRT_String_WaveParallaxInnerStrength=7391,
			  STRT_String_WaveParallaxOuterStrength=7417,
			  STRT_String_WaveFlipWaveDirection=7443,
			  STRT_String_BSMaterial_WaterGrimeSettingsComponent=7465,
			  STRT_String_NormalOverride=7505,
			  STRT_String_BSMaterial_GlobalLayerDataComponent=7520,
			  STRT_String_TexcoordScale_XY=7557,
			  STRT_String_TexcoordScale_YZ=7574,
			  STRT_String_TexcoordScale_XZ=7591,
			  STRT_String_AlbedoTintColor=7608,
			  STRT_String_UsesDirectionality=7624,
			  STRT_String_SourceDirection=7643,
			  STRT_String_DirectionalityIntensity=7659,
			  STRT_String_DirectionalityScale=7683,
			  STRT_String_DirectionalitySaturation=7703,
			  STRT_String_BlendNormalsAdditively=7728,
			  STRT_String_BlendPosition=7751,
			  STRT_String_BlendContrast=7765,
			  STRT_String_BSMaterial_GlobalLayerNoiseSettings=7779,
			  STRT_String_MaterialMaskIntensityScale=7816,
			  STRT_String_UseNoiseMaskTexture=7843,
			  STRT_String_NoiseMaskTexture=7863,
			  STRT_String_TexcoordScaleAndBias=7880,
			  STRT_String_WorldspaceScaleFactor=7901,
			  STRT_String_HurstExponent=7923,
			  STRT_String_BaseFrequency=7937,
			  STRT_String_FrequencyMultiplier=7951,
			  STRT_String_MaskIntensityMin=7971,
			  STRT_String_MaskIntensityMax=7988,
			  STRT_String_GlobalLayerNoiseData=8005,
			  STRT_String_BSMaterial_VegetationSettingsComponent=8026,
			  STRT_String_LeafFrequency=8066,
			  STRT_String_LeafAmplitude=8080,
			  STRT_String_BranchFlexibility=8094,
			  STRT_String_TrunkFlexibility=8112,
			  STRT_String_DEPRECATEDTerrainBlendStrength=8129,
			  STRT_String_DEPRECATEDTerrainBlendGradientFactor=8160,
			  STRT_String_BSMaterial_TerrainTintSettingsComponent=8197,
			  STRT_String_TerrainBlendStrength=8238,
			  STRT_String_TerrainBlendGradientFactor=8259,
			  STRT_String_BSBind_TimerController=8286,
			  STRT_String_BSMaterial_CollisionComponent=8310,
			  STRT_String_BSMaterial_PhysicsMaterialType=8341,
			  STRT_String_MaterialTypeOverride=8373,
			  STRT_String_BSBind_ComponentProperty=8394,
			  STRT_String_Object=8420,
			  STRT_String_ComponentType=8427,
			  STRT_String_ComponentIndex=8441,
			  STRT_String_PathStr=8456,
			  STRT_String_BSMaterial_MouthSettingsComponent=8464,
			  STRT_String_IsTeeth=8499;
	  
	  
	  
	//from https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsrefl.hpp
	/*  0xFFFFFF01 (-255): Null, no data.
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
		0xFFFFFF12 (-238): 64-bit float.*/
		//NOTE these are not the string offsets in the STRT chunk, they are something else, 
	  //now pointed to fix up the pig sty that is C++ code
		  public static final int
		    String_Unknown = 0,//has to be 0 as it's used in  >0 checks constantly (weird)
		    
		    String_None = -255,//0,
		    String_String = -254,//1,
		    String_List = -253,//2,
		    String_Map = -252,//3,
		    String_Ref = -251,//4,
		    String_Int8 = -248,//7,
		    String_UInt8 = -247,//8,
		    String_Int16 = -246,//9,
		    String_UInt16 = -245,//10,
		    String_Int32 = -244,//11,
		    String_UInt32 = -243,//12,
		    String_Int64 = -242,//13,
		    String_UInt64 = -241,//14,
		    String_Bool = -240,//15,
		    String_Float = -239,//16,
		    String_Double = -238,//17,
		    
		    //STRT_String_BSBind_ControllerComponent=5356,
		    // so what the hell are these types? is it the 130th index into strt? juat replace
		    String_BSBind_ControllerComponent = STRT_String_BSBind_ControllerComponent,//130,	    
		    String_BSBind_DirectoryComponent = STRT_String_BSBind_DirectoryComponent,//134,
		    String_BSComponentDB2_DBFileIndex = STRT_String_BSComponentDB2_DBFileIndex,//148,
		    String_BSComponentDB2_DBFileIndex_ComponentInfo = STRT_String_BSComponentDB2_DBFileIndex_ComponentInfo,//149,
		    String_BSComponentDB2_DBFileIndex_ComponentTypeInfo = STRT_String_BSComponentDB2_DBFileIndex_ComponentTypeInfo,//150,
		    String_BSComponentDB2_DBFileIndex_EdgeInfo = STRT_String_BSComponentDB2_DBFileIndex_EdgeInfo,//151,
		    String_BSComponentDB2_DBFileIndex_ObjectInfo = STRT_String_BSComponentDB2_DBFileIndex_ObjectInfo,//152,
		    String_BSComponentDB2_ID = STRT_String_BSComponentDB2_ID,//153,
		    String_BSComponentDB_CTName = STRT_String_BSComponentDB_CTName,//154,
		    String_BSMaterial_AlphaBlenderSettings = STRT_String_BSMaterial_AlphaBlenderSettings,//164,
		    String_BSMaterial_AlphaSettingsComponent = STRT_String_BSMaterial_AlphaSettingsComponent,//165,
		    String_BSMaterial_BlendModeComponent = STRT_String_BSMaterial_BlendModeComponent,//166,
		    String_BSMaterial_BlendParamFloat = STRT_String_BSMaterial_BlendParamFloat,//167,
		    String_BSMaterial_BlenderID = STRT_String_BSMaterial_BlenderID,//168,
		    String_BSMaterial_Channel = STRT_String_BSMaterial_Channel,//169,
		    String_BSMaterial_CollisionComponent = STRT_String_BSMaterial_CollisionComponent,//170,
		    String_BSMaterial_Color = STRT_String_BSMaterial_Color,//171,
		    String_BSMaterial_ColorChannelTypeComponent = STRT_String_BSMaterial_ColorChannelTypeComponent,//172,
		    String_BSMaterial_ColorRemapSettingsComponent = STRT_String_BSMaterial_ColorRemapSettingsComponent,//173,
		    String_BSMaterial_DecalSettingsComponent = STRT_String_BSMaterial_DecalSettingsComponent,//174,
		    String_BSMaterial_DetailBlenderSettings = STRT_String_BSMaterial_DetailBlenderSettings,//175,
		    String_BSMaterial_DetailBlenderSettingsComponent = STRT_String_BSMaterial_DetailBlenderSettingsComponent,//176,
		    String_BSMaterial_DistortionComponent = STRT_String_BSMaterial_DistortionComponent,//177,
		    String_BSMaterial_EffectSettingsComponent = STRT_String_BSMaterial_EffectSettingsComponent,//178,
		    String_BSMaterial_EmissiveSettingsComponent = STRT_String_BSMaterial_EmissiveSettingsComponent,//179,
		    String_BSMaterial_EmittanceSettings = STRT_String_BSMaterial_EmittanceSettings,//180,
		    String_BSMaterial_EyeSettingsComponent = STRT_String_BSMaterial_EyeSettingsComponent,//181,
		    String_BSMaterial_FlipbookComponent = STRT_String_BSMaterial_FlipbookComponent,//182,
		    String_BSMaterial_FlowSettingsComponent = STRT_String_BSMaterial_FlowSettingsComponent,//183,
		    String_BSMaterial_GlobalLayerDataComponent = STRT_String_BSMaterial_GlobalLayerDataComponent,//184,
		    String_BSMaterial_GlobalLayerNoiseSettings = STRT_String_BSMaterial_GlobalLayerNoiseSettings,//185,
		    String_BSMaterial_HairSettingsComponent = STRT_String_BSMaterial_HairSettingsComponent,//186,
		    String_BSMaterial_Internal_CompiledDB = STRT_String_BSMaterial_Internal_CompiledDB,//187,
		    //String_BSMaterial_Internal_CompiledDB_FilePair = STRT_String_BSMaterial_Internal_CompiledDB_FilePair,//188,
		    String_BSMaterial_LODMaterialID = STRT_String_BSMaterial_LODMaterialID,//189,
		    String_BSMaterial_LayerID = STRT_String_BSMaterial_LayerID,//190,
		    String_BSMaterial_LayeredEdgeFalloffComponent = STRT_String_BSMaterial_LayeredEdgeFalloffComponent,//191,
		    String_BSMaterial_LayeredEmissivityComponent = STRT_String_BSMaterial_LayeredEmissivityComponent,//192,
		    String_BSMaterial_LevelOfDetailSettings = STRT_String_BSMaterial_LevelOfDetailSettings,//193,
		    String_BSMaterial_MRTextureFile = STRT_String_BSMaterial_MRTextureFile,//194,
		    String_BSMaterial_MaterialID = STRT_String_BSMaterial_MaterialID,//195,
		    String_BSMaterial_MaterialOverrideColorTypeComponent = STRT_String_BSMaterial_MaterialOverrideColorTypeComponent,//196,
		    String_BSMaterial_MaterialParamFloat = STRT_String_BSMaterial_MaterialParamFloat,//197,
		    String_BSMaterial_MipBiasSetting = STRT_String_BSMaterial_MipBiasSetting,//198,
		    String_BSMaterial_MouthSettingsComponent = STRT_String_BSMaterial_MouthSettingsComponent,//199,
		    String_BSMaterial_Offset = STRT_String_BSMaterial_Offset,//200,
		    String_BSMaterial_OpacityComponent = STRT_String_BSMaterial_OpacityComponent,//201,
		    String_BSMaterial_ParamBool = STRT_String_BSMaterial_ParamBool,//202,
		    String_BSMaterial_PhysicsMaterialType = STRT_String_BSMaterial_PhysicsMaterialType,//203,
		    String_BSMaterial_ProjectedDecalSettings = STRT_String_BSMaterial_ProjectedDecalSettings,//204,
		    String_BSMaterial_Scale = STRT_String_BSMaterial_Scale,//205,
		    String_BSMaterial_ShaderModelComponent = STRT_String_BSMaterial_ShaderModelComponent,//206,
		    String_BSMaterial_ShaderRouteComponent = STRT_String_BSMaterial_ShaderRouteComponent,//207,
		    String_BSMaterial_SourceTextureWithReplacement = STRT_String_BSMaterial_SourceTextureWithReplacement,//208,
		    String_BSMaterial_StarmapBodyEffectComponent = STRT_String_BSMaterial_StarmapBodyEffectComponent,//209,
		    String_BSMaterial_TerrainSettingsComponent = STRT_String_BSMaterial_TerrainSettingsComponent,//210,
		    String_BSMaterial_TerrainTintSettingsComponent = STRT_String_BSMaterial_TerrainTintSettingsComponent,//211,
		    String_BSMaterial_TextureAddressModeComponent = STRT_String_BSMaterial_TextureAddressModeComponent,//212,
		    String_BSMaterial_TextureFile = STRT_String_BSMaterial_TextureFile,//213,
		    String_BSMaterial_TextureReplacement = STRT_String_BSMaterial_TextureReplacement,//214,
		    String_BSMaterial_TextureResolutionSetting = STRT_String_BSMaterial_TextureResolutionSetting,//215,
		    String_BSMaterial_TextureSetID = STRT_String_BSMaterial_TextureSetID,//216,
		    String_BSMaterial_TextureSetKindComponent = STRT_String_BSMaterial_TextureSetKindComponent,//217,
		    String_BSMaterial_TranslucencySettings = STRT_String_BSMaterial_TranslucencySettings,//218,
		    String_BSMaterial_TranslucencySettingsComponent = STRT_String_BSMaterial_TranslucencySettingsComponent,//219,
		    String_BSMaterial_UVStreamID = STRT_String_BSMaterial_UVStreamID,//220,
		    String_BSMaterial_UVStreamParamBool = STRT_String_BSMaterial_UVStreamParamBool,//221,
		    String_BSMaterial_VegetationSettingsComponent = STRT_String_BSMaterial_VegetationSettingsComponent,//222,
		    String_BSMaterial_WaterFoamSettingsComponent = STRT_String_BSMaterial_WaterFoamSettingsComponent,//223,
		    String_BSMaterial_WaterGrimeSettingsComponent = STRT_String_BSMaterial_WaterGrimeSettingsComponent,//224,
		    String_BSMaterial_WaterSettingsComponent = STRT_String_BSMaterial_WaterSettingsComponent,//225,
		    String_BSResource_ID = STRT_String_BSResource_ID,//228,
		    String_XMFLOAT2 = STRT_String_XMFLOAT2,//1097,
		    String_XMFLOAT3 = STRT_String_XMFLOAT3,//1098,
		    String_XMFLOAT4 = STRT_String_XMFLOAT4//1099
		  ;
}
