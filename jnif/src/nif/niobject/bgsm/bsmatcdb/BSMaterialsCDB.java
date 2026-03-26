package nif.niobject.bgsm.bsmatcdb;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import nif.niobject.bgsm.BSMaterial;
 

/**
 * 
 * More on the material database, it consists of two objects describing the list of materials, material objects and
 * components, followed by all components.
 * 
 * The first object in the CDB is of type BSMaterial.Internal.CompiledDB:
 * 
 * BSMaterial.Internal.CompiledDB { 
 * String BuildVersion 
 * Map HashMap 
 * List Collisions 
 * List Circular }
 * 
 * BuildVersion is the version of the game (currently "1.8.86.0"),
 * and HashMap is a map from BSResource.ID to uint64_t. It maps material paths (represented as CRC32 hashes of the base 
 * name without the .mat extension and the directory name, and the extension that is always "mat\0") to an unknown 64-bit hash.
 * Note that while the definition of BSResource.ID has the fields in Dir, File, Ext order, File is actually the first in the data.
 * 
 * 
 * The second object is BSComponentDB2.DBFileIndex:
 * 
 * BSComponentDB2.DBFileIndex { 
 * Map ComponentTypes 
 * List Objects 
 * List Components 
 * List Edges 
 * boolean Optimized }
 * 
 * 'ComponentTypes' maps 16-bit component type IDs to string format class names. 'Objects' is a list of all material
 * objects, in this format:
 * 
 * BSComponentDB2.DBFileIndex.ObjectInfo { 
 * BSResource.ID PersistentID 
 * BSComponentDB2.ID DBID 
 * BSComponentDB2.ID Parent 
 * boolean HasData }
 * 
 * PersistentID is similar to the keys used in HashMap above, and contains the same information for the externally
 * visible layered material (.mat) objects. DBID is the internal 32-bit ID of the object (it cannot be 0), while Parent
 * is used as the base object to construct this object from. HasData is true for all except 6 "root" objects from which
 * all others are derived, and only for those, the Parent is 0. These 6 objects are for the 6 material object types,
 * denoted by the base names "layeredmaterials", "blenders", "layers", "materials", "texturesets" and "uvstreams".
 * 
 * 'Components' links material components to material objects:
 * 
 * BSComponentDB2.DBFileIndex.ComponentInfo { 
 * BSComponentDB2.ID ObjectID 
 * UInt16 Index 
 * UInt16 Type }
 * 
 * ObjectID is one of the DBID values from the object list, Index is a component slot for component types of which there
 * can be more than one for a single object (e.g. a TextureSet object may have texture files associated with it at Index
 * = 0 to 20), otherwise it is 0, and Type is one of the 16-bit component type IDs previously defined in ComponentTypes.
 * 
 * Finally, 'Edges' describes how the material objects are organized in a tree structure:
 * 
 * BSComponentDB2.DBFileIndex.EdgeInfo { 
 * BSComponentDB2.ID SourceID 
 * BSComponentDB2.ID TargetID 
 * UInt16 Index 
 * UInt16 Type }
 * 
 * Index seems to be always 0, and Type is always the ID of BSComponentDB2.OuterEdge. This defines TargetID as
 * logically the parent of SourceID.
 * 
 * After BSMaterial.Internal.CompiledDB and BSComponentDB2.DBFileIndex, all material components are stored as OBJT
 * and DIFF chunks, the total number and order of these is exactly the same as in 'Components' above. All components of
 * derived objects are always stored after all components of their base object (the Parent in ObjectInfo), so they can
 * be copy constructed using the data that has already been read.
 * 
 * //https://forums.nexusmods.com/topic/13361451-starfields-cdb-material-database/
 * //https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsmatcdb.cpp
 * 
 */

//this is the REFLArchive but in the c++ shape, so chuck in whats needed and translate

	//CE2MaterialDB extends BSMaterialsCDB  and takes these basics and makes them "readable" as java primitives properly
	// checking type etc
public class BSMaterialsCDB extends REFLArchive {
	public static CompiledDB cdb ;
	public static DBFileIndex dbfi;
	
	//public static SparseArray<MaterialObject>	objectTable		= new SparseArray<MaterialObject>();
	//public static SparseArray<DBFileIndex.ComponentInfo2>		componentInfo;
	public static HashMap<Integer, MaterialObject>	objectTable		= new HashMap<Integer, MaterialObject>();
	public static HashMap<Integer, DBFileIndex.ComponentInfo2>		componentInfo;
	
	public BSMaterialsCDB(ByteBuffer in) {
		super(in);
		 cdb = new CompiledDB(this.objts.get(0));
		 dbfi = new DBFileIndex(this.objts.get(1));
		 
		 componentInfo = dbfi.Components;//TODO bit odd using 2 names here but clean later
		 
		 
		//objts 2+ are OBJT (incl diffs)
		for (int i = 2; i < objts.size(); i++) {
			OBJT objt = objts.get(i);
			int componentID = i - 2;// the components are loaded in the same order so the 0 index again
			DBFileIndex.ComponentInfo2 compInfo = componentInfo.get(componentID);

			int dbID = compInfo.dbID.id;
			int key = compInfo.key;
			
			int className = objt.type;
			key = (key & 0xFFFF) | (className << 16); // edit key across to be the objt not the comp id odd
			
			MaterialObject  o = findObject(dbID);
			if (o != null && className > REFLArchive.String_Unknown) {
				if (o.baseObject != null && o.baseObject.baseObject != null)
					copyBaseObject(o);//TODO: investigate why
				
				// attach this OBJT to the MaterialComponent to allow it to be loaded later
				findComponent(o, key, className).o = objt;
			}  
		}
		
		// dbfi filled the objectTable by way of creating the ObjectIfo list
		// now pull those from objectTable, do a weird copyBaseObject (not sure) 
		// then populate the matFileObjectMap hash with persistentID vs MaterialObject
		for (int i : objectTable.keySet()) {
			MaterialObject p = objectTable.get(i);
			if (p == null)
				continue;
			if (p.baseObject != null && p.baseObject.baseObject != null)
				copyBaseObject(p);//TODO: investigate why
			if (p.persistentID != null)
				storeMatFileObject(p);			
		}
		System.out.println("matFileObjectMap loaded, " + matFileObjectMap.size());
		
/*		 
	 

	  int  chunkType;
	  int  objectInfoSize = 21;
	  int  componentID = 0;
	  int  componentCnt = 0;

	  while ((chunkType = cdbFile.readChunk(chunkBuf)) != 0U)
	  {
	    std::uint32_t className = BSReflStream::String_None;
	     
	    className = cdbFile.findString(chunkBuf.readUInt32Fast());
	    
	 
	    std::uint32_t n = 0U;
	    if (chunkBuf.size() >= 8)
	      n = chunkBuf.readUInt32Fast();
	  
	    
	    
	    // so the prep parts at the top are all just simply RELF structures and all objt are materials and their components
	    
	    // notice that these are loadd last, so the code below has filled up the componentInfo array etc
	    
	    if (chunkType == BSReflStream::ChunkType_DIFF ||
	        chunkType == BSReflStream::ChunkType_OBJT)  
	    {
	      bool    isDiff = (chunkType == BSReflStream::ChunkType_DIFF);
	      if (componentID < componentCnt) 
	      {
	        std::uint32_t dbID = componentInfo[componentID].first;
	        std::uint32_t key = componentInfo[componentID].second; // key is a fo76 construct for refng into a map, 
	        key = (key & 0xFFFFU) | (className << 16);
	        MaterialObject  *i = findObject(dbID);
	        if (i && className > BSReflStream::String_Unknown)
	        {
	          if (i->baseObject && i->baseObject->baseObject)
	            copyBaseObject(*i);
	          loadItem(findComponent(*i, key, className).o,
	                   cdbFile, chunkBuf, isDiff, className);
	        }
	        componentID++;
	      }
	      continue;
	    }
	    
	    if (chunkType != BSReflStream::ChunkType_LIST)
	      continue;
	    
	  }
	  for (MaterialObject *p : objectTable)
	  {
	    if (!p)
	      continue;
	    if (p->baseObject && p->baseObject->baseObject)
	      copyBaseObject(*p);
	    if (p->persistentID)
	      storeMatFileObject(p);
	  }
	  
	  */
		System.out.println("Cdb converted to BSMaterialsCDB");
	}
	
	//sorry excuse the madnees, I'm gonna sneak a CE2Material back across the BSMaterial interface
	public static class CE2BSMaterial extends BSMaterial {
		public CE2Material ce2material;
	}

	public BSMaterial getMaterialFileCDB(String fileName) throws IOException {
		BSResourceID incomingFilename = new BSResourceID(fileName);
		CE2MaterialObject o = CE2MaterialDB.materialObjectMap.get(incomingFilename);
		if (o == null) {
			o = CE2MaterialDB.findMaterialObject(getMaterial(incomingFilename));
		}
		CE2BSMaterial ret = new CE2BSMaterial();
		ret.ce2material = (CE2Material)o;
		return ret;

		//   
		// position where a request for a hash return a BSMaterial sub class of some sort, 
		//possibly a sub of a super calss without all the BSMAterial variables?
		//BSMaterialDataBGEM and BSMaterialDataBGSM are subs of BSMaterial

		// I nedd to check the strucutres above, but also actually read into variables the 
		// data in the REFL side, gonna be a nightmare
		//possibly just write out all the classes and then make actual classes from them, and read into those 
		// somehow, but the diff gear is a total pain
		// otherwise it's a total reflection shitfight
		// possibly it's all static ids for variables and types and I have a program that just knows those things
		// so what's the advantage of this damn XML like cdb crap?

		/*	byte[] matBytes = cdb.find(hash);		
			ByteBuffer in = ByteBuffer.wrap(matBytes);		
			
			byte[] buf = new byte[4];
			in.get(buf);			
			String headerString = new String(buf);
			if (headerString.equals("BGEM")) {
				BSMaterial m = new BSMaterialDataBGEM();
				m.readFile(in);
				return m;
			} else if (headerString.equals("BGSM")) {
				BSMaterial m = new BSMaterialDataBGSM();
				m.readFile(in);
				return m;
			} else {
				throw new RuntimeException("BAD MaterialsCDB hash: " + hash);
			}*/

	}


	void copyBaseObject(MaterialObject o)
	{
		MaterialObject p = (MaterialObject)(o.baseObject);
		if (p.baseObject.baseObject != null)
			copyBaseObject(p);
		o.baseObject = p.baseObject;
		o.components = null;
		MaterialComponent prv = null;//(o.components);// this was MaterialComponent **prv = &(o.components);
		for (MaterialComponent i = p.components; i != null; i = i.next) {
			MaterialComponent tmp = new MaterialComponent();
			tmp.key = i.key;
			tmp.className = i.className;
			tmp.o = i.o;

			//if (tmp.o != null)
			//	copyObject((tmp.o));
			tmp.next = null;

			//  some c++ linked list contortion, I think inseret it into the linked list but o.components is head
			//prv = tmp;//*prv = tmp;
			//prv = (tmp.next);//prv = &(tmp->next);
			if (prv == null) {
				o.components = tmp;
			} else {
				prv.next = tmp;
			}
			prv = tmp;
			
		}
	}

	//This appears to do nothing?? Not calling it, o is alive and fine
	/*void copyObject(CDBObject o) {
		CDBObject[] ps = o.children();//CDBObject **p = o.children();
		for (int pi = 0; pi < ps.length; pi++) {
			CDBObject p = ps[pi];
			if (p != null)
				copyObject(p);
		}
	}*/
	

	/*BSComponentDB2.ID {
		uint Value
	}*/
 
	public static class BSComponentDB2_ID {
		int id;
		public BSComponentDB2_ID(REFL refl) {
			//check for being type 74   
			if (refl != null && refl.type == REFLArchive.STRT_String_BSComponentDB2_ID) {
				id = refl.children[0].intValue();					
			} else {
				throw new RuntimeException("Bad readVal");
			}
		}
	}

	/*
	BSMaterial.Internal.CompiledDB {
		String BuildVersion
		Map HashMap
		List Collisions - empty
		List Circular - empty
	}*/
	public static class CompiledDB {
		public String BuildVersion;//currently "1.8.86.0"
		public MAPC HashMap;
		public CompiledDB(OBJT objt) {
			BuildVersion = objt.children[0].stringValue();
			HashMap = (MAPC)objt.children[1];
		}
	}

	/*BSComponentDB2.DBFileIndex {
		Map ComponentTypes
		List Objects =MaterialObjects
		List Components =MaterialComponants 
		List Edges
		boolean Optimized
	}
 
	 * 
	 * 'ComponentTypes' maps 16-bit component type IDs to string format class names. (followed by an int and then a bool)
	 * 

	*/
	
	// for Components note I DO have a class called ComponentInfo below
	public static class DBFileIndex {
		public HashMap<Short, String>		ComponentTypes;
		public ArrayList<ObjectInfo>		Objects;
		public HashMap<Integer, ComponentInfo2>	Components;
		public ArrayList<EdgeInfo>			Edges;
		public boolean						Optimized;

		public  DBFileIndex(OBJT objt) {
			ComponentTypes = convertComponentTypes((MAPC)objt.children[0]);
			Objects = ObjectInfo.convertObjectInfo((LIST)objt.children[1]);
			Components = ComponentInfo2.convertObjectInfo((LIST)objt.children[2]);
			Edges = EdgeInfo.convertEdgeInfo( (LIST)objt.children[3]);
			Optimized = objt.children[4].boolValue();
		}
		
		

		
		public static HashMap<Short, String> convertComponentTypes(MAPC map) {
			HashMap<Short, String> returnList = new HashMap<Short, String>(map.map.size());
			for (Entry<CDBObject, CDBObject> o : map.map.entrySet()) {				 
				Short k = o.getKey().shortValue();
				String v = ((USER)o.getValue().children[0]).zerofieldsValue.stringValue();
				//children[1]=Short, children[2]=boolean
				// 2 other children ignored fornow
				returnList.put(k, v);
				 
			}
			return returnList;
		}
	 
		
		/* * 'Objects' is a list of all material objects, in this format: 
		 * BSComponentDB2.DBFileIndex.ObjectInfo { 
		 * BSResource.ID PersistentID 
		 * BSComponentDB2.ID DBID  these sorts of things => readBSComponentDB2ID(p, (byte)2);
		 * BSComponentDB2.ID Parent 
		 * boolean HasData }
		 * 
		 * PersistentID is similar to the keys used in HashMap above, and contains the same information for the externally
		 * visible layered material (.mat) objects. DBID is the internal 32-bit ID of the object (it cannot be 0), while Parent
		 * is used as the base object to construct this object from. HasData is true for all except 6 "root" objects from which
		 * all others are derived, and only for those, the Parent is 0. These 6 objects are for the 6 material object types,
		 * denoted by the base names "layeredmaterials", "blenders", "layers", "materials", "texturesets" and "uvstreams".
		 * */
	public static class ObjectInfo {
		
		public static boolean hasParentBSResourceID = false;
		
		BSResourceID persistentID;  
		BSComponentDB2_ID dbID;
		BSComponentDB2_ID parentID;
		BSResourceID parentID2; // version >= 1.11.33.0 also stores the parent ID as BSResource::ID
		boolean hasData = false;

		public ObjectInfo(REFL refl) {
			//https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsmatcdb.cpp#L814

			persistentID = new BSResourceID((REFL)refl.children[0]);
			dbID = new BSComponentDB2_ID((REFL)refl.children[1]);
			parentID = new BSComponentDB2_ID((REFL)refl.children[2]);

			//optional ParentID now
			if (hasParentBSResourceID) {
				parentID2 = new BSResourceID((REFL)refl.children[3]);
				hasData = refl.children[4].boolValue();
			} else {
				hasData = refl.children[3].boolValue();
			}

			//now we create the MaterialObject to be loaded by the chunk looper in a while and we put it in
			// the materialObject array

			if (findMatFileObject(persistentID) != null && hasData)
				return; // ignore duplicate objects

			MaterialObject o = new MaterialObject();
			if (objectTable.get(dbID.id) != null)
				System.err.println("duplicate object ID in material database");
			objectTable.put(dbID.id, o);
			o.persistentID = persistentID;
			o.dbID = dbID.id;
			o.baseObject = findObject(parentID.id);
			if (o.baseObject == null && hasParentBSResourceID && hasData) {
				o.baseObject = findMatFileObject(parentID2);
			}
			o.components = null;
			o.parent = null;
			o.children = null;
			o.next = null;
		}
		
		public static ArrayList<ObjectInfo> convertObjectInfo(LIST list) {
			ArrayList<ObjectInfo> returnList = new ArrayList<ObjectInfo>(list.list.size());
			// version >= 1.11.33.0 also stores the parent ID as BSResource::ID after the dbID
			CLAS clas = CLASLookup.get(list.list.get(0).type);
			hasParentBSResourceID = clas.fields.length > 4;
			
			for (CDBObject o : list.list) {
				if (o instanceof REFL) {
					REFL r = (REFL)o;
					returnList.add(new ObjectInfo(r));					 
				}
			}
			return returnList;
			/* 
			 * n has been read off as chunk size
			 *  unsigned int  objectInfoSize = 21;
			 *  
			 *  during class loading if ObjectInfo class is 5+ field then objectInfos are 33 size
			 *  if (className == BSReflStream::String_BSComponentDB2_DBFileIndex_ObjectInfo &&
            	fieldCnt > 4)
		        {
		          // version >= 1.11.33.0 also stores the parent ID as BSResource::ID
		          objectInfoSize = 33; // or 1 field large of a BSResource_ID
		        }
			 *  
			 *  
			 *  if (className == BSReflStream::String_BSComponentDB2_DBFileIndex_ObjectInfo)
		    {
		      if (n > ((chunkBuf.size() - chunkBuf.getPosition()) / objectInfoSize))
		        errorMessage("unexpected end of LIST chunk in material database");
		      const unsigned char *objectInfoPtr = chunkBuf.getReadPtr();
		      std::uint32_t maxObjID = 0U;
		      for (std::uint32_t i = 0U; i < n; i++)
		      {
		        std::uint32_t dbID =  FileBuffer::readUInt32Fast(  objectInfoPtr + (i * objectInfoSize + 12U));
		        maxObjID = std::max(maxObjID, dbID);
		      }
		      if (maxObjID > 0x007FFFFFU)
		        errorMessage("object ID is out of range in material database");
		      objectTable.resize(maxObjID + 1U, nullptr);
		      objectTablePtr = objectTable.data();
		      objectTableSize = objectTable.size();
		      for (std::uint32_t i = 0U; i < n; i++, objectInfoPtr += objectInfoSize)
		      {
		        BSResourceID  persistentID;
		        persistentID.file = FileBuffer::readUInt32Fast(objectInfoPtr);
		        persistentID.ext = FileBuffer::readUInt32Fast(objectInfoPtr + 4);
		        persistentID.dir = FileBuffer::readUInt32Fast(objectInfoPtr + 8);
		        std::uint32_t dbID = FileBuffer::readUInt32Fast(objectInfoPtr + 12);
		        if (!(dbID && dbID <= maxObjID))
		          errorMessage("invalid object ID in material database");
		        bool    hasData = bool(objectInfoPtr[objectInfoSize - 1U]);
		        if (findMatFileObject(persistentID) && hasData)
		          continue;                     // ignore duplicate objects
		        MaterialObject  *o = allocateObjects< MaterialObject >(1);
		        if (objectTable[dbID])
		          errorMessage("duplicate object ID in material database");
		        objectTable[dbID] = o;
		        o->persistentID = persistentID;
		        o->dbID = dbID;
		        o->baseObject =
		            findObject(FileBuffer::readUInt32Fast(objectInfoPtr + 16));
		        if (!o->baseObject) [[unlikely]]
		        {
		          if (objectInfoSize >= 33U) //[[likely]] // version >= 1.11.33.0
		          {
		            BSResourceID  parentID;
		            parentID.file = FileBuffer::readUInt32Fast(objectInfoPtr + 20);
		            parentID.ext = FileBuffer::readUInt32Fast(objectInfoPtr + 24);
		            parentID.dir = FileBuffer::readUInt32Fast(objectInfoPtr + 28);
		            if (hasData)
		              o->baseObject = findMatFileObject(parentID);
		          }
		        }
		        o->components = nullptr;
		        o->parent = nullptr;
		        o->children = nullptr;
		        o->next = nullptr;
		      }
		    }*/
			 
			}
		}
			
			
		/*
		 * 
		 * 'Components' links material components to material objects: 
		 * BSComponentDB2.DBFileIndex.ComponentInfo { 
		 * BSComponentDB2.ID ObjectID 
		 * UInt16 Index 
		 * UInt16 Type }
		 * 
		 * ObjectID is one of the DBID values from the object list, Index is a component slot for component types of which there
		 * can be more than one for a single object (e.g. a TextureSet object may have texture files associated with it at Index
		 * = 0 to 20), otherwise it is 0, and Type is one of the 16-bit component type IDs previously defined in ComponentTypes.
		 * */
		public static class  ComponentInfo2 {
			public BSComponentDB2_ID dbID;
			public short index;
			public short type;
			
			public int key; // derived
			
			public ComponentInfo2(REFL refl) {
				dbID = new BSComponentDB2_ID((REFL)refl.children[0]);
				index =  refl.children[1].shortValue();
				type =  refl.children[2].shortValue();					
				key = (type << 16) | index;
			}
			
			
			public static HashMap<Integer, ComponentInfo2> convertObjectInfo(LIST list) {
				HashMap<Integer, ComponentInfo2> returnList = new HashMap<Integer, ComponentInfo2>(list.list.size());
	
				int idx = 0;
				for (CDBObject o : list.list) {
					if (o instanceof REFL) {
						REFL r = (REFL)o;
						returnList.put(idx++, new ComponentInfo2(r));
					}
				}
				return returnList;
			}
		/* 
		 *  n has been read off as chunk size so the n-- iterates trough the list
		 * 
		 * else if (className   == BSReflStream::String_BSComponentDB2_DBFileIndex_ComponentInfo)
		    {
		      componentID = 0;
		      componentCnt = 0;
		      while (n--)
		      {
		        std::uint32_t dbID = chunkBuf.readUInt32();
		        std::uint32_t key = chunkBuf.readUInt32();      // (type << 16) | index
		        componentInfo.emplace_back(dbID, key);
		        componentCnt++;
		      }
		    }*/
		}
		
		/*
		 * 
		 * 
		 * Finally, 'Edges' describes how the material objects are organized in a tree structure:  
		 * BSComponentDB2.DBFileIndex.EdgeInfo { 
		 * BSComponentDB2.ID SourceID 
		 * BSComponentDB2.ID TargetID 
		 * UInt16 Index 
		 * UInt16 Type }
		 * 
		 * Index seems to be always 0, and Type is always the ID of BSComponentDB2.OuterEdge. This defines TargetID as
		 * logically the parent of SourceID.
		 */
		public static class  EdgeInfo {
			
			BSComponentDB2_ID	sourceID;
			BSComponentDB2_ID	targetID;
			short				index;
			short				type;

			public EdgeInfo(REFL refl) {
				sourceID = new BSComponentDB2_ID((REFL)refl.children[0]);
				targetID = new BSComponentDB2_ID((REFL)refl.children[1]);
				index = refl.children[2].shortValue();
				type = refl.children[3].shortValue();
				
				MaterialObject o = findObject(sourceID.id);
				MaterialObject p = findObject(targetID.id);
				if (o != null && p != null) {
					if (o.parent != null) {
						throw new RuntimeException(
								"object 0x%08X has multiple parents in " + "material database " + sourceID);
					}
					o.parent = p;
					o.next = p.children;
					p.children = o;
				}
			}
			
			public static ArrayList<EdgeInfo> convertEdgeInfo(LIST list) {
				ArrayList<EdgeInfo> returnList = new ArrayList<EdgeInfo>(list.list.size());				 
				
				for (CDBObject o : list.list) {
					if (o instanceof REFL) {
						REFL r = (REFL)o;
						returnList.add(new EdgeInfo(r));					 
					}
				}
				return returnList;
			}
		/* 
		 * n has been read off as chunk size
		 *  else if (className == BSReflStream::String_BSComponentDB2_DBFileIndex_EdgeInfo)
		    {
		      if (n > ((chunkBuf.size() - chunkBuf.getPosition()) / 12))
		        errorMessage("unexpected end of LIST chunk in material database");
		      const unsigned char *edgeInfoPtr = chunkBuf.getReadPtr();
		      for (std::uint32_t i = 0U; i < n; i++)
		      {
		        // uint32_t sourceID, uint32_t targetID, uint16_t index, uint16_t type
		        std::uint32_t sourceID =
		            FileBuffer::readUInt32Fast(edgeInfoPtr + (i * 12U));
		        std::uint32_t targetID =
		            FileBuffer::readUInt32Fast(edgeInfoPtr + (i * 12U + 4U));
		        MaterialObject  *o = findObject(sourceID);
		        MaterialObject  *p = findObject(targetID);
		        if (o && p)
		        {
		          if (o->parent)
		          {
		            throw NifSkopeError("object 0x%08X has multiple parents in "
		                                "material database", (unsigned int) sourceID);
		          }
		          o->parent = p;
		          o->next = p->children;
		          p->children = o;
		        }
		      }
		    }*/
		}
	}
	
	

	public static class BSResourceID {
		long file; // CRC32 of base name (not including the extension)
		long ext=0x0074616D;  // extension (0x0074616D for "mat\0") (decimal of that is 7627117)
		long dir;  // CRC32 of directory name (e.g. "materials\\test")
 			
		public BSResourceID(REFL refl) {
			//check for being type 74   
			if (refl != null && refl.type == REFLArchive.STRT_String_BSResource_ID) {
				file = refl.children[0].uintValue() & 0x00000000FFFFFFFFL;
				ext = refl.children[1].uintValue() & 0x00000000FFFFFFFFL;
				dir = refl.children[2].uintValue() & 0x00000000FFFFFFFFL;
			} else {
				throw new RuntimeException("Bad BSResourceID type");
			}
		}
		
		public BSResourceID(String fileName) {
			//https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsmatcdb.cpp#L795				
			//NOTE!! does not appear to match code from HashCode in BSAManager

			int baseNamePos = fileName.lastIndexOf('/');
			int baseNamePos2 = fileName.lastIndexOf('\\');
			int extPos = fileName.lastIndexOf('.');
			if (baseNamePos == -1 || (baseNamePos2 != -1 && baseNamePos2 > baseNamePos)) {
				baseNamePos = baseNamePos2;
			}
			if (extPos == -1 || (baseNamePos != -1 && extPos < baseNamePos)) {
				extPos = fileName.length();
			}
			int i = 0;
			long[] crcValue = new long[1];
			byte[] fileNamedata = getBytesFast(fileName);
			if (baseNamePos != -1) {
				// directory name
				for (; i < baseNamePos; i++) {
					byte c = fileNamedata[i];
					if (c >= 0x41 && c <= 0x5A) // 'A' - 'Z'
						c = (byte)(c | 0x20); // convert to lower case
					else if (c == 0x2F) // '/'
						c = 0x5C; // convert to '\\'
					hashFunctionCRC32(crcValue, c);
				}
				i++;//skip the '\' between dir and file
			}
			dir = crcValue[0];
			crcValue[0] = 0;
			for (; i < extPos; i++) {
				// base name
				byte c = fileNamedata[i];
				if (c >= 0x41 && c <= 0x5A) // 'A' - 'Z'
					c = (byte)(c | 0x20); // convert to lower case
				hashFunctionCRC32(crcValue, c);
			}
			file = crcValue[0];

			switch (fileNamedata.length - i) {
				case 0:
				case 1:
					ext = 0;
					break;
				case 2:
					ext = fileNamedata[i + 1];
					break;
				case 3:
					ext = fileNamedata[i + 2] << 8 | fileNamedata[i + 1];
					break;
				case 4:
					ext = fileNamedata[i + 3] << 16 | fileNamedata[i + 2] << 8 | fileNamedata[i + 1];
					break;
				default:
					ext = fileNamedata[i + 4] << 24 | fileNamedata[i + 3] << 16 | fileNamedata[i + 2] << 8
							| fileNamedata[i + 1];
					break;
			}
			// convert extension to lower case
			ext = ext | ((ext >> 1) & 0x20202020);
		}
		
		//h is a 1 element pointer
		void hashFunctionCRC32(long[] h, byte c) {
			h[0] = (h[0] >> 8) ^ crc32Table_EDB88320[(int)((h[0] ^ c) & 0xFF)];
		}

		@Override
		public int hashCode() {
			return (int)(this.dir | this.file | this.ext);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof BSResourceID)
				return this.dir == ((BSResourceID)o).dir	&& this.file == ((BSResourceID)o).file
						&& this.ext == ((BSResourceID)o).ext;
			return false;
		}
	}
		
		
	// So every item is a CDBObject down amoungst the primitives
	// and theList are also CDBObject and the compound objects are also CDBObjects
	// where children are the fields, bt also the list contents
	// so I should really just of the fields as Objects and the compounds as something higher 
	// even each field is a cdbobject, but it keeps it's type 
	public static class CDBObject {

		//for REFL
		protected CDBObject() {
			
		}
		// for simple type value pairs
		public CDBObject(int fieldType, Object value) {
			this.type = fieldType;
			this.value = value;
		}


		public int			type = REFLArchive.String_Unknown;		// BSReflStream::stringTable[] index

		// Size of children[] for compound types (struct, ref, list and map).
		// For maps, childCnt = 2 * elements, and data.children[N * 2] and
		// data.children[N * 2 + 1] contain the key and value for element N.
		public int			childCnt() {return children.length;}

		//this is the field primitive data, but could be structured, however it is never the list or mapc data			   
		CDBObject[]	children;

		// for type == String_List, String_Map, String_Ref and structures
		CDBObject[] children() {
			return children;
		}

		//the value part of this is only primitive types
		private Object	value;

		// for type == BSReflStream::String_Bool
		boolean boolValue() {
			return ((Boolean)value).booleanValue();
		}
		byte byteValue() {
			return ((Byte)value).byteValue();
		}
		short shortValue() {
			return ((Short)value).shortValue();
		}
		// for type == String_Int8, String_Int16 and String_Int32
		int intValue() {
			return ((Integer)value).intValue();
		}

		// for type == String_UInt8, String_UInt16 and String_UInt32
		int uintValue() {
			return ((Integer)value).intValue();
		}

		// for type == String_Int64
		long int64Value() {
			return ((Long)value).longValue();
		}

		// for type == String_UInt64
		long uint64Value() {
			return ((Long)value).longValue();
		}

		// for type == String_Float
		float floatValue() {
			return ((Float)value).floatValue();
		}

		// for type == String_Double
		double doubleValue() {
			return ((Double)value).intValue();
		}

		// for type == String_String
		String stringValue() {
			return (String)value;
		}

		// for type == String_BSComponentDB2_ID
		MaterialObject linkedObject;

		MaterialObject linkedObject() {
			//example of use here readBSComponentDB2ID(p, (byte)2);
			if (type == REFLArchive.STRT_String_BSComponentDB2_ID) {
				if (linkedObject == null) {
					BSComponentDB2_ID targetID = new BSComponentDB2_ID((REFL)this);
					linkedObject = findObject(targetID.id); //lazy record this object
				}
				return linkedObject;
			} else {
				System.err.println(" linkedObject() type != REFLArchive.STRT_String_BSComponentDB2_ID " + type);
				return null;
			}
		}
	}		  
	
	// these are the things defined in the BSComponentDB2.DBFileIndex {  List Objects =MaterialObjects
	// notice the nonCE2 version are in refl format, but the CE2MaterialObject is a real object
	// made out of the refl data lists. CE2MaterialObject has the inheritance group of 6 object types
	
	//MaterialComponent does not have any class hierarchy, it is used to load the parts of the 
	//MaterialObject into the CE2MaterialObject, via the DBFileIndex.Components look up
	public static class MaterialObject { 
		BSResourceID			persistentID; 
		int						dbID; 
		MaterialObject			baseObject; // linked list generally (set to the highest one?)
		MaterialComponent		components; // linked list of MaterialComponents, each has a next pointer
		MaterialObject			parent;
		MaterialObject			children; // linked list, using next and parent somehow
		MaterialObject			next;

		//currently unused anywhere
		MaterialObject getNextChildObject() {
			MaterialObject i = children;
			if (i != null)
				return i;
			for (i = this; i.next != null && i.parent != null; i = i.parent)
				;
			return i.next;
		}
	}

	  
	public static class MaterialComponent {
		int					key;		// (type << 16) | index
		int					className;
		CDBObject			o;
		MaterialComponent	next;
		//  boolean operator<(const MaterialComponent& r) const
		//   {
		//     return (key < r.key);
		//  }
	}
		
	/**
	 * MaterialObject should already have components in a linked list called components
	 * this will step through each component until it finds the matching key (being index and type combo)
	 * if found it is returned otherwise create a MaterialCompnent, set it's key and className(int) 
	 * and add it to the end of the linked list		 
	 * 
	 * @param o MaterialObject in refl format
	 * @param key to find if the component is already in the list
	 * @param className used to construct the required component
	 * @return
	 */
	public static MaterialComponent findComponent(MaterialObject o, int key, int className) {
		MaterialComponent prv = null;
		MaterialComponent i = o.components;

		for (; i != null && i.key != key; i = i.next)
			prv = i;
		if (i != null && i.key == key)
			return i;
		MaterialComponent p = new MaterialComponent();
		p.key = key;
		p.className = className;
		p.o = null;
		p.next = i;
		if (prv == null)
			o.components = p;
		else
			prv.next = p;
		return p;
	}
	
	public static MaterialObject getMaterial(BSResourceID objectID) {
		return findMatFileObject(objectID);
	}

	public static MaterialObject getMaterial(String materialPath) {
		return findMatFileObject(new BSResourceID(materialPath));
	}

	static HashMap<BSResourceID, MaterialObject> matFileObjectMap = new HashMap<BSResourceID, MaterialObject>();    

	public static MaterialObject findMatFileObject(BSResourceID objectID) {
		return matFileObjectMap.get(objectID);
	}

	public static void storeMatFileObject(MaterialObject o) {
		matFileObjectMap.put(o.persistentID, o);
	}
	 
	
	public static MaterialObject findObject(int dbID) {
		if (dbID == 0)
			return null;
		return objectTable.get(dbID);
	}
	 

	// CE2Material (.mat file), object type 1
	//   |
	//   +--(BSMaterial.BlenderID)-- CE2Material.Blender, object type 2
	//   |      |
	//   |      +--(BSMaterial.UVStreamID)-- CE2Material.UVStream, object type 6
	//   +--(BSMaterial.LayerID)-- CE2Material.Layer, object type 3
	//   |      |
	//   |      +--(BSMaterial.UVStreamID)-- CE2Material.UVStream, object type 6
	//   |      |
	//   |      +--(BSMaterial.MaterialID)-- CE2Material.Material, object type 4
	//   |             |
	//   |       (BSMaterial.TextureSetID)-- CE2Material.TextureSet, object type 5
	//   +--(BSMaterial.LODMaterialID)-- LOD material, object type 1

	public static class CE2MaterialObject {
		// 0: CE2MaterialObject
		// 1: CE2Material
		// 2: CE2Material.Blender
		// 3: CE2Material.Layer
		// 4: CE2Material.Material
		// 5: CE2Material.TextureSet
		// 6: CE2Material.UVStream
		int								type;
		MaterialObject	cdbObject;	// valid if type > 0

		//TODO: readString returns a String, which is correct for primitives of type String, but the consts
		//want to store and int index to the consts STRT, and name was an int like that
		String							name;
		CE2MaterialObject				parent;

		CE2MaterialObject(int t) {
			type = (t);
			cdbObject = (null);
			name = null;//-1;
			parent = (null);
		}
	}

	public static class  CE2Material extends CE2MaterialObject   // object type 1
	{
		
		public static int 
		    // flag values can be combined (bitwise OR) with NIFFile.NIFTriShape.flags
		    Flag_HasOpacity = 0x00000001,
		    Flag_AlphaVertexColor = 0x00000002
		  ;
		  public static int Flag_IsEffect = 0x00000004;
		  public static int Flag_IsDecal = 0x00000008;
		  public static int Flag_TwoSided = 0x00000010;
		  public static int Flag_IsVegetation = 0x00000020;
		  public static int Flag_LayeredEmissivity = 0x00000040;
		  public static int //repeat of emissive Flag_Glow = 0x00000080,
		  Flag_Emissive = 0x00000080;
		  public static int Flag_Translucency = 0x00000100;
		  public static int Flag_AlphaDetailBlendMask = 0x00000200;
		  public static int Flag_DitheredTransparency = 0x00000400;
		  public static int // Flag_TSOrdered = 0x00000800,
		  Flag_AlphaBlending = 0x00001000;
		  public static int // Flag_TSVertexColors = 0x00002000,
		  Flag_IsWater = 0x00004000;
		  public static int // Flag_TSHidden = 0x00008000,
		  Flag_HasOpacityComponent = 0x00010000;
		  public static int Flag_OpacityLayer2Active = 0x00020000;
		  public static int Flag_OpacityLayer3Active = 0x00040000;
		  public static int Flag_IsTerrain = 0x00080000;
		  public static int Flag_IsHair = 0x00100000;
		  public static int Flag_UseDetailBlender = 0x00200000;
		  public static int Flag_LayeredEdgeFalloff = 0x00400000;
		  public static int Flag_GlobalLayerData = 0x00800000
		  // Flag_TSMarker = 0x01000000
;
		  public static int 
		    EffectFlag_UseFalloff = 0x00000001,
		    EffectFlag_UseRGBFalloff = 0x00000002,
		    EffectFlag_VertexColorBlend = 0x00000040,
		    EffectFlag_IsAlphaTested = 0x00000080,
		    EffectFlag_NoHalfResOpt = 0x00000200,
		    EffectFlag_SoftEffect = 0x00000400,
		    EffectFlag_EmissiveOnly = 0x00001000,
		    EffectFlag_EmissiveOnlyAuto = 0x00002000,
		    EffectFlag_DirShadows = 0x00004000,
		    EffectFlag_NonDirShadows = 0x00008000,
		    EffectFlag_IsGlass = 0x00010000,
		    EffectFlag_Frosting = 0x00020000;//U; unsigned means nothing for an int? unless if was FF in first byte?
		  public static int EffectFlag_ZTest = 0x00200000;
		  public static int EffectFlag_ZWrite = 0x00400000;
		  public static int EffectFlag_BacklightEnable = 0x01000000;
		  public static int EffectFlag_RenderBeforeClouds = 0x10000000;
		  public static int EffectFlag_MVFixup = 0x20000000;
		  public static int EffectFlag_MVFixupEdgesOnly = 0x40000000;
		  public static int EffectFlag_RenderBeforeOIT = 0x80000000;
		  public static int 
		    maxLayers = 6,
		    maxBlenders = 5,
		    maxLODMaterials = 3;
		  
		  
		  
		  //public static  String emptyStringView = "";
		  private static  CE2Material  defaultLayeredMaterial;
		  private static  Blender defaultBlender;
		  private static  Layer   defaultLayer;
		  private static  Material    defaultMaterial;
		  private static  TextureSet  defaultTextureSet;
		  private static  UVStream    defaultUVStream;

			public static CE2Material defaultLayeredMaterial() {
				if (defaultLayeredMaterial == null)
					defaultLayeredMaterial = new CE2Material();
				return defaultLayeredMaterial;
			}

			public static Blender defaultBlender() {
				if (defaultBlender == null)
					defaultBlender = new Blender();
				return defaultBlender;
			}

			public static Layer defaultLayer() {
				if (defaultLayer == null)
					defaultLayer = new Layer();
				return defaultLayer;
			}

			public static Material defaultMaterial() {
				if (defaultMaterial == null)
					defaultMaterial = new Material();
				return defaultMaterial;
			}

			public static TextureSet defaultTextureSet() {
				if (defaultTextureSet == null)
					defaultTextureSet = new TextureSet();
				return defaultTextureSet;
			}

			public static UVStream defaultUVStream() {
				if (defaultUVStream == null)
					defaultUVStream = new UVStream();
				return defaultUVStream;
			}
		  public int flags;
		  public int layerMask;
		  public Layer[]   layers = new Layer[maxLayers];
		  public float   alphaThreshold;
		  // index to shaderModelNames, default: 31 ("BaseMaterial")
		  public byte shaderModel;
		  public byte alphaSourceLayer;
		  // 0 = "Linear" (default), 1 = "Additive", 2 = "PositionContrast", 3 = "None"
		  public byte alphaBlendMode;
		  public byte alphaVertexColorChannel;
		  public float   alphaHeightBlendThreshold;
		  public float   alphaHeightBlendFactor;
		  public float   alphaPosition;
		  public float   alphaContrast;
		  public UVStream  alphaUVStream;               // can be NULL
		  // 0 = "Deferred" (default), 1 = "Effect", 2 = "PlanetaryRing",
		  // 3 = "PrecomputedScattering", 4 = "Water"
		  public byte shaderRoute;
		  public byte opacityLayer1;
		  public byte opacityLayer2;
		  public byte opacityBlender1;
		  // 0 = "Lerp", 1 = "Additive", 2 = "Subtractive", 3 = "Multiplicative"
		  public byte opacityBlender1Mode;
		  public byte opacityLayer3;
		  public byte opacityBlender2;
		  public byte opacityBlender2Mode;
		  public float   specularOpacityOverride;
		  // 0 = "None" (default), 1 = "Carpet", 2 = "Mat",
		  // 3 = "MaterialGroundTileVinyl", 4 = "MaterialMat",
		  // 5 = "MaterialPHYIceDebrisLarge", 6 = "Metal", 7 = "Wood"
		  public byte physicsMaterialType;
		  public Blender[] blenders = new Blender[maxBlenders];
		  public CE2Material[] lodMaterials = new CE2Material[maxLODMaterials];
		  // the following pointers are valid if the corresponding bit in flags is set
		  public EffectSettings  effectSettings;
		  public EmissiveSettings  emissiveSettings;
		  public LayeredEmissiveSettings layeredEmissiveSettings;
		  public TranslucencySettings  translucencySettings;
		  public DecalSettings   decalSettings;
		  public VegetationSettings  vegetationSettings;
		  public DetailBlenderSettings detailBlenderSettings;
		  public LayeredEdgeFalloff  layeredEdgeFalloff;
		  public WaterSettings   waterSettings;
		  public GlobalLayerData globalLayerData;
		  public HairSettings  hairSettings;
	   
	 	  
		
		public CE2Material() {
		  super(1);
		    flags=(0);
		    layerMask=(0);
		    alphaThreshold=(1.0f / 3.0f);
		    shaderModel=(31);            // "BaseMaterial"
		    alphaSourceLayer=(0);
		    alphaBlendMode=(0);          // "Linear"
		    alphaVertexColorChannel=(0); // "Red"
		    alphaHeightBlendThreshold=(0.0f);
		    alphaHeightBlendFactor=(0.05f);
		    alphaPosition=(0.5f);
		    alphaContrast=(0.0f);
		    alphaUVStream=(null);
		    shaderRoute=(0);             // "Deferred"
		    opacityLayer1=(0);           // "MATERIAL_LAYER_0"
		    opacityLayer2=(1);           // "MATERIAL_LAYER_1"
		    opacityBlender1=(0);         // "BLEND_LAYER_0"
		    opacityBlender1Mode=(0);     // "Lerp"
		    opacityLayer3=(2);           // "MATERIAL_LAYER_2"
		    opacityBlender2=(1);         // "BLEND_LAYER_1"
		    opacityBlender2Mode=(0);     // "Lerp"
		    specularOpacityOverride=(0.0f);
		    physicsMaterialType=(0);     // "None"
		    effectSettings=(null);
		    emissiveSettings=(null);
		    layeredEmissiveSettings=(null);
		    translucencySettings=(null);
		    decalSettings=(null);
		    vegetationSettings=(null);
		    detailBlenderSettings=(null);
		    layeredEdgeFalloff=(null);
		    waterSettings=(null);
		    globalLayerData=(null);
		    hairSettings=(null);
		 
		  for (int i = 0; i < maxLayers; i++)
		    layers[i] = null;
		  for (int i = 0; i < maxBlenders; i++)
		    blenders[i] = null;
		  for (int i = 0; i < maxLODMaterials; i++)
		    lodMaterials[i] = null;
		}
		 
	
		void setFlags(int m, boolean n) {
			flags = n ? (flags & ~m) : (flags | m);//(flags & ~m) | ((0U - std.uint32_t(n)) & m);	
		}
	
	 
		public static class UVStream extends CE2MaterialObject // object type 6
		{
			// U scale, V scale, U offset, V offset
			public FloatVector4	scaleAndOffset;
			// 0 = "Wrap", 1 = "Clamp", 2 = "Mirror", 3 = "Border"
			public byte			textureAddressMode;
			// 1 = "One" (default), 2 = "Two"
			public byte			channel;

			public UVStream() {
				super(6);
				scaleAndOffset = new FloatVector4(1.0f, 1.0f, 0.0f, 0.0f);
				textureAddressMode = (0); // "Wrap"
				channel = (1); // "One"

			}
		}
	  
		
		
		public static class TextureSet extends CE2MaterialObject // object type 5
		{
			static int[] defaultTextureRepl = new int[]//CE2Material.TextureSet.maxTexturePaths]
					{
						// color, normal, opacity, rough
						0xFF000000, 0xFFFF8080, 0xFFFFFFFF, 0xFF000000,
						// metal, ao, height, emissive
						0xFF000000, 0xFFFFFFFF, 0xFF000000, 0xFF000000,
						// transmissive, curvature, mask, unknown
						0xFF000000, 0xFF808080, 0xFF000000, 0xFF808080,
						// Z offset, unknown, overlay color, overlay roughness
						0xFF000000, 0xFF000000, 0xFF808080, 0xFF808080,
						// overlay metalness, unknown, unknown, unknown
						0xFF808080, 0xFF000000, 0xFF000000, 0xFFFFFFFF,
						// ID
						0xFF808080};
			private static int	
					textureNumColor		= 0,
					textureNumNormal = 1, 
					textureNumOpacity = 2,
					textureNumRough = 3, 
					textureNumMetal = 4, 
					textureNumAO = 5, 
					textureNumHeight = 6,
					textureNumEmissive = 7, 
					textureNumTransmissive = 8, 
					textureNumCurvature = 9, 
					textureNumMask = 10;

			private static int	maxTexturePaths		= 21;

			public int					texturePathMask;
			public float				floatParam;																				// normal map intensity
			// texturePaths[0] =  albedo (_color.dds)
			// texturePaths[1] =  normal map (_normal.dds)
			// texturePaths[2] =  alpha (_opacity.dds)
			// texturePaths[3] =  roughness (_rough.dds)
			// texturePaths[4] =  metalness (_metal.dds)
			// texturePaths[5] =  ambient occlusion (_ao.dds)
			// texturePaths[6] =  height map (_height.dds)
			// texturePaths[7] =  glow map (_emissive.dds)
			// texturePaths[8] =  translucency (_transmissive.dds)
			// texturePaths[9] =  _curvature.dds
			// texturePaths[10] = _mask.dds
			// texturePaths[12] = _zoffset.dds
			// texturePaths[14] = overlay color
			// texturePaths[15] = overlay roughness
			// texturePaths[16] = overlay metalness
			// texturePaths[20] = _id.dds
			// NOTE: string view pointers are always valid and the strings are
			// null-terminated
			public String[]			texturePaths		= new String[maxTexturePaths];
			// texture replacements are colors in R8G8B8A8 format
			public int					textureReplacementMask;
			public int[]				textureReplacements	= new int[maxTexturePaths];
			// 0 = "Tiling" (default), 1 = "UniqueMap", 2 = "DetailMapTiling", 3 = "HighResUniqueMap"
			public byte				resolutionHint;
			public boolean				disableMipBiasHint;

			public TextureSet() {
				super(5);
				texturePathMask = (0);
				floatParam = (1.0f); // normal map intensity
				textureReplacementMask = (0);
				resolutionHint = (0); // "Tiling"
				disableMipBiasHint = (false);

				for (int i = 0; i < maxTexturePaths; i++)
					texturePaths[i] = null;
				for (int i = 0; i < maxTexturePaths; i++)
					textureReplacements[i] = defaultTextureRepl[i];
			}
		}

		public static class Material extends CE2MaterialObject // object type 4
		{
			public FloatVector4 color;
			// bit 0 = MaterialOverrideColorTypeComponent, 0: "Multiply", 1: "Lerp"
			// bit 1 = ParamBool, 1: use vertex color as tint
			public byte		colorModeFlags;
			// bit 0 = is flipbook, bit 1 = loops
			public byte		flipbookFlags;
			public byte		flipbookColumns;
			public byte		flipbookRows;
			public float		flipbookFPS;
			public TextureSet	textureSet;

			public Material() {
				super(4);
				color = new FloatVector4(1.0f, 1.0f, 1.0f, 0.0f);
				colorModeFlags = (1); // "Lerp"
				flipbookFlags = (0);
				flipbookColumns = (1);
				flipbookRows = (1);
				flipbookFPS = (30.0f);
				textureSet = (null);

			}
		}

		public static class Layer extends CE2MaterialObject // object type 3
		{
			public Material	material;
			public UVStream	uvStream;

			public Layer() {
				super(3);
				material = (null);
				uvStream = (null);
			}
		}
		
		public static class Blender extends CE2MaterialObject // object type 2
		{
			public static int maxFloatParams	= 5;
			public static int maxBoolParams = 8;
			public UVStream			uvStream;
			public String				texturePath;
			public int					textureReplacement;
			public boolean				textureReplacementEnabled;
			// 0 = "Linear" (default), 1 = "Additive", 2 = "PositionContrast",
			// 3 = "None", 4 = "CharacterCombine", 5 = "Skin"
			public byte				blendMode;
			// 0 = "Red" (default), 1 = "Green", 2 = "Blue", 3 = "Alpha"
			public byte				colorChannel;
			// values set via BSMaterial.MaterialParamFloat and BSMaterial.ParamBool
			// 0: height blend threshold
			// 1: height blend factor
			// 2: position
			// 3: 1.0 - contrast
			// 4: mask intensity
			public float[]				floatParams		= new float[maxFloatParams];
			// 0: blend albedo texture
			// 1: blend metalness texture
			// 2: blend roughness texture
			// 3: blend normal map texture
			// 4: blend normals additively
			// 5: vertex alpha
			// 6: blend ambient occlusion texture
			// 7: use dual blend mask
			public boolean[]			boolParams		= new boolean[maxBoolParams];

			public Blender() {
				super(2);
				uvStream = (null);
				texturePath = (null);
				textureReplacement = (0xFFFFFFFF);
				textureReplacementEnabled = (false);
				blendMode = (0); // "Linear"
				colorChannel = (0); // "Red"		  
				floatParams[0] = 0.5f; // height blend threshold
				floatParams[1] = 0.5f; // height blend factor
				floatParams[2] = 0.5f; // position
				floatParams[3] = 1.0f; // 1.0 - contrast
				floatParams[4] = 1.0f; // mask intensity
				boolParams[0] = true; // blend color
				boolParams[1] = true; // blend metalness
				boolParams[2] = true; // blend roughness
				boolParams[3] = true; // blend normals
				boolParams[4] = true; // blend normals additively
				boolParams[5] = false; // use vertex color
				boolParams[6] = true; // blend ambient occlusion
				boolParams[7] = false; // use detail blend mask
			}
		}
	  
	  
	  
	  
		public static class EffectSettings {
			public int				flags;
			// 0 = "AlphaBlend", 1 = "Additive", 2 = "SourceSoftAdditive",
			// 3 = "Multiply", 4 = "DestinationSoftAdditive",
			// 5 = "DestinationInvertedSoftAdditive", 6 = "TakeSmaller", 7 = "None"
			public byte			blendMode;
			public float			falloffStartAngle;
			public float			falloffStopAngle;
			public float			falloffStartOpacity;
			public float			falloffStopOpacity;
			public float			alphaThreshold;
			public float			softFalloffDepth;
			public float			frostingBgndBlend;
			public float			frostingBlurBias;
			public float			materialAlpha;
			public float			backlightScale;
			public float			backlightSharpness;
			public float			backlightTransparency;
			public FloatVector4	backlightTintColor;
			public int				depthBias;

			public EffectSettings() {
				blendMode = (0); // "AlphaBlend"
				falloffStartAngle = (0.0f);
				falloffStopAngle = (0.0f);
				falloffStartOpacity = (0.0f);
				falloffStopOpacity = (0.0f);
				alphaThreshold = (0.5f);
				softFalloffDepth = (2.0f);
				frostingBgndBlend = (0.98f);
				frostingBlurBias = (0.0f);
				materialAlpha = (1.0f);
				backlightScale = (0.0f);
				backlightSharpness = (8.0f);
				backlightTransparency = (0.0f);
				backlightTintColor = (new FloatVector4(1.0f));
				depthBias = (0);
			}

			void setFlags(int m, boolean n) {
				flags = n ? (flags & ~m) : (flags | m);// (flags & ~m) | ((0U - std.uint32_t(n)) & m);	 
			}
		}
	  
		public static class EmissiveSettings {
			public boolean			isEnabled;
			public byte			sourceLayer;
			public byte			maskSourceBlender;		// 0: "None", 1: "Blender1"
			public boolean			adaptiveEmittance;
			public boolean			enableAdaptiveLimits;
			public float			clipThreshold;
			public float			luminousEmittance;
			public FloatVector4	emissiveTint;			// R, G, B, overall scale
			public float			exposureOffset;
			public float			maxOffset;
			public float			minOffset;

			public EmissiveSettings() {
				isEnabled = (false);
				sourceLayer = (0); // "MATERIAL_LAYER_0"
				maskSourceBlender = (0); // "None"
				adaptiveEmittance = (false);
				enableAdaptiveLimits = (false);
				clipThreshold = (0.0f);
				luminousEmittance = (100.0f);
				emissiveTint = (new FloatVector4(1.0f));
				exposureOffset = (0.0f);
				maxOffset = (9999.0f);
				minOffset = (0.0f);
			}
		}
	  
		public static class LayeredEmissiveSettings {
			public boolean	isEnabled;
			public byte	layer1Index;			// "MATERIAL_LAYER_n"
			public byte	layer1MaskIndex;		// 0: "None", 1: "Blender1"
			public boolean	layer2Active;
			public byte	layer2Index;
			public byte	layer2MaskIndex;
			public byte	blender1Index;			// "BLEND_LAYER_n"
			// 0 = "Lerp", 1 = "Additive", 2 = "Subtractive", 3 = "Multiplicative"
			public byte	blender1Mode;
			public boolean	layer3Active;
			public byte	layer3Index;
			public byte	layer3MaskIndex;
			public byte	blender2Index;
			public byte	blender2Mode;
			public boolean	adaptiveEmittance;
			public boolean	enableAdaptiveLimits;
			public boolean	ignoresFog;
			public int		layer1Tint;				// R, G, B, overall scale
			public int		layer2Tint;
			public int		layer3Tint;
			public float	clipThreshold;
			public float	luminousEmittance;
			public float	exposureOffset;
			public float	maxOffset;
			public float	minOffset;

			public LayeredEmissiveSettings() {
				isEnabled = (false);
				layer1Index = (0); // "MATERIAL_LAYER_0"
				layer1MaskIndex = (0); // "None"
				layer2Active = (false);
				layer2Index = (1); // "MATERIAL_LAYER_1"
				layer2MaskIndex = (0); // "None"
				blender1Index = (0); // "BLEND_LAYER_0"
				blender1Mode = (0); // "Lerp"
				layer3Active = (false);
				layer3Index = (2); // "MATERIAL_LAYER_2"
				layer3MaskIndex = (0); // "None"
				blender2Index = (1); // "BLEND_LAYER_1"
				blender2Mode = (0); // "Lerp"
				adaptiveEmittance = (false);
				enableAdaptiveLimits = (false);
				ignoresFog = (false);
				layer1Tint = (0xFFFFFFFF);
				layer2Tint = (0xFFFFFFFF);
				layer3Tint = (0xFFFFFFFF);
				clipThreshold = (0.0f);
				luminousEmittance = (100.0f);
				exposureOffset = (0.0f);
				maxOffset = (1.0f);
				minOffset = (0.0f);
			}
		}
	  
	  
		public static class TranslucencySettings {
			public boolean	isEnabled;
			public boolean	isThin;
			public boolean	flipBackFaceNormalsInVS;
			public boolean	useSSS;
			public float	sssWidth;
			public float	sssStrength;
			public float	transmissiveScale;
			public float	transmittanceWidth;
			public float	specLobe0RoughnessScale;
			public float	specLobe1RoughnessScale;
			public byte	sourceLayer;			// default: 0 ("MATERIAL_LAYER_0")

			public TranslucencySettings() {
				isEnabled = (false);
				isThin = (false);
				flipBackFaceNormalsInVS = (false);
				useSSS = (false);
				sssWidth = (0.025f);
				sssStrength = (0.5f);
				transmissiveScale = (0.05f);
				transmittanceWidth = (0.03f);
				specLobe0RoughnessScale = (0.55f);
				specLobe1RoughnessScale = (1.2f);
				sourceLayer = (0); // "MATERIAL_LAYER_0"		
			}
		}
	  
		public static class DecalSettings {
			public boolean	isDecal;
			public boolean	isPlanet;
			// 0 = "None" (default), 1 = "Additive"
			public byte	blendMode;
			public boolean	animatedDecalIgnoresTAA;
			public float	decalAlpha;
			// bits 0-2: output albedo R, G, B
			// bits 4-5: output normal X, Y
			// bit 8:    output ambient occlusion
			// bit 9:    output roughness
			// bit 10:   output metalness
			// bit 20:   output emissive R
			// bit 21:   output emissive G
			// bit 22:   output emissive B
			// bit 23:   output emissive A
			// defaults to 0x0737 (all channels enabled)
			public int		writeMask;
			public boolean	isProjected;
			// projected decal settings
			public boolean	useParallaxMapping;
			public boolean	parallaxOcclusionShadows;
			public byte	maxParallaxSteps;
			public String	surfaceHeightMap;
			public float	parallaxOcclusionScale;
			// 0 = "Top", 1 = "Middle", 2 = "Bottom" (default)
			public byte	renderLayer;
			public boolean	useGBufferNormals;

			public DecalSettings() {
				isDecal = (false);
				isPlanet = (false);
				blendMode = (0); // "None"
				animatedDecalIgnoresTAA = (false);
				decalAlpha = (1.0f);
				writeMask = (0x0737);
				isProjected = (false);
				useParallaxMapping = (false);
				parallaxOcclusionShadows = (false);
				maxParallaxSteps = (72);
				surfaceHeightMap = (null);
				parallaxOcclusionScale = (1.0f);
				renderLayer = (2); // "Bottom"
				useGBufferNormals = (true);
			}
		}
	  
		public static class VegetationSettings {
			public boolean	isEnabled;
			public float	leafFrequency;
			public float	leafAmplitude;
			public float	branchFlexibility;
			public float	trunkFlexibility;
			public float	terrainBlendStrength;		// the last two variables are deprecated
			public float	terrainBlendGradientFactor;

			public VegetationSettings() {
				isEnabled = (false);
				leafFrequency = (3.69f);
				leafAmplitude = (0.068f);
				branchFlexibility = (0.03f);
				trunkFlexibility = (4.0f);
				terrainBlendStrength = (0.0f);
				terrainBlendGradientFactor = (0.0f);
			}
		}
	  
		public static class DetailBlenderSettings {
			public boolean		isEnabled;
			public boolean		textureReplacementEnabled;
			public int			textureReplacement;
			public String		texturePath;
			public UVStream	uvStream;

			public DetailBlenderSettings() {
				isEnabled = (false);
				textureReplacementEnabled = (false);
				textureReplacement = (0xFFFFFFFF);
				texturePath = (null);
				uvStream = (null);
			}
		}
	  
		public static class LayeredEdgeFalloff {
			public float[]	falloffStartAngles		= new float[3];
			public float[]	falloffStopAngles		= new float[3];
			public float[]	falloffStartOpacities	= new float[3];
			public float[]	falloffStopOpacities	= new float[3];
			public byte	activeLayersMask;
			public boolean	useRGBFalloff;

			public LayeredEdgeFalloff() {
				activeLayersMask = (0);
				useRGBFalloff = (false);
				falloffStartAngles[0] = 0.0f;
				falloffStartAngles[1] = 0.0f;
				falloffStartAngles[2] = 0.0f;
				falloffStopAngles[0] = 0.0f;
				falloffStopAngles[1] = 0.0f;
				falloffStopAngles[2] = 0.0f;
				falloffStartOpacities[0] = 0.0f;
				falloffStartOpacities[1] = 0.0f;
				falloffStartOpacities[2] = 0.0f;
				falloffStopOpacities[0] = 0.0f;
				falloffStopOpacities[1] = 0.0f;
				falloffStopOpacities[2] = 0.0f;
			}
		}
	  
		public static class WaterSettings {
			public float			waterEdgeFalloff;
			public float			waterWetnessMaxDepth;
			public float			waterEdgeNormalFalloff;
			public float			waterDepthBlur;
			// color R, color G, color B, refraction magnitude
			public FloatVector4	reflectance;
			// color R, color G, color B, max. concentration
			public FloatVector4	phytoplanktonReflectance;
			public FloatVector4	sedimentReflectance;
			public FloatVector4	yellowMatterReflectance;
			public boolean			lowLOD;
			public boolean			placedWater;

			public WaterSettings() {
				waterEdgeFalloff = (0.1f);
				waterWetnessMaxDepth = (0.05f);
				waterEdgeNormalFalloff = (3.0f);
				waterDepthBlur = (0.0f);
				reflectance = (new FloatVector4(0.0f, 0.003f, 0.004f, 0.1199f));
				phytoplanktonReflectance = (new FloatVector4(-0.0001f, 0.002f, 0.0003f, 0.04f));
				sedimentReflectance = (new FloatVector4(0.0001f, 0.0001f, 0.0f, 0.14f));
				yellowMatterReflectance = (new FloatVector4(0.0007f, 0.0f, -0.0001f, 0.04f));
				lowLOD = (false);
				placedWater = (false);
			}
		}
	  
		public static class GlobalLayerData {
			public float			texcoordScaleXY;
			public float			texcoordScaleYZ;
			public float			texcoordScaleXZ;
			public boolean			usesDirectionality;
			public boolean			blendNormalsAdditively;
			public boolean			useNoiseMaskTexture;
			public boolean			noiseMaskTxtReplacementEnabled;
			public FloatVector4	albedoTintColor;
			// sourceDirection[3] = directionalityIntensity
			public FloatVector4	sourceDirection;
			public float			directionalityScale;
			public float			directionalitySaturation;
			public float			blendPosition;					// used if blendNormalsAdditively is false
			public float			blendContrast;
			// GlobalLayerNoiseData
			public float			materialMaskIntensityScale;
			public int				noiseMaskTextureReplacement;
			public String			noiseMaskTexture;
			public FloatVector4	texcoordScaleAndBias;
			public float			worldspaceScaleFactor;
			public float			hurstExponent;
			public float			baseFrequency;
			public float			frequencyMultiplier;
			public float			maskIntensityMin;
			public float			maskIntensityMax;

			public GlobalLayerData() {
				texcoordScaleXY = (0.25f);
				texcoordScaleYZ = (0.25f);
				texcoordScaleXZ = (0.25f);
				usesDirectionality = (true);
				blendNormalsAdditively = (true);
				useNoiseMaskTexture = (false);
				noiseMaskTxtReplacementEnabled = (false);
				albedoTintColor = (new FloatVector4(1.0f));
				sourceDirection = (new FloatVector4(0.0f, 0.0f, 1.0f, 0.85f));
				directionalityScale = (2.0f);
				directionalitySaturation = (1.3f);
				blendPosition = (0.5f);
				blendContrast = (0.5f);
				materialMaskIntensityScale = (1.0f);
				noiseMaskTextureReplacement = (0xFFFFFFFF);
				noiseMaskTexture = (null);
				texcoordScaleAndBias = (new FloatVector4(1.0f, 1.0f, 0.0f, 0.0f));
				worldspaceScaleFactor = (0.25f);
				hurstExponent = (1.0f);
				baseFrequency = (32.0f);
				frequencyMultiplier = (2.0f);
				maskIntensityMin = (0.0f);
				maskIntensityMax = (1.0f);
			}
		}

		public static class HairSettings {
			public boolean			isEnabled;
			public boolean			isSpikyHair;
			public byte			depthOffsetMaskVertexColorChannel;
			public byte			aoVertexColorChannel;
			public float			specScale;
			public float			specularTransmissionScale;
			public float			directTransmissionScale;
			public float			diffuseTransmissionScale;
			public float			roughness;
			public float			contactShadowSoftening;
			public float			backscatterStrength;
			public float			backscatterWrap;
			public float			variationStrength;
			public float			indirectSpecularScale;
			public float			indirectSpecularTransmissionScale;
			public float			indirectSpecRoughness;
			public float			edgeMaskContrast;
			public float			edgeMaskMin;
			public float			edgeMaskDistanceMin;
			public float			edgeMaskDistanceMax;
			public float			ditherScale;
			public float			ditherDistanceMin;
			public float			ditherDistanceMax;
			// tangent[3] = TangentBend
			public FloatVector4	tangent;
			public float			maxDepthOffset;

			public HairSettings() {
				isEnabled = (false);
				isSpikyHair = (false);
				depthOffsetMaskVertexColorChannel = (0); // "Red"
				aoVertexColorChannel = (1); // "Green"
				specScale = (1.0f);
				specularTransmissionScale = (0.5f);
				directTransmissionScale = (0.33f);
				diffuseTransmissionScale = (0.2f);
				roughness = (0.38f);
				contactShadowSoftening = (0.25f);
				backscatterStrength = (0.5f);
				backscatterWrap = (0.5f);
				variationStrength = (0.2f);
				indirectSpecularScale = (1.0f);
				indirectSpecularTransmissionScale = (0.5f);
				indirectSpecRoughness = (0.2f);
				edgeMaskContrast = (0.7f);
				edgeMaskMin = (0.75f);
				edgeMaskDistanceMin = (0.5f);
				edgeMaskDistanceMax = (5.0f);
				ditherScale = (0.5f);
				ditherDistanceMin = (0.5f);
				ditherDistanceMax = (1.5f);
				tangent = (new FloatVector4(0.0f, -1.0f, 0.0f, 0.3f));
				maxDepthOffset = (0.075f);
			}
		}

		
	}

	//no members so just a scoping class name
	public static class CE2MaterialDB  {
		/* 'Components' links material components to material objects: 
			 * BSComponentDB2.DBFileIndex.ComponentInfo { 
			 * BSComponentDB2.ID ObjectID 
			 * UInt16 Index 
			 * UInt16 Type }
			 * Looks like this is the data translated with the index embedded in whoever owns this?
			 */
		public static class ComponentInfo {
			
			CE2MaterialObject	o;
			MaterialComponent	componentData;
	     
			public ComponentInfo(CE2MaterialObject q) {
				o = (q);
			}
	    
			// this method can't send back a "not able to be loaded" signal, like other, that can use -1 or somesuch
			// so in the case of a default false we can't be sure why it's false, prolly got to
			// use an in and out an int, allowing a singal
			int readBool(CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_Bool) {
					return p.children()[fieldNum].boolValue() ? 1 : 0;
				}
				return -1;
			}
			
			boolean readBool(boolean def, CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_Bool) {
					return p.children()[fieldNum].boolValue();
				}
				return def;
			}

			byte readUInt8(byte def,CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_UInt8) {
					return p.children()[fieldNum].byteValue();
				}
				return def;
			}

			int readUInt16(int def,CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_UInt16) {
					return p.children()[fieldNum].shortValue();
				}
				return def;
			}

			int readUInt32(int def,CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_UInt32) {
					return (p.children()[fieldNum].uintValue());
				}
				return def;
			}

			float readFloat(float def, CDBObject p, int fieldNum) {
				return readFloat(def, p, fieldNum, false);
			}

			float readFloat(float def, CDBObject p, int fieldNum, boolean clampTo0To1) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_Float) {
					float n = p.children()[fieldNum].floatValue();
					if (clampTo0To1)
						n = Math.min((n > 0.0f ? n : 0.0f), 1.0f);
					return n;
				}
				return def;
			}

			String readString(String def,CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					return p.children()[fieldNum].stringValue();
				}
				return def;
			}

			
			String readPath(String def,CDBObject p, int fieldNum, String prefix, String suffix) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					String t = p.children()[fieldNum].stringValue();
					t = readPath(t, 0, prefix, suffix);
					return t;
				}
				return def;
			}
			
			// 1 prefix trims to start with that, or added if not started with that
			// 2 all lowered
			// 3 all \\ to /
			// 4 suffix added if not there
			String readPath(String s, int n, String prefix, String suffix) {					
				if (s == null || s.length() == 0)
					return null;

				if (n > 0)
					s = s.substring(n);

				s = s.toLowerCase();
				s = s.replace('\\', '/');
				if (prefix != null && prefix.length() > 0) {
					int p = s.indexOf(prefix);
					if (p != -1)
						s = s.substring(p);
					else
						s = prefix + s;
				}
				if (suffix != null && suffix.length() > 0 && !s.endsWith(suffix))
					s += suffix;

				return s;
			}
	    	    
			
			// Not this crazy idea: t = sequence of strings with length prefix (e.g. "\005False\004True")
			byte readEnum(byte def,CDBObject p, int fieldNum, String[] enumStrings) {
				
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					String s = p.children()[fieldNum].stringValue();
					for (int i = 0; i < enumStrings.length; i++) {
						if (enumStrings[i].equals(s))
							return (byte)i;
					}
					System.out.println("Enum not found in strings!!! " + s);
					for (int i = 0; i < enumStrings.length; i++) 
						System.out.println(enumStrings[i]);
				}
				//c++ code very poor design
			/*	byte[] esb = enumsString.getBytes(StandardCharsets.UTF_8); 
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					String s = p.children()[fieldNum].stringValue();
					byte[] sbytes = s.getBytes(StandardCharsets.UTF_8); 
					int len = s.length();
					int t = 0;
					for (byte i = 0; t < esb.length; i++) {
						int len2 = esb[t];
						if (len2 != len)// can't match
							continue;
						byte[] s2 = new byte[len2];
						System.arraycopy(esb, t + 1, s2, 0, len2);
						t = (t + 1) + len2;
						for (int j = 0; len2 != 0 && s2[j] == sbytes[j]; j++, len2--)
							;
						if (len2 == 0) {
							return i;
						}
					}
				}*/

				return def;
			}

			byte readLayerNumber(byte def, CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					String s = p.children()[fieldNum].stringValue();
					byte[] sbytes = getBytesFast(s);
					if (s.startsWith("MATERIAL_LAYER_") && sbytes[15] >= '0'
						&& sbytes[15] < (CE2Material.maxLayers + '0')) {
						return (byte)(sbytes[15] - '0');// characters 0-10 converted to int 0-10

					}
				}
				return def;
			}

			byte readBlenderNumber(byte def, CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null && p.children()[fieldNum].type == REFLArchive.String_String) {
					String s = p.children()[fieldNum].stringValue();
					byte[] sbytes = getBytesFast(s);
					if (s.startsWith("BLEND_LAYER_")	&& sbytes[12] >= '0'
						&& sbytes[12] < (CE2Material.maxBlenders + '0')) {
						return (byte)(sbytes[12] - '0');// characters 0-10 converted to int 0-10
					}
				}
				return def;
			}


			// BSMaterial_LayeredEmissivityComponent
			//   boolean  Enabled
			//   String  FirstLayerIndex
			//   BSMaterial_Color  FirstLayerTint
			//   String  FirstLayerMaskIndex
			//   boolean  SecondLayerActive
			//   String  SecondLayerIndex
			//   BSMaterial_Color  SecondLayerTint
			//   String  SecondLayerMaskIndex
			//   String  FirstBlenderIndex
			//   String  FirstBlenderMode
			//   boolean  ThirdLayerActive
			//   String  ThirdLayerIndex
			//   BSMaterial_Color  ThirdLayerTint
			//   String  ThirdLayerMaskIndex
			//   String  SecondBlenderIndex
			//   String  SecondBlenderMode
			//   Float  EmissiveClipThreshold
			//   boolean  AdaptiveEmittance
			//   Float  LuminousEmittance
			//   Float  ExposureOffset
			//   boolean  EnableAdaptiveLimits
			//   Float  MaxOffsetEmittance
			//   Float  MinOffsetEmittance
			//   boolean  IgnoresFog
			void readLayeredEmissivityComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.LayeredEmissiveSettings sp = new CE2Material.LayeredEmissiveSettings();
				m.layeredEmissiveSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_LayeredEmissivity, tmp==1);
					sp.isEnabled = tmp==1;
				}
				sp.layer1Index = readLayerNumber(sp.layer1Index, p, 1);
				sp.layer1Tint = readColorValue(sp.layer1Tint, p, 2);
				sp.layer1MaskIndex = readEnum(sp.layer1MaskIndex, p, 3, maskSourceBlenderNames);
				sp.layer2Active = readBool(sp.layer2Active, p, 4);
				sp.layer2Index = readLayerNumber(sp.layer2Index, p, 5);
				sp.layer2Tint = readColorValue(sp.layer2Tint, p, 6);
				sp.layer2MaskIndex = readEnum(sp.layer2MaskIndex, p, 7, maskSourceBlenderNames);
				sp.blender1Index = readBlenderNumber(sp.blender1Index, p, 8);
				sp.blender1Mode = readEnum(sp.blender1Mode, p, 9, blenderModeNames);
				sp.layer3Active = readBool(sp.layer3Active, p, 10);
				sp.layer3Index = readLayerNumber(sp.layer3Index, p, 11);
				sp.layer3Tint = readColorValue(sp.layer3Tint, p, 12);
				sp.layer3MaskIndex = readEnum(sp.layer3MaskIndex, p, 13, maskSourceBlenderNames);
				sp.blender2Index = readBlenderNumber(sp.blender2Index, p, 14);
				sp.blender2Mode = readEnum(sp.blender2Mode, p, 15, blenderModeNames);
				sp.clipThreshold = readFloat(sp.clipThreshold, p, 16);
				sp.adaptiveEmittance = readBool(sp.adaptiveEmittance, p, 17);
				sp.luminousEmittance = readFloat(sp.luminousEmittance, p, 18);
				sp.exposureOffset = readFloat(sp.exposureOffset, p, 19);
				sp.enableAdaptiveLimits = readBool(sp.enableAdaptiveLimits, p, 20);
				sp.maxOffset = readFloat(sp.maxOffset, p, 21);
				sp.minOffset = readFloat(sp.minOffset, p, 22);
				sp.ignoresFog = readBool(sp.ignoresFog, p, 23);
			}
		     
			 
			// BSMaterial_AlphaBlenderSettings
			//   String  Mode
			//   boolean  UseDetailBlendMask
			//   boolean  UseVertexColor
			//   String  VertexColorChannel
			//   BSMaterial_UVStreamID  OpacityUVStream
			//   Float  HeightBlendThreshold
			//   Float  HeightBlendFactor
			//   Float  Position
			//   Float  Contrast
			void readAlphaBlenderSettings(CDBObject p) {
				if (o.type != 1)
					return;
				if (!(p != null && p.type == String_BSMaterial_AlphaBlenderSettings))
					return;
				CE2Material m = (CE2Material)(o);
				m.alphaBlendMode = readEnum(m.alphaBlendMode, p, 0, alphaBlendModeNames);
				int tmp = readBool(p, 1);
				if (tmp != -1)
					m.setFlags(CE2Material.Flag_AlphaDetailBlendMask, tmp==1);
				tmp = readBool(p, 2);
				if (tmp!=-1)
					m.setFlags(CE2Material.Flag_AlphaVertexColor, tmp==1);
				m.alphaVertexColorChannel = readEnum(m.alphaVertexColorChannel, p, 3, colorChannelNames);
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 5)
					readUVStreamID(p.children()[4]);
				m.alphaHeightBlendThreshold = readFloat(m.alphaHeightBlendThreshold, p, 5, true);
				m.alphaHeightBlendFactor = readFloat(m.alphaHeightBlendFactor, p, 6, true);
				m.alphaPosition = readFloat(m.alphaPosition, p, 7, true);
				m.alphaContrast = readFloat(m.alphaContrast, p, 8, true);
			}
	
			// BSFloatCurve
			//   List  Controls
			//   Float  MaxInput
			//   Float  MinInput
			//   Float  InputDistance
			//   Float  MaxValue
			//   Float  MinValue
			//   Float  DefaultValue
			//   String  Type
			//   String  Edge
			//   boolean  IsSampleInterpolating
			void readBSFloatCurve(CDBObject p) {
				// not implemented
			}
	    

			// BSMaterial_EmissiveSettingsComponent
			//   boolean  Enabled
			//   BSMaterial_EmittanceSettings  Settings
			void readEmissiveSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.EmissiveSettings sp = new CE2Material.EmissiveSettings();
				m.emissiveSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_Emissive, tmp==1);
					sp.isEnabled = tmp==1;
				}
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 2)
					readEmittanceSettings(p.children()[1]);
			}
	
			// BSMaterial_WaterFoamSettingsComponent
			//   String  Mode
			//   Float  MaskDistanceFromShoreStart
			//   Float  MaskDistanceFromShoreEnd
			//   Float  MaskDistanceRampWidth
			//   Float  WaveShoreFadeInnerDistance
			//   Float  WaveShoreFadeOuterDistance
			//   Float  WaveSpawnFadeInDistance
			//   XMFLOAT4  MaskNoiseAmp
			//   XMFLOAT4  MaskNoiseFreq
			//   XMFLOAT4  MaskNoiseBias
			//   XMFLOAT4  MaskNoiseAnimSpeed
			//   Float  MaskNoiseGlobalScale
			//   Float  MaskWaveParallax
			//   Float  BlendMaskPosition
			//   Float  BlendMaskContrast
			//   Float  FoamTextureScrollSpeed
			//   Float  FoamTextureDistortion
			//   Float  WaveSpeed
			//   Float  WaveAmplitude
			//   Float  WaveScale
			//   Float  WaveDistortionAmount
			//   Float  WaveParallaxFalloffBias
			//   Float  WaveParallaxFalloffScale
			//   Float  WaveParallaxInnerStrength
			//   Float  WaveParallaxOuterStrength
			//    boolean  WaveFlipWaveDirection
			void readWaterFoamSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_FlipbookComponent
			//   boolean  IsAFlipbook
			//   UInt32  Columns
			//   UInt32  Rows
			//   Float  FPS
			//   boolean  Loops
			void readFlipbookComponent(CDBObject p) {
				if (o.type != 4)
					return;
				CE2Material.Material m = (CE2Material.Material)(o);
				int tmp = readBool(p, 0);
				if (tmp!=-1)
					m.flipbookFlags = (byte)((m.flipbookFlags & 2) |  tmp );
				int tmp2 = readUInt32(0, p, 1);
				if (tmp2 != 0)
					m.flipbookColumns = (byte)Math.min(tmp2, 255);
				tmp2 = readUInt32(0, p, 2);
				if (tmp2 != 0)
					m.flipbookRows = (byte)Math.min(tmp2, 255);
				m.flipbookFPS = readFloat(m.flipbookFPS, p, 3);
				tmp = readBool(p, 4);
				if (tmp!=-1)
					m.flipbookFlags = (byte)((m.flipbookFlags & 1) | ( tmp  << 1));
			}
	
			// BSMaterial_PhysicsMaterialType
			//   UInt32  Value
			void readPhysicsMaterialType(CDBObject p) {
				if (!(p != null && p.type == String_BSMaterial_PhysicsMaterialType))
					return;
				if (o.type != 1)
					return;
				int tmp = readUInt32(0, p, 0);
				//if (!readUInt32(tmp, p, 0))
				//  return;
				CE2Material m = (CE2Material)(o);
				switch (tmp) {
					case 0x064003D4: // "metal"
						m.physicsMaterialType = 6;
						break;
					case 0x1DD9C611: // "wood"
						m.physicsMaterialType = 7;
						break;
					case 0x4BDC3571: // "materialphyicedebrislarge"
						m.physicsMaterialType = 5;
						break;
					case 0x6B81B7B0: // "mat"
						m.physicsMaterialType = 2;
						break;
					case 0x7A0EB611: // "materialmat"
						m.physicsMaterialType = 4;
						break;
					case 0xAD5ACB92: // "materialgroundtilevinyl"
						m.physicsMaterialType = 3;
						break;
					case 0xF0170989: // "carpet"
						m.physicsMaterialType = 1;
						break;
					default:
						m.physicsMaterialType = 0;
						break;
				}
			}
	
			// BSMaterial_TerrainTintSettingsComponent
			//   boolean  Enabled
			//   Float  TerrainBlendStrength
			//   Float  TerrainBlendGradientFactor
			void readTerrainTintSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_UVStreamID
			//   BSComponentDB2_ID  ID
			void readUVStreamID(CDBObject p) {
				CE2MaterialObject tmp = readBSComponentDB2ID(p, (byte)6);
				if (tmp == null)
					return;
				CE2Material.UVStream uvStream = (CE2Material.UVStream)(tmp);
				switch (componentData.className) {
					case String_BSMaterial_UVStreamID:
						if (o.type == 2)
							((CE2Material.Blender)o).uvStream = uvStream;
						else if (o.type == 3)
							((CE2Material.Layer)o).uvStream = uvStream;
						break;
					case String_BSMaterial_AlphaSettingsComponent:
						if (o.type == 1)
							((CE2Material)o).alphaUVStream = uvStream;
						break;
					case String_BSMaterial_DetailBlenderSettingsComponent:
						if (o.type == 1) {
							((CE2Material.DetailBlenderSettings)((CE2Material)o).detailBlenderSettings).uvStream = uvStream;
						}
						break;
				}
			}
	
			// BSMaterial_DecalSettingsComponent
			//   boolean  IsDecal
			//   Float  MaterialOverallAlpha
			//   UInt32  WriteMask
			//   boolean  IsPlanet
			//   boolean  IsProjected
			//   BSMaterial_ProjectedDecalSettings  ProjectedDecalSetting
			//   String  BlendMode
			//   boolean  AnimatedDecalIgnoresTAA
			void readDecalSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.DecalSettings sp = new CE2Material.DecalSettings();
				m.decalSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_IsDecal, tmp==1);
					if (tmp!=-1)
						m.setFlags(CE2Material.Flag_AlphaBlending, true);
					sp.isDecal = tmp==1;
				}
				sp.decalAlpha = readFloat(sp.decalAlpha, p, 1);
				sp.writeMask = readUInt32(sp.writeMask, p, 2);
				sp.isPlanet = readBool(sp.isPlanet, p, 3);
				sp.isProjected = readBool(sp.isProjected, p, 4);
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 6)
					readProjectedDecalSettings(p.children()[5]);
				sp.blendMode = readEnum(sp.blendMode, p, 6, decalBlendModeNames);
				sp.animatedDecalIgnoresTAA = readBool(sp.animatedDecalIgnoresTAA, p, 7);
			}
	
			// BSBind_Directory
			//   String  Name
			//   Map  Children
			//   UInt64  SourceDirectoryHash
			void readDirectory(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_WaterSettingsComponent
			//   Float  WaterEdgeFalloff
			//   Float  WaterWetnessMaxDepth
			//   Float  WaterEdgeNormalFalloff
			//   Float  WaterDepthBlur
			//   Float  WaterRefractionMagnitude
			//   Float  PhytoplanktonReflectanceColorR
			//   Float  PhytoplanktonReflectanceColorG
			//   Float  PhytoplanktonReflectanceColorB
			//   Float  SedimentReflectanceColorR
			//   Float  SedimentReflectanceColorG
			//   Float  SedimentReflectanceColorB
			//   Float  YellowMatterReflectanceColorR
			//   Float  YellowMatterReflectanceColorG
			//   Float  YellowMatterReflectanceColorB
			//   Float  MaxConcentrationPlankton
			//   Float  MaxConcentrationSediment
			//   Float  MaxConcentrationYellowMatter
			//   Float  ReflectanceR
			//   Float  ReflectanceG
			//   Float  ReflectanceB
			//   boolean  LowLOD
			//   boolean  PlacedWater
			void readWaterSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.WaterSettings sp = new CE2Material.WaterSettings();
				m.setFlags(CE2Material.Flag_IsWater | CE2Material.Flag_AlphaBlending, true);
				m.waterSettings = sp;
				sp.waterEdgeFalloff = readFloat(sp.waterEdgeFalloff, p, 0);
				sp.waterWetnessMaxDepth = readFloat(sp.waterWetnessMaxDepth, p, 1);
				sp.waterEdgeNormalFalloff = readFloat(sp.waterEdgeNormalFalloff, p, 2);
				sp.waterDepthBlur = readFloat(sp.waterDepthBlur, p, 3);
				sp.reflectance.w = readFloat(sp.reflectance.w, p, 4);
				sp.phytoplanktonReflectance.x = readFloat(sp.phytoplanktonReflectance.x, p, 5);
				sp.phytoplanktonReflectance.y = readFloat(sp.phytoplanktonReflectance.y, p, 6);
				sp.phytoplanktonReflectance.z = readFloat(sp.phytoplanktonReflectance.z, p, 7);
				sp.sedimentReflectance.x = readFloat(sp.sedimentReflectance.x, p, 8);
				sp.sedimentReflectance.y = readFloat(sp.sedimentReflectance.y, p, 9);
				sp.sedimentReflectance.z = readFloat(sp.sedimentReflectance.z, p, 10);
				sp.yellowMatterReflectance.x = readFloat(sp.yellowMatterReflectance.x, p, 11);
				sp.yellowMatterReflectance.y = readFloat(sp.yellowMatterReflectance.y, p, 12);
				sp.yellowMatterReflectance.z = readFloat(sp.yellowMatterReflectance.z, p, 13);
				sp.phytoplanktonReflectance.w = readFloat(sp.phytoplanktonReflectance.w, p, 14);
				sp.sedimentReflectance.w = readFloat(sp.sedimentReflectance.w, p, 15);
				sp.yellowMatterReflectance.w = readFloat(sp.yellowMatterReflectance.w, p, 16);
				sp.reflectance.x = readFloat(sp.reflectance.x, p, 17);
				sp.reflectance.y = readFloat(sp.reflectance.y, p, 18);
				sp.reflectance.z = readFloat(sp.reflectance.z, p, 19);
				sp.lowLOD = readBool(sp.lowLOD, p, 20);
				sp.placedWater = readBool(sp.placedWater, p, 21);
			}
	
			// BSFloatCurve_Control
			//   Float  Input
			//   Float  Value
			void readControl(CDBObject p) {
				// not implemented
			}
	
			// BSBind_ComponentProperty
			//   String  Name
			//   BSComponentDB2_ID  Object
			//   ClassReference  ComponentType
			//   UInt16  ComponentIndex
			//   String  PathStr
			void readComponentProperty(CDBObject p) {
				// not implemented
			}
	
			// XMFLOAT4
			//   Float  x
			//   Float  y
			//   Float  z
			//   Float  w
			boolean readXMFLOAT4(FloatVector4 v, CDBObject p, int fieldNum) {
				if (!(p != null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()))
					return false;
				CDBObject q = p.children()[fieldNum];
				if (!(q != null && q.type == REFLArchive.String_XMFLOAT4 && q.childCnt() == 4))
					return false;
				v.x = readFloat(v.x, q, 0);
				v.y = readFloat(v.y, q, 1);
				v.z = readFloat(v.z, q, 2);
				v.w = readFloat(v.w, q, 3);
				return true;
			}
	
			// BSMaterial_EffectSettingsComponent
			//   boolean  UseFallOff
			//   boolean  UseRGBFallOff
			//   Float  FalloffStartAngle
			//   Float  FalloffStopAngle
			//   Float  FalloffStartOpacity
			//   Float  FalloffStopOpacity
			//   boolean  VertexColorBlend
			//   boolean  IsAlphaTested
			//   Float  AlphaTestThreshold
			//   boolean  NoHalfResOptimization
			//   boolean  SoftEffect
			//   Float  SoftFalloffDepth
			//   boolean  EmissiveOnlyEffect
			//   boolean  EmissiveOnlyAutomaticallyApplied
			//   boolean  ReceiveDirectionalShadows
			//   boolean  ReceiveNonDirectionalShadows
			//   boolean  IsGlass
			//   boolean  Frosting
			//   Float  FrostingUnblurredBackgroundAlphaBlend
			//   Float  FrostingBlurBias
			//   Float  MaterialOverallAlpha
			//   boolean  ZTest
			//   boolean  ZWrite
			//   String  BlendingMode
			//   boolean  BackLightingEnable
			//   Float  BacklightingScale
			//   Float  BacklightingSharpness
			//   Float  BacklightingTransparencyFactor
			//   BSMaterial_Color  BackLightingTintColor
			//   boolean  DepthMVFixup
			//   boolean  DepthMVFixupEdgesOnly
			//   boolean  ForceRenderBeforeOIT
			//   boolean  ForceRenderBeforeClouds (new in version 1.14.68.0)
			//   UInt16  DepthBiasInUlp
			void readEffectSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.EffectSettings sp = new CE2Material.EffectSettings();
				m.setFlags(CE2Material.Flag_IsEffect | CE2Material.Flag_AlphaBlending, true);
				m.effectSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_UseFalloff, tmp==1);
				tmp = readBool(p, 1);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_UseRGBFalloff, tmp==1);
				sp.falloffStartAngle = readFloat(sp.falloffStartAngle ,p, 2);
				sp.falloffStopAngle = readFloat(sp.falloffStopAngle ,p, 3);
				sp.falloffStartOpacity = readFloat(sp.falloffStartOpacity ,p, 4);
				sp.falloffStopOpacity = readFloat(sp.falloffStopOpacity ,p, 5);
				tmp = readBool(p, 6);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_VertexColorBlend, tmp==1);
				tmp = readBool(p, 7);
				//if (readBool(tmp, p, 7))
				sp.setFlags(CE2Material.EffectFlag_IsAlphaTested, tmp==1);
				sp.alphaThreshold = readFloat(sp.alphaThreshold ,p, 8);
				tmp = readBool(p, 9);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_NoHalfResOpt, tmp==1);
				tmp = readBool(p, 10);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_SoftEffect, tmp==1);
				sp.softFalloffDepth = readFloat(sp.softFalloffDepth  ,p, 11);
				tmp = readBool(p, 12);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_EmissiveOnly, tmp==1);
				tmp = readBool(p, 13);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_EmissiveOnlyAuto, tmp==1);
				tmp = readBool(p, 14);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_DirShadows, tmp==1);
				tmp = readBool(p, 15);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_NonDirShadows, tmp==1);
				tmp = readBool(p, 16);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_IsGlass, tmp==1);
				tmp = readBool(p, 17);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_Frosting, tmp==1);
				sp.frostingBgndBlend = readFloat(sp.frostingBgndBlend  ,p, 18);
				sp.frostingBlurBias = readFloat(sp.frostingBlurBias  ,p, 19);
				sp.materialAlpha = readFloat(sp.materialAlpha  ,p, 20);
				tmp = readBool(p, 21);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_ZTest, tmp==1);
				tmp = readBool(p, 22);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_ZWrite, tmp==1);
				sp.blendMode = readEnum(sp.blendMode  ,p, 23, effectBlendModeNames);
				tmp = readBool(p, 24);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_BacklightEnable, tmp==1);
				sp.backlightScale = readFloat(sp.backlightScale   ,p, 25);
				sp.backlightSharpness = readFloat( sp.backlightSharpness  ,p, 26);
				sp.backlightTransparency = readFloat( sp.backlightTransparency ,p, 27);
				 readColorValues( sp.backlightTintColor , p, 28);
				tmp = readBool(p, 29);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_MVFixup, tmp==1);
				tmp = readBool(p, 30);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_MVFixupEdgesOnly, tmp==1);
				tmp = readBool(p, 31);
				if (tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_RenderBeforeOIT, tmp==1);
	
				 
				boolean isNewVersion = (p.childCnt() >= 34);
				tmp = readBool(p, 32);
				if (isNewVersion && tmp!=-1)
					sp.setFlags(CE2Material.EffectFlag_RenderBeforeClouds, tmp==1);
				int tmp2 = readUInt16( -1  ,p, (isNewVersion ? 1 : 0) + 32);
				if (tmp2 != -1)
					sp.depthBias = tmp2;
			}
	
			// BSComponentDB_CTName
			//   String  Name
			void readCTName(CDBObject p) {
				o.name = readString(o.name,p, 0);
			}
	
			// BSMaterial_GlobalLayerDataComponent
			//   Float  TexcoordScale_XY
			//   Float  TexcoordScale_YZ
			//   Float  TexcoordScale_XZ
			//   BSMaterial_Color  AlbedoTintColor
			//    boolean  UsesDirectionality
			//   XMFLOAT3  SourceDirection
			//   Float  DirectionalityIntensity
			//   Float  DirectionalityScale
			//   Float  DirectionalitySaturation
			//    boolean  BlendNormalsAdditively
			//   Float  BlendPosition
			//   Float  BlendContrast
			//   BSMaterial_GlobalLayerNoiseSettings  GlobalLayerNoiseData
			void readGlobalLayerDataComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.GlobalLayerData sp = new CE2Material.GlobalLayerData();
				m.globalLayerData = sp;
				m.setFlags(CE2Material.Flag_GlobalLayerData, true);
				sp.texcoordScaleXY = readFloat(sp.texcoordScaleXY, p, 0);
				sp.texcoordScaleYZ = readFloat(sp.texcoordScaleYZ, p, 1);
				sp.texcoordScaleXZ = readFloat(sp.texcoordScaleXZ, p, 2);
				readColorValues(sp.albedoTintColor, p, 3);
				sp.usesDirectionality = readBool(sp.usesDirectionality, p, 4);
				readXMFLOAT3(sp.sourceDirection, p, 5);
				sp.sourceDirection.w = readFloat(sp.sourceDirection.w, p, 6);
				sp.directionalityScale = readFloat(sp.directionalityScale, p, 7);
				sp.directionalitySaturation = readFloat(sp.directionalitySaturation, p, 8);
				sp.blendNormalsAdditively = readBool(sp.blendNormalsAdditively, p, 9);
				sp.blendPosition = readFloat(sp.blendPosition, p, 10);
				sp.blendContrast = readFloat(sp.blendContrast, p, 11);
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 13)
					readGlobalLayerNoiseSettings(p.children()[12]);
			}
	
			// BSMaterial_Offset
			//   XMFLOAT2  Value
			void readOffset(CDBObject p) {
				if (o.type == 6 && componentData.className == String_BSMaterial_Offset) {
					readXMFLOAT2H(((CE2Material.UVStream)o).scaleAndOffset, p, 0);
				}
			}
	
			// BSMaterial_TextureAddressModeComponent
			//   String  Value
			void readTextureAddressModeComponent(CDBObject p) {
				if (o.type == 6) {
					((CE2Material.UVStream)o).textureAddressMode = readEnum(((CE2Material.UVStream)o).textureAddressMode, p,
							0, textureAddressModeNames);
				}
			}
	
			// BSBind_FloatCurveController
			//   BSFloatCurve  Curve
			//    boolean  Loop
			void readFloatCurveController(CDBObject p) {
				// not implemented
			}
	
			// BSMaterialBinding_MaterialPropertyNode
			//   String  Name
			//   String  Binding
			//   UInt16  LayerIndex
			void readMaterialPropertyNode(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_ProjectedDecalSettings
			//   boolean  UseParallaxOcclusionMapping
			//   BSMaterial_TextureFile  SurfaceHeightMap
			//   Float  ParallaxOcclusionScale
			//   boolean  ParallaxOcclusionShadows
			//   UInt8  MaxParralaxOcclusionSteps
			//   String  RenderLayer
			//   boolean  UseGBufferNormals
			void readProjectedDecalSettings(CDBObject p) {
				if (!(p != null && p.type == String_BSMaterial_ProjectedDecalSettings))
					return;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.DecalSettings sp = (CE2Material.DecalSettings)(m.decalSettings);
				sp.useParallaxMapping = readBool(sp.useParallaxMapping, p, 0);
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 2)
					readTextureFile(p.children()[1]);
				sp.parallaxOcclusionScale = readFloat(sp.parallaxOcclusionScale, p, 2);
				sp.parallaxOcclusionShadows = readBool(sp.parallaxOcclusionShadows, p, 3);
				sp.maxParallaxSteps = readUInt8(sp.maxParallaxSteps, p, 4);
				sp.renderLayer = readEnum(sp.renderLayer, p, 5, decalRenderLayerNames);
				sp.useGBufferNormals = readBool(sp.useGBufferNormals, p, 6);
			}
	
			// BSMaterial_ParamBool
			//    boolean  Value
			void readParamBool(CDBObject p) {
				int tmp = readBool(p, 0);
				if (tmp==-1)
					return;
				int i = componentData.key & 0xFFFF;
				if (o.type == 2) {
					if (i < CE2Material.Blender.maxBoolParams)
						((CE2Material.Blender)o).boolParams[i] = tmp==1;
					return;
				}
				if (i != 0)
					return;
				if (o.type == 1) {
					((CE2Material)o).setFlags(CE2Material.Flag_TwoSided, tmp==1);
				} else if (o.type == 4) {
					((CE2Material.Material)o).colorModeFlags = (byte)((((CE2Material.Material)o).colorModeFlags & 1)
																		| ( tmp   << 1));
				}
			}
	
			// BSBind_Float3DCurveController
			//   BSFloat3DCurve  Curve
			//   boolean  Loop
			//   String  Mask
			void readFloat3DCurveController(CDBObject p) {
				// not implemented
			}
	
			void readColorValues(FloatVector4 ret, CDBObject p, int fieldNum) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
					&& p.children()[fieldNum] != null
					&& p.children()[fieldNum].type == String_BSMaterial_Color) {
					 
					readXMFLOAT4(ret, p.children()[fieldNum], 0);
					// if (readXMFLOAT4( p.children()[fieldNum], 0))
					{
						ret.maxValues(new FloatVector4(0.0f)).minValues(new FloatVector4(1.0f));
					}
				}
				 
			}
	
			int readColorValue(int def, CDBObject p, int fieldNum) {
			  if (p!=null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt() &&
			      p.children()[fieldNum] != null &&
			      p.children()[fieldNum].type == String_BSMaterial_Color)
			  {
			    FloatVector4  tmp = new FloatVector4((1.0f / 255.0f));
			    //tmp *= (1.0f / 255.0f);
			    readXMFLOAT4(tmp, p.children()[fieldNum], 0);
			    //if (readXMFLOAT4(tmp, p.children()[fieldNum], 0)) {
			      return (int)(tmp.x * 255.0f);
			    //}
			  }
			  return def;
			}
	
			// BSMaterial_Color
			//   XMFLOAT4  Value
			void readColor(CDBObject p) {
				if (o.type != 4)
					return;
				CE2Material.Material m = (CE2Material.Material)(o);
				readXMFLOAT4(m.color, p, 0);
				//if (readXMFLOAT4(m.color, p, 0))
				m.color.maxValues(new FloatVector4(0.0f)).minValues(new FloatVector4(1.0f));
			}
	
			// BSMaterial_SourceTextureWithReplacement
			//   BSMaterial_MRTextureFile  Texture
			//   BSMaterial_TextureReplacement  Replacement				
			public static class TexWithRep {
				public String texturePath;
				public int textureReplacement;
				public boolean textureReplacementEnabled;
			}
	
			TexWithRep readSourceTextureWithReplacement(
														// String texturePath, 
														// std_uint32_t& textureReplacement,   
														// bool& textureReplacementEnabled,
	
														CDBObject p, int fieldNum) {
				if (!(p != null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()
						&& p.children()[fieldNum] != null
						&& p.children()[fieldNum].type == String_BSMaterial_SourceTextureWithReplacement)) {
					return null;
				}
				CDBObject q = p.children()[fieldNum];
				TexWithRep twr = new TexWithRep();
				if (q.childCnt() >= 1 && q.children()[0] != null
					&& q.children()[0].type == String_BSMaterial_MRTextureFile) {
					twr.texturePath = readPath(twr.texturePath, q.children()[0], 0, "textures/", ".dds");
				}
				if (q.childCnt() >= 2 && q.children()[1] != null
					&& q.children()[1].type == String_BSMaterial_TextureReplacement) {
					q = q.children()[1];
					twr.textureReplacementEnabled = readBool(twr.textureReplacementEnabled,q, 0);
					twr.textureReplacement = readColorValue(twr.textureReplacement,q, 1);
				}
	
				return twr;
			}
			 
			 
	
			// BSMaterial_FlowSettingsComponent
			//   BSMaterial_Offset  FlowUVOffset
			//   BSMaterial_Scale  FlowUVScale
			//   Float  FlowExtent
			//   BSMaterial_Channel  FlowSourceUVChannel
			//   boolean  FlowIsAnimated
			//   Float  FlowSpeed
			//   boolean  FlowMapAndTexturesAreFlipbooks
			//   BSMaterial_UVStreamID  TargetUVStream
			//   String  UVStreamTargetLayer
			//   String  UVStreamTargetBlender
			//   BSMaterial_TextureFile  FlowMap
			//   boolean  ApplyFlowOnANMR
			//   boolean  ApplyFlowOnOpacity
			//   boolean  ApplyFlowOnEmissivity
			void readFlowSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_DetailBlenderSettings
			//   boolean  IsDetailBlendMaskSupported
			//   BSMaterial_SourceTextureWithReplacement  DetailBlendMask
			//   BSMaterial_UVStreamID  DetailBlendMaskUVStream
			void readDetailBlenderSettings(CDBObject p) {
				if (!(p != null && p.type == String_BSMaterial_DetailBlenderSettings))
					return;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.DetailBlenderSettings sp = (CE2Material.DetailBlenderSettings)(m.detailBlenderSettings);
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_UseDetailBlender, tmp==1);
					sp.isEnabled = tmp==1;
				}
				//readSourceTextureWithReplacement(sp.texturePath, sp.textureReplacement, sp.textureReplacementEnabled, p, 1);
				TexWithRep twr = readSourceTextureWithReplacement(p, 1);
				if (twr != null) {
					sp.texturePath = twr.texturePath;
					sp.textureReplacement = twr.textureReplacement;
					sp.textureReplacementEnabled = twr.textureReplacementEnabled;
				}
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 3)
					readUVStreamID(p.children()[2]);
			}
	
			// BSMaterial_LayerID
			//   BSComponentDB2_ID  ID
			void readLayerID(CDBObject p) {
				int i = componentData.key & 0xFFFF;
				if (!(o.type == 1 && i < CE2Material.maxLayers))
					return;
				CE2MaterialObject tmp = readBSComponentDB2ID(p, 3);
				if (tmp != null) {
					CE2Material m = (CE2Material)(o);
					m.layers[i] = (CE2Material.Layer)(tmp);
					if (!(tmp != null))
						m.layerMask &= ~(1 << i);
					else
						m.layerMask |= (1 << i);
				}
			}
	
			// BSBind_Controllers_Mapping
			//   BSBind_Address  Address
			//   Ref  Controller
			void readMapping(CDBObject p) {
				// not implemented
			}
	
			// ClassReference
			void readClassReference(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_Scale
			//   XMFLOAT2  Value
			void readScale(CDBObject p) {
				if (o.type == 6 && componentData.className == String_BSMaterial_Scale) {
					readXMFLOAT2L(((CE2Material.UVStream)(o)).scaleAndOffset, p, 0);
				}
			}
	
			// BSMaterial_WaterGrimeSettingsComponent
			//   String  Mode
			//   Float  MaskDistanceFromShoreStart
			//   Float  MaskDistanceFromShoreEnd
			//   Float  MaskDistanceRampWidth
			//   XMFLOAT4  MaskNoiseAmp
			//   XMFLOAT4  MaskNoiseFreq
			//   XMFLOAT4  MaskNoiseBias
			//   XMFLOAT4  MaskNoiseAnimSpeed
			//   Float  MaskNoiseGlobalScale
			//   Float  MaskWaveParallax
			//   Float  BlendMaskPosition
			//   Float  BlendMaskContrast
			//   Float  NormalOverride
			void readWaterGrimeSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_UVStreamParamBool
			//    boolean  Value
			void readUVStreamParamBool(CDBObject p) {
				// not implemented
			}
	
			// BSBind_Multiplex
			//   String  Name
			//   Map  Nodes
			void readMultiplex(CDBObject p) {
				// not implemented
			}
			 
			// BSMaterial_OpacityComponent
			//   String  FirstLayerIndex
			//   boolean  SecondLayerActive
			//   String  SecondLayerIndex
			//   String  FirstBlenderIndex
			//   String  FirstBlenderMode
			//   boolean  ThirdLayerActive
			//   String  ThirdLayerIndex
			//   String  SecondBlenderIndex
			//   String  SecondBlenderMode
			//   Float  SpecularOpacityOverride
			void readOpacityComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				m.setFlags(CE2Material.Flag_HasOpacityComponent, true);
				m.opacityLayer1 = readLayerNumber(m.opacityLayer1, p, 0);
				int tmp = readBool(p, 1);
				if (tmp!=-1)
					m.setFlags(CE2Material.Flag_OpacityLayer2Active, tmp==1);
				m.opacityLayer2 = readLayerNumber(m.opacityLayer2, p, 2);
				m.opacityBlender1 = readBlenderNumber(m.opacityBlender1, p, 3);
				m.opacityBlender1Mode = readEnum(m.opacityBlender1Mode, p, 4,		blenderModeNames);
				tmp = readBool(p, 5);
				if (tmp!=-1)
					m.setFlags(CE2Material.Flag_OpacityLayer3Active, tmp==1);
				m.opacityLayer3 = readLayerNumber(m.opacityLayer3, p, 6);
				m.opacityBlender2 = readBlenderNumber(m.opacityBlender2, p, 7);
				m.opacityBlender2Mode = readEnum(m.opacityBlender2Mode, p, 8,			blenderModeNames);
				m.specularOpacityOverride = readFloat(m.specularOpacityOverride, p, 9);
			}
	
			// BSMaterial_BlendParamFloat
			//   Float  Value
			void readBlendParamFloat(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_ColorRemapSettingsComponent
			//   boolean  RemapAlbedo
			//   boolean  RemapOpacity
			//   boolean  RemapEmissive
			//   BSMaterial_SourceTextureWithReplacement  AlbedoPaletteTex
			//   BSMaterial_Color  AlbedoTint
			//   BSMaterial_SourceTextureWithReplacement  AlphaPaletteTex
			//   Float  AlphaTint
			//   BSMaterial_SourceTextureWithReplacement  EmissivePaletteTex
			//   BSMaterial_Color  EmissiveTint
			void readColorRemapSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_EyeSettingsComponent
			//    boolean  Enabled
			//   Float  ScleraEyeRoughness
			//   Float  CorneaEyeRoughness
			//   Float  ScleraSpecularity
			//   Float  IrisSpecularity
			//   Float  CorneaSpecularity
			//   Float  IrisDepthPosition
			//   Float  IrisTotalDepth
			//   Float  DepthScale
			//   Float  IrisDepthTransitionRatio
			//   Float  IrisUVSize
			//   Float  LightingWrap
			//   Float  LightingPower
			void readEyeSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSBind_Float2DLerpController
			//   Float  Duration
			//   boolean  Loop
			//   XMFLOAT2  Start
			//   XMFLOAT2  End
			//   String  Easing
			//   String  Mask
			void readFloat2DLerpController(CDBObject p) {
				// not implemented
			}
	
			// BSComponentDB2_ID
			//   UInt32  Value
			CE2MaterialObject readBSComponentDB2ID(CDBObject p, int typeRequired) {
				if (p != null	&& p.type > REFLArchive.String_Unknown && p.childCnt() >= 1 && p.children()[0] != null
					&& p.children()[0].type == REFLArchive.String_BSComponentDB2_ID) {
					CE2MaterialObject tmp = findMaterialObject(p.children()[0].linkedObject());
					if (typeRequired != 0 && tmp != null && tmp.type != typeRequired)
						tmp = null;
					return tmp;
				}
	
				return null;//p could be null or whatever
			}
	
			// BSMaterial_TextureReplacement
			//   boolean  Enabled
			//   BSMaterial_Color  Color
			void readTextureReplacement(CDBObject p) {
				int i = componentData.key & 0xFFFF;
				if (o.type == 5 && i < CE2Material.TextureSet.maxTexturePaths) {
					CE2Material.TextureSet txtSet = (CE2Material.TextureSet)(o);
					boolean isEnabled = readBool(false, p, 0);
					if (isEnabled) {
						if (!isEnabled)
							txtSet.textureReplacementMask &= ~(1 << i);
						else
							txtSet.textureReplacementMask |= (1 << i);
					}
					txtSet.textureReplacements[i] = readColorValue(txtSet.textureReplacements[i], p, 1);
				} else if (o.type == 2) {
					CE2Material.Blender blender = (CE2Material.Blender)(o);
					blender.textureReplacementEnabled = readBool(blender.textureReplacementEnabled, p, 0);
					blender.textureReplacement = readColorValue(blender.textureReplacement, p, 1);
				}
			}
			 
			// BSMaterial_BlendModeComponent
			//   String  Value
			void readBlendModeComponent(CDBObject p) {
				if (o.type == 2) {
					((CE2Material.Blender)o).blendMode = readEnum(((CE2Material.Blender)o).blendMode, p, 0, alphaBlendModeNames);
				}
			}
	
			// BSMaterial_LayeredEdgeFalloffComponent
			//   List  FalloffStartAngles
			//   List  FalloffStopAngles
			//   List  FalloffStartOpacities
			//   List  FalloffStopOpacities
			//   UInt8  ActiveLayersMask
			//   boolean  UseRGBFallOff
			void readLayeredEdgeFalloffComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.LayeredEdgeFalloff sp = new CE2Material.LayeredEdgeFalloff();
				m.layeredEdgeFalloff = sp;
				for (int i = 0; i < 4; i++) {
					if (!(p != null && p.type > REFLArchive.String_Unknown && i < p.childCnt() && p.children()[i] != null
							&& p.children()[i].type == REFLArchive.String_List)) {
						continue;
					}
					CDBObject q = p.children()[i];
					float[] tmp = (sp.falloffStartAngles);
					if (i == 1)
						tmp = (sp.falloffStopAngles);
					else if (i == 2)
						tmp = (sp.falloffStartOpacities);
					else if (i == 3)
						tmp = (sp.falloffStopOpacities);
					for (int j = 0; j < 3; j++) {
						if (j < q.childCnt() && q.children()[j] != null && q.children()[j].type == REFLArchive.String_Float) {
							tmp[j] = q.children()[j].floatValue();
						}
					}
				}
				byte tmp = readUInt8((byte)0, p, 4);
				if (tmp != 0) {
					tmp = (byte)(tmp & 7);
					sp.activeLayersMask = tmp;
					m.setFlags(CE2Material.Flag_LayeredEdgeFalloff, (tmp != 0));
				}
				sp.useRGBFalloff = readBool(sp.useRGBFalloff, p, 5);
			}
	
			// BSMaterial_VegetationSettingsComponent
			//   boolean  Enabled
			//   Float  LeafFrequency
			//   Float  LeafAmplitude
			//   Float  BranchFlexibility
			//   Float  TrunkFlexibility
			//   Float  DEPRECATEDTerrainBlendStrength
			//   Float  DEPRECATEDTerrainBlendGradientFactor
			void readVegetationSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.VegetationSettings sp = new CE2Material.VegetationSettings();
				m.vegetationSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_IsVegetation, tmp==1);
					sp.isEnabled = tmp==1;
				}
				sp.leafFrequency = readFloat(sp.leafFrequency ,p, 1);
				sp.leafAmplitude = readFloat(sp.leafAmplitude, p, 2);
				sp.branchFlexibility = readFloat(sp.branchFlexibility, p, 3);
				sp.trunkFlexibility = readFloat(sp.trunkFlexibility, p, 4);
				sp.terrainBlendStrength = readFloat(sp.terrainBlendStrength, p, 5);
				sp.terrainBlendGradientFactor = readFloat(sp.terrainBlendGradientFactor, p, 6);
			}
	
			// BSMaterial_TextureResolutionSetting
			//   String  ResolutionHint
			void readTextureResolutionSetting(CDBObject p) {
				if (o.type == 5) {
					((CE2Material.TextureSet)o).resolutionHint = readEnum(((CE2Material.TextureSet)o).resolutionHint, p, 0,
							resolutionSettingNames);
				}
			}
	
			// BSBind_Address
			//   List  Path
			void readAddress(CDBObject p) {
				// not implemented
			}
	
			// BSBind_DirectoryComponent
			//   Ref  upDir
			void readDirectoryComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterialBinding_MaterialUVStreamPropertyNode
			//   String  Name
			//   BSMaterial_UVStreamID  StreamID
			//   String  BindingType
			void readMaterialUVStreamPropertyNode(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_ShaderRouteComponent
			//   String  Route
			void readShaderRouteComponent(CDBObject p) {
				if (o.type == 1) {
					((CE2Material)o).shaderRoute = readEnum(((CE2Material)o).shaderRoute, p, 0,
							new String[]{"Deferred","Effect","PlanetaryRing","PrecomputedScattering","Water"});
				}
			}
	
			// BSBind_FloatLerpController
			//   Float  Duration
			//   boolean  Loop
			//   Float  Start
			//   Float  End
			//   String  Easing
			void readFloatLerpController(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_ColorChannelTypeComponent
			//   String  Value
			void readColorChannelTypeComponent(CDBObject p) {
				if (o.type == 2) {
					((CE2Material.Blender)o).colorChannel = readEnum(((CE2Material.Blender)o).colorChannel, p, 0, colorChannelNames);
				}
			}
	
			// BSMaterial_AlphaSettingsComponent
			//   boolean  HasOpacity
			//   Float  AlphaTestThreshold
			//   String  OpacitySourceLayer
			//   BSMaterial_AlphaBlenderSettings  Blender
			//   boolean  UseDitheredTransparency
			void readAlphaSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				int tmp = readBool(p, 0);
				if (tmp!=-1)
					m.setFlags(CE2Material.Flag_HasOpacity, tmp==1);
				m.alphaThreshold = readFloat(m.alphaThreshold, p, 1, true);
				m.alphaSourceLayer = readLayerNumber(m.alphaSourceLayer, p, 2);
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 4)
					readAlphaBlenderSettings(p.children()[3]);
				tmp = readBool(p, 4);
				if (tmp!=-1)
					m.setFlags(CE2Material.Flag_DitheredTransparency, tmp==1);
			}
	
			// BSMaterial_LevelOfDetailSettings
			//   UInt8  NumLODMaterials
			//   String  MostSignificantLayer
			//   String  SecondMostSignificantLayer
			//   String  MediumLODRootMaterial
			//   String  LowLODRootMaterial
			//   String  VeryLowLODRootMaterial
			//   Float  Bias
			void readLevelOfDetailSettings(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_TextureSetID
			//   BSComponentDB2_ID  ID
			void readTextureSetID(CDBObject p) {
				if (o.type != 4)
					return;
				CE2MaterialObject tmp = readBSComponentDB2ID(p, 5);
				if (tmp != null) {
					((CE2Material.Material)(o)).textureSet = (CE2Material.TextureSet)(tmp);
				}
			}
	
			// BSBind_Float2DCurveController
			//   BSFloat2DCurve  Curve
			//    boolean  Loop
			//   String  Mask
			void readFloat2DCurveController(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_TextureFile
			//   String  FileName
			void readTextureFile(CDBObject p) {
				if (componentData.className == String_BSMaterial_TextureFile) {
					readMRTextureFile(p);
					return;
				}
				if (componentData.className != String_BSMaterial_DecalSettingsComponent) {
					return;
				}
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.DecalSettings sp = (CE2Material.DecalSettings)(m.decalSettings);
				sp.surfaceHeightMap = readPath(sp.surfaceHeightMap, p, 0, "textures/", ".dds");
			}
	
			// BSMaterial_TranslucencySettings
			//   boolean  Thin
			//   boolean  FlipBackFaceNormalsInViewSpace
			//   boolean  UseSSS
			//   Float  SSSWidth
			//   Float  SSSStrength
			//   Float  TransmissiveScale
			//   Float  TransmittanceWidth
			//   Float  SpecLobe0RoughnessScale
			//   Float  SpecLobe1RoughnessScale
			//   String  TransmittanceSourceLayer
			void readTranslucencySettings(CDBObject p) {
				if (!(p != null && p.type == String_BSMaterial_TranslucencySettings))
					return;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.TranslucencySettings sp = (CE2Material.TranslucencySettings)(m.translucencySettings);
				sp.isThin = readBool(sp.isThin  , p, 0);
				sp.flipBackFaceNormalsInVS = readBool(sp.flipBackFaceNormalsInVS  , p, 1);
				sp.useSSS = readBool( sp.useSSS , p, 2);
				sp.sssWidth = readFloat(sp.sssWidth  , p, 3);
				sp.sssStrength = readFloat( sp.sssStrength , p, 4);
				sp.transmissiveScale = readFloat(sp.transmissiveScale  , p, 5);
				sp.transmittanceWidth = readFloat( sp.transmittanceWidth , p, 6);
				sp.specLobe0RoughnessScale = readFloat( sp.specLobe0RoughnessScale , p, 7);
				sp.specLobe1RoughnessScale = readFloat( sp.specLobe1RoughnessScale , p, 8);
				sp.sourceLayer = readLayerNumber( sp.sourceLayer , p, 9);
			}
	
			// BSMaterial_MouthSettingsComponent
			//   boolean  Enabled
			//   boolean  IsTeeth
			//   String  AOVertexColorChannel
			void readMouthSettingsComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_DistortionComponent
			//   boolean  Enabled
			//   Float  Strength
			//   boolean  UseVertexAlpha
			//   boolean  NormalAffectsStrength
			//   boolean  CameraDistanceFade
			//   Float  NearFadeValue
			//   Float  FarFadeValue
			//   String  OpacitySourceLayer
			//   Float  BlurStrength
			void readDistortionComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_DetailBlenderSettingsComponent
			//   BSMaterial_DetailBlenderSettings  DetailBlenderSettings
			void readDetailBlenderSettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.DetailBlenderSettings sp = new CE2Material.DetailBlenderSettings();
				m.detailBlenderSettings = sp;
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 1)
					readDetailBlenderSettings(p.children()[0]);
			}
	
			// BSMaterial_StarmapBodyEffectComponent
			//   boolean  Enabled
			//   String  Type
			void readStarmapBodyEffectComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_MaterialParamFloat
			//   Float  Value
			void readMaterialParamFloat(CDBObject p) {
				int i = componentData.key & 0xFFFF;
				if (o.type == 2 && i < CE2Material.Blender.maxFloatParams) {
					((CE2Material.Blender)(o)).floatParams[i] = readFloat(((CE2Material.Blender)(o)).floatParams[i],p, 0, true);
				} else if (o.type == 5) {
					((CE2Material.TextureSet)(o)).floatParam = readFloat(((CE2Material.TextureSet)(o)).floatParam, p, 0, true);
				}
			}
	
			// BSFloat2DCurve
			//   BSFloatCurve  XCurve
			//   BSFloatCurve  YCurve
			void readBSFloat2DCurve(CDBObject p) {
				// not implemented
			}
	
			// BSFloat3DCurve
			//   BSFloatCurve  XCurve
			//   BSFloatCurve  YCurve
			//   BSFloatCurve  ZCurve
			void readBSFloat3DCurve(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_TranslucencySettingsComponent
			//   boolean  Enabled
			//   BSMaterial_TranslucencySettings  Settings
			void readTranslucencySettingsComponent(CDBObject p) {
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.TranslucencySettings sp = new CE2Material.TranslucencySettings();
				m.translucencySettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					m.setFlags(CE2Material.Flag_Translucency, tmp==1);
					sp.isEnabled = tmp==1;
				}
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 2)
					readTranslucencySettings(p.children()[1]);
			}
	
			// BSMaterial_GlobalLayerNoiseSettings
			//   Float  MaterialMaskIntensityScale
			//   boolean  UseNoiseMaskTexture
			//   BSMaterial_SourceTextureWithReplacement  NoiseMaskTexture
			//   XMFLOAT4  TexcoordScaleAndBias
			//   Float  WorldspaceScaleFactor
			//   Float  HurstExponent
			//   Float  BaseFrequency
			//   Float  FrequencyMultiplier
			//   Float  MaskIntensityMin
			//   Float  MaskIntensityMax
			void readGlobalLayerNoiseSettings(CDBObject p) {
				if (p == null || p.type != String_BSMaterial_GlobalLayerNoiseSettings)
					return;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.GlobalLayerData sp = (CE2Material.GlobalLayerData)(m.globalLayerData);
				sp.materialMaskIntensityScale = readFloat(sp.materialMaskIntensityScale, p, 0);
				sp.useNoiseMaskTexture = readBool(sp.useNoiseMaskTexture, p, 1);
				//readSourceTextureWithReplacement(sp.noiseMaskTexture, sp.noiseMaskTextureReplacement,
				//		sp.noiseMaskTxtReplacementEnabled, p, 2);
				TexWithRep twr = readSourceTextureWithReplacement(p, 2);
				if (twr != null) {
					sp.noiseMaskTexture = twr.texturePath;
					sp.noiseMaskTextureReplacement = twr.textureReplacement;
					sp.noiseMaskTxtReplacementEnabled = twr.textureReplacementEnabled;
				}
				readXMFLOAT4(sp.texcoordScaleAndBias, p, 3);
				sp.worldspaceScaleFactor = readFloat(sp.worldspaceScaleFactor , p, 4);
				sp.hurstExponent = readFloat(sp.hurstExponent, p, 5);
				sp.baseFrequency = readFloat(sp.baseFrequency, p, 6);
				sp.frequencyMultiplier = readFloat(sp.frequencyMultiplier, p, 7);
				sp.maskIntensityMin = readFloat(sp.maskIntensityMin, p, 8);
				sp.maskIntensityMax = readFloat(sp.maskIntensityMax, p, 9);
			}
	
			// BSMaterial_MaterialID
			//   BSComponentDB2_ID  ID
			void readMaterialID(CDBObject p) {
				if (o.type != 3)
					return;
				CE2MaterialObject tmp = readBSComponentDB2ID(p, 4);
				if (tmp != null) {
					((CE2Material.Layer)(o)).material = (CE2Material.Material)(tmp);
				}
			}
	
			// XMFLOAT2
			//   Float  x
			//   Float  y
			void readXMFLOAT2L(FloatVector4 v, CDBObject p, int fieldNum) {
				if (!(p != null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()))
					return;
				CDBObject q = p.children()[fieldNum];
				if (!(q != null && q.type == REFLArchive.String_XMFLOAT2 && q.childCnt() == 2))
					return;
				v.x = readFloat(v.x, q, 0);
				v.y = readFloat(v.y, q, 1);
			}
	
			void readXMFLOAT2H(FloatVector4 v, CDBObject p, int fieldNum) {
				if (!(p != null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()))
					return;
				CDBObject q = p.children()[fieldNum];
				if (!(q != null && q.type == REFLArchive.String_XMFLOAT2 && q.childCnt() == 2))
					return;
				v.z = readFloat(v.z, q, 0);
				v.w = readFloat(v.w, q, 1);
			}
	
			// BSBind_TimerController
			void readTimerController(CDBObject p) {
				// not implemented
			}
	
			// BSBind_Controllers
			//   List  MappingsA
			//   boolean  UseRandomOffset
			void readControllers(CDBObject p) {
				// not implemented
			}
	
			 
			// BSMaterial_EmittanceSettings
			//   String  EmissiveSourceLayer
			//   BSMaterial_Color  EmissiveTint
			//   String  EmissiveMaskSourceBlender
			//   Float  EmissiveClipThreshold
			//   boolean  AdaptiveEmittance
			//   Float  LuminousEmittance
			//   Float  ExposureOffset
			//   boolean  EnableAdaptiveLimits
			//   Float  MaxOffsetEmittance
			//   Float  MinOffsetEmittance
			void readEmittanceSettings(CDBObject p) {
				if (!(p != null && p.type == String_BSMaterial_EmittanceSettings))
					return;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.EmissiveSettings sp = (CE2Material.EmissiveSettings)(m.emissiveSettings);
				sp.sourceLayer = readLayerNumber(sp.sourceLayer, p, 0);
				readColorValues(sp.emissiveTint, p, 1);
				sp.maskSourceBlender = readEnum(sp.maskSourceBlender, p, 2, maskSourceBlenderNames);
				sp.clipThreshold = readFloat(sp.clipThreshold, p, 3);
				sp.adaptiveEmittance = readBool(sp.adaptiveEmittance, p, 4);
				sp.luminousEmittance = readFloat(sp.luminousEmittance, p, 5);
				sp.exposureOffset = readFloat(sp.exposureOffset, p, 6);
				sp.enableAdaptiveLimits = readBool(sp.enableAdaptiveLimits, p, 7);
				sp.maxOffset = readFloat(sp.maxOffset, p, 8);
				sp.minOffset = readFloat(sp.minOffset, p, 9);
			}
	
			// BSMaterial_ShaderModelComponent
			//   String  FileName
			void readShaderModelComponent(CDBObject p) {
				if (o.type != 1)
					return;
				String s = readString(null, p, 0);
				if (s == null)
				  return;
				CE2Material m = (CE2Material)(o);
				int n0 = 0;
				int n2 = (shaderModelNames.length) / 1;//sizeof(char *);
				while ((n2 - n0) >= 2) {
					int n1 = (n0 + n2) >> 1;
					if ((s.compareTo(shaderModelNames[n1])) < 0)
						n2 = n1;
					else
						n0 = n1;
				}
				byte tmp = 55; // "Unknown"
				if (s.compareTo(shaderModelNames[n0]) == 0)
					tmp = (byte)n0;
				m.shaderModel = tmp;
				m.setFlags(CE2Material.Flag_TwoSided, ((1 << tmp) & 0xF060000000000000L) != 0);
			}
	
			// BSResource_ID
			//   UInt32  Dir
			//   UInt32  File
			//   UInt32  Ext
			void readBSResourceID(CDBObject p) {
				// not implemented
			}
	
			// XMFLOAT3
			//   Float  x
			//   Float  y
			//   Float  z
			void readXMFLOAT3(FloatVector4 v, CDBObject p, int fieldNum) {
				if (!(p != null && p.type > REFLArchive.String_Unknown && fieldNum < p.childCnt()))
					return;
				CDBObject q = p.children()[fieldNum];
				if (!(q != null && q.type == REFLArchive.String_XMFLOAT3 && q.childCnt() == 3))
					return;
				v.x = readFloat(v.x,q, 0);
				v.y = readFloat(v.y,q, 1);
				v.z = readFloat(v.z,q, 2);
			}
	
			// BSMaterial_MaterialOverrideColorTypeComponent
			//   String  Value
			void readMaterialOverrideColorTypeComponent(CDBObject p) {
				if (o.type != 4)
					return;
				byte tmp = readEnum((byte)-1, p, 0, colorModeNames);
				if (tmp!=-1)
				{
					((CE2Material.Material)(o)).colorModeFlags = (byte)((((CE2Material.Material)(o)).colorModeFlags & 2)
																		| tmp);
				}
			}
	
			// BSMaterial_Channel
			//   String  Value
			void readChannel(CDBObject p) {
				if (!(o.type == 6 && componentData.className == String_BSMaterial_Channel)) {
					return;
				}
				((CE2Material.UVStream)o).channel = readEnum(((CE2Material.UVStream)o).channel,p, 0, channelNames );
			}
	
			// BSBind_ColorCurveController
			//   BSColorCurve  Curve
			//   boolean  Loop
			//   String  Mask
			void readColorCurveController(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_HairSettingsComponent
			//   boolean  Enabled
			//   boolean  IsSpikyHair
			//   Float  SpecScale
			//   Float  SpecularTransmissionScale
			//   Float  DirectTransmissionScale
			//   Float  DiffuseTransmissionScale
			//   Float  Roughness
			//   Float  ContactShadowSoftening
			//   Float  BackscatterStrength
			//   Float  BackscatterWrap
			//   Float  VariationStrength
			//   Float  IndirectSpecularScale
			//   Float  IndirectSpecularTransmissionScale
			//   Float  IndirectSpecRoughness
			//   Float  AlphaDistance
			//   Float  MipBase
			//   Float  AlphaBias
			//   Float  EdgeMaskContrast
			//   Float  EdgeMaskMin
			//   Float  EdgeMaskDistanceMin
			//   Float  EdgeMaskDistanceMax
			//   Float  MaxDepthOffset
			//   Float  DitherScale
			//   Float  DitherDistanceMin
			//   Float  DitherDistanceMax
			//   XMFLOAT3  Tangent
			//   Float  TangentBend
			//   String  DepthOffsetMaskVertexColorChannel
			//   String  AOVertexColorChannel
			// NOTE: AlphaDistance, MipBase and AlphaBias have been removed
			// as of version 1.10.30.0
			void readHairSettingsComponent(CDBObject p) {
				//(void) p;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				CE2Material.HairSettings sp = new CE2Material.HairSettings();
				m.hairSettings = sp;
				int tmp = readBool(p, 0);
				if (tmp!=-1) {
					sp.isEnabled = tmp==1;
					m.setFlags(CE2Material.Flag_IsHair, tmp==1);
				}
				tmp = readBool(p, 1);
				if (tmp!=-1)
					sp.isSpikyHair = tmp==1;
				sp.specScale = readFloat(sp.specScale, p, 2);
				sp.specularTransmissionScale = readFloat(sp.specularTransmissionScale, p, 3);
				sp.directTransmissionScale = readFloat(sp.directTransmissionScale, p, 4);
				sp.diffuseTransmissionScale = readFloat(sp.diffuseTransmissionScale, p, 5);
				sp.roughness = readFloat(sp.roughness, p, 6);
				sp.contactShadowSoftening = readFloat(sp.contactShadowSoftening, p, 7);
				sp.backscatterStrength = readFloat(sp.backscatterStrength, p, 8);
				sp.backscatterWrap = readFloat(sp.backscatterWrap, p, 9);
				sp.variationStrength = readFloat(sp.variationStrength, p, 10);
				sp.indirectSpecularScale = readFloat(sp.indirectSpecularScale, p, 11);
				sp.indirectSpecularTransmissionScale = readFloat(sp.indirectSpecularTransmissionScale, p, 12);
				sp.indirectSpecRoughness = readFloat(sp.indirectSpecRoughness, p, 13);
				int n = 0;
				if (p.childCnt() >= 29)
					n = 3; // skip deprecated fields
				sp.edgeMaskContrast = readFloat(sp.edgeMaskContrast, p, n + 14);
				sp.edgeMaskMin = readFloat(sp.edgeMaskMin, p, n + 15);
				sp.edgeMaskDistanceMin = readFloat(sp.edgeMaskDistanceMin, p, n + 16);
				sp.edgeMaskDistanceMax = readFloat(sp.edgeMaskDistanceMax, p, n + 17);
				sp.maxDepthOffset = readFloat(sp.maxDepthOffset, p, n + 18);
				sp.ditherScale = readFloat(sp.ditherScale, p, n + 19);
				sp.ditherDistanceMin = readFloat(sp.ditherDistanceMin, p, n + 20);
				sp.ditherDistanceMax = readFloat(sp.ditherDistanceMax, p, n + 21);
				readXMFLOAT3(sp.tangent, p, n + 22);
				sp.tangent.w = readFloat(sp.tangent.w, p, n + 23);
				sp.depthOffsetMaskVertexColorChannel = readEnum(sp.depthOffsetMaskVertexColorChannel, p, n + 24, colorChannelNames);
				sp.aoVertexColorChannel = readEnum(sp.aoVertexColorChannel, p, n + 25, colorChannelNames);
			}
	
			// BSColorCurve
			//   BSFloatCurve  RedChannel
			//   BSFloatCurve  GreenChannel
			//   BSFloatCurve  BlueChannel
			//   BSFloatCurve  AlphaChannel
			void readBSColorCurve(CDBObject p) {
				// not implemented
			}
	
			// BSBind_ControllerComponent
			//   Ref  upControllers
			void readControllerComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_MRTextureFile
			//   String  FileName
			void readMRTextureFile(CDBObject p) {
				String txtPath;
				int txtMask;
				int i = componentData.key & 0xFFFF;
				if (o.type == 5 && i < CE2Material.TextureSet.maxTexturePaths) {
					txtPath = ((CE2Material.TextureSet)(o)).texturePaths[i];
					txtMask = ((CE2Material.TextureSet)(o)).texturePathMask;
				} else if (o.type == 2) {
					txtPath = ((CE2Material.Blender)(o)).texturePath;
					txtMask = i;
				} else {
					return;
				}
				if (!(txtPath == null || txtPath.length() == 0)) {
					if (componentData.className != String_BSMaterial_MRTextureFile) {
						return;
					}   
				}
				txtPath = readPath(null, p, 0, "textures/", ".dds");
				if (txtPath != null) {
					if ((txtPath).length() == 0)
						txtMask &= ~(1 << i);
					else
						txtMask |= (1 << i);
				}
			}
	
			// BSMaterial_TextureSetKindComponent
			//   String  Value
			void readTextureSetKindComponent(CDBObject p) {
				// not implemented
			}
	
			// BSMaterial_BlenderID
			//   BSComponentDB2_ID  ID
			void readBlenderID(CDBObject p) {
				int i = componentData.key & 0xFFFF;
				if (!(o.type == 1 && i < CE2Material.maxBlenders))
					return;
				CE2MaterialObject tmp = readBSComponentDB2ID(p, (byte)2);
				if (tmp != null) {
					((CE2Material)(o)).blenders[i] = (CE2Material.Blender)(tmp);
				}
			}
	
			// BSMaterial_CollisionComponent
			//   BSMaterial_PhysicsMaterialType  MaterialTypeOverride
			void readCollisionComponent(CDBObject p) {
				if (p != null && p.type > REFLArchive.String_Unknown && p.childCnt() >= 1)
					readPhysicsMaterialType(p.children()[0]);
			}
	
			// BSMaterial_TerrainSettingsComponent
			//   boolean  Enabled
			//   String  TextureMappingType
			//   Float  RotationAngle
			//   Float  BlendSoftness
			//   Float  TilingDistance
			//   Float  MaxDisplacement
			//   Float  DisplacementMidpoint
			void readTerrainSettingsComponent(CDBObject p) {
				//(void) p;
				if (o.type != 1)
					return;
				CE2Material m = (CE2Material)(o);
				m.setFlags(CE2Material.Flag_IsTerrain, true);
			}
	
			// BSMaterial_LODMaterialID
			//   BSComponentDB2_ID  ID
			void readLODMaterialID(CDBObject p) {
				int i = componentData.key & 0xFFFF;
				if (!(o.type == 1 && i < CE2Material.maxLODMaterials))
					return;
				CE2MaterialObject tmp = readBSComponentDB2ID(p, (byte)1);
				if (tmp != null) {
					((CE2Material)o).lodMaterials[i] = (CE2Material)(tmp);
				}
			}
	
			// BSMaterial_MipBiasSetting
			//    boolean  DisableMipBiasHint
			void readMipBiasSetting(CDBObject p) {
				if (o.type == 5) {
					((CE2Material.TextureSet)(o)).disableMipBiasHint = readBool(
							((CE2Material.TextureSet)(o)).disableMipBiasHint, p, 0);
				}
			}
	    
	    
	    
	
	
			void loadComponent(MaterialComponent p) {
				componentData = p;
				CDBObject q = p.o;
				switch (p.className) {
					case REFLArchive.String_BSBind_ControllerComponent:
						readControllerComponent(q);//not imp
						break;
					case REFLArchive.String_BSBind_DirectoryComponent:
						readDirectoryComponent(q);//not imp
						break;
					case REFLArchive.String_BSComponentDB_CTName:
						readCTName(q);
						break;
					case String_BSMaterial_AlphaSettingsComponent:
						readAlphaSettingsComponent(q);
						break;
					case String_BSMaterial_BlendModeComponent:
						readBlendModeComponent(q);
						break;
					case String_BSMaterial_BlendParamFloat:
						readBlendParamFloat(q);//not imp
						break;
					case String_BSMaterial_BlenderID:
						readBlenderID(q);
						break;
					case String_BSMaterial_Channel:
						readChannel(q);
						break;
					case String_BSMaterial_CollisionComponent:
						readCollisionComponent(q);
						break;
					case String_BSMaterial_Color:
						readColor(q);
						break;
					case String_BSMaterial_ColorChannelTypeComponent:
						readColorChannelTypeComponent(q);
						break;
					case String_BSMaterial_ColorRemapSettingsComponent:
						readColorRemapSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_DecalSettingsComponent:
						readDecalSettingsComponent(q);
						break;
					case String_BSMaterial_DetailBlenderSettingsComponent:
						readDetailBlenderSettingsComponent(q);
						break;
					case String_BSMaterial_DistortionComponent:
						readDistortionComponent(q);//not imp
						break;
					case String_BSMaterial_EffectSettingsComponent:
						readEffectSettingsComponent(q);
						break;
					case String_BSMaterial_EmissiveSettingsComponent:
						readEmissiveSettingsComponent(q);
						break;
					case String_BSMaterial_EyeSettingsComponent:
						readEyeSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_FlipbookComponent:
						readFlipbookComponent(q);
						break;
					case String_BSMaterial_FlowSettingsComponent:
						readFlowSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_GlobalLayerDataComponent:
						readGlobalLayerDataComponent(q);
						break;
					case String_BSMaterial_HairSettingsComponent:
						readHairSettingsComponent(q);
						break;
					case String_BSMaterial_LODMaterialID:
						readLODMaterialID(q);
						break;
					case String_BSMaterial_LayerID:
						readLayerID(q);
						break;
					case String_BSMaterial_LayeredEdgeFalloffComponent:
						readLayeredEdgeFalloffComponent(q);
						break;
					case String_BSMaterial_LayeredEmissivityComponent:
						readLayeredEmissivityComponent(q);
						break;
					case String_BSMaterial_LevelOfDetailSettings:
						readLevelOfDetailSettings(q);//not imp
						break;
					case String_BSMaterial_MRTextureFile:
						readMRTextureFile(q);
						break;
					case String_BSMaterial_MaterialID:
						readMaterialID(q);
						break;
					case String_BSMaterial_MaterialOverrideColorTypeComponent:
						readMaterialOverrideColorTypeComponent(q);
						break;
					case String_BSMaterial_MaterialParamFloat:
						readMaterialParamFloat(q);
						break;
					case String_BSMaterial_MipBiasSetting:
						readMipBiasSetting(q);
						break;
					case String_BSMaterial_MouthSettingsComponent:
						readMouthSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_Offset:
						readOffset(q);
						break;
					case String_BSMaterial_OpacityComponent:
						readOpacityComponent(q);
						break;
					case String_BSMaterial_ParamBool:
						readParamBool(q);
						break;
					case String_BSMaterial_Scale:
						readScale(q);
						break;
					case String_BSMaterial_ShaderModelComponent:
						readShaderModelComponent(q);
						break;
					case String_BSMaterial_ShaderRouteComponent:
						readShaderRouteComponent(q);
						break;
					case String_BSMaterial_StarmapBodyEffectComponent:
						readStarmapBodyEffectComponent(q);
						break;
					case String_BSMaterial_TerrainSettingsComponent:
						readTerrainSettingsComponent(q);
						break;
					case String_BSMaterial_TerrainTintSettingsComponent:
						readTerrainTintSettingsComponent(q);
						break;
					case String_BSMaterial_TextureAddressModeComponent:
						readTextureAddressModeComponent(q);
						break;
					case String_BSMaterial_TextureFile:
						readTextureFile(q);
						break;
					case String_BSMaterial_TextureReplacement:
						readTextureReplacement(q);
						break;
					case String_BSMaterial_TextureResolutionSetting:
						readTextureResolutionSetting(q);
						break;
					case String_BSMaterial_TextureSetID:
						readTextureSetID(q);
						break;
					case String_BSMaterial_TextureSetKindComponent:
						readTextureSetKindComponent(q);
						break;
					case String_BSMaterial_TranslucencySettingsComponent:
						readTranslucencySettingsComponent(q);
						break;
					case String_BSMaterial_UVStreamID:
						readUVStreamID(q);
						break;
					case String_BSMaterial_UVStreamParamBool:
						readUVStreamParamBool(q);//not imp
						break;
					case String_BSMaterial_VegetationSettingsComponent:
						readVegetationSettingsComponent(q);
						break;
					case String_BSMaterial_WaterFoamSettingsComponent:
						readWaterFoamSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_WaterGrimeSettingsComponent:
						readWaterGrimeSettingsComponent(q);//not imp
						break;
					case String_BSMaterial_WaterSettingsComponent:
						readWaterSettingsComponent(q);
						break;
				}
				
				
				
			}
	
			static enum MaterialFlagNames {
				Has_opacity("Has opacity", 0x00000001), //  0 (0x00000001)
				Alpha_uses_vertex_color("Alpha uses vertex color", 0x00000002), //  1 (0x00000002)
				Is_effect("Is effect", 0x00000004), //  2 (0x00000004)
				Is_decal("Is decal", 0x00000008), //  3 (0x00000008)
				Two_sided("Two sided", 0x00000010), //  4 (0x00000010)
				Is_vegetation("Is vegetation", 0x00000020), //  5 (0x00000020)
				Layered_emissivity("Layered emissivity", 0x00000040), //  6 (0x00000040)
				Emissive("Emissive", 0x00000080), //  7 (0x00000080)
				Is_translucent("Is translucent", 0x00000100), //  8 (0x00000100)
				Alpha_detail_blend_mask("Alpha detail blend mask", 0x00000200), //  9 (0x00000200)
				Dithered_transparency("Dithered transparency", 0x00000400), // 10 (0x00000400)
				Ordered_with_previous("Ordered with previous", 0x00000800), // 11 (0x00000800)
				Alpha_blending("Alpha blending", 0x00001000), // 12 (0x00001000)
				Has_vertex_colors("Has vertex colors", 0x00002000), // 13 (0x00002000)
				Is_water("Is water", 0x00004000), // 14 (0x00004000)
				Hidden("Hidden", 0x00008000), // 15 (0x00008000)
				Has_opacity_component("Has opacity component", 0x00010000), // 16 (0x00010000)
				Opacity_layer_2_active("Opacity layer 2 active", 0x00020000), // 17 (0x00020000)
				Opacity_layer_3_active("Opacity layer 3 active", 0x00040000), // 18 (0x00040000)
				Is_terrain("Is terrain", 0x00080000), // 19 (0x00080000)
				Is_hair("Is hair", 0x00100000), // 20 (0x00100000)
				Use_detail_blender("Use detail blender", 0x00200000), // 21 (0x00200000)
				Layered_edge_falloff("Layered edge falloff", 0x00400000), // 22 (0x00400000)
				Global_layer_data("Global layer data", 0x00800000), // 23 (0x00800000)
				Is_marker("Is marker", 0x01000000), // 24 (0x01000000)
				Unknown1("Unknown1", 0x02000000), // 25 (0x02000000)
				Unknown2("Unknown2", 0x04000000), // 26 (0x04000000)
				Unknown3("Unknown3", 0x08000000), // 27 (0x08000000)
				Unknown4("Unknown4", 0x10000000), // 28 (0x10000000)
				Unknown5("Unknown5", 0x20000000), // 29 (0x20000000)
				Unknown6("Unknown6", 0x40000000), // 30 (0x40000000)
				Unknown7("Unknown7", 0x80000000); // 31 (0x80000000)
	
				private String		name;
				private int			mask;
	
				MaterialFlagNames(String name, int mask) {
					this.name = name;
					this.mask = mask;
				}
	
				public String getName() {
					return name;
				}
	
				public int getMask() {
					return mask;
				}
			};
	 
			static String[]			shaderModelNames		= new String[] {"1LayerEffect",									//  0
				"1LayerEffectEmissive",																						//  1
				"1LayerEffectEmissiveOnly",																					//  2
				"1LayerEffectEmissiveOnlyRemap",																			//  3
				"1LayerEffectEmissiveRemap",																				//  4
				"1LayerEffectGlass",																						//  5
				"1LayerEffectGlassNoFrost",																					//  6
				"1LayerEffectRemap",																						//  7
				"1LayerEyebrow",																							//  8
				"1LayerMouth",																								//  9
				"1LayerPlanetDecal",																						// 10
				"1LayerStandard",																							// 11
				"1LayerStandardDecal",																						// 12
				"1LayerStandardDecalEmissive",																				// 13
				"2LayerEffect",																								// 14
				"2LayerEffectEmissive",																						// 15
				"2LayerEffectEmissiveOnly",																					// 16
				"2LayerEffectEmissiveOnlyRemap",																			// 17
				"2LayerEffectEmissiveRemap",																				// 18
				"2LayerEffectRemap",																						// 19
				"2LayerPlanetDecal",																						// 20
				"2LayerStandard",																							// 21
				"2LayerStandardEmissive",																					// 22
				"3LayerEffect",																								// 23
				"3LayerEffectEmissive",																						// 24
				"3LayerEffectEmissiveOnly",																					// 25
				"3LayerPlanetDecal",																						// 26
				"3LayerStandard",																							// 27
				"3LayerStandardEmissive",																					// 28
				"4LayerPlanetDecal",																						// 29
				"4LayerStandard",																							// 30
				"BaseMaterial",																								// 31
				"BodySkin1Layer",																							// 32
				"BodySkin2Layer",																							// 33
				"Character2Layer",																							// 34
				"Character3Layer",																							// 35
				"Character4Layer",																							// 36
				"CloudCard",																								// 37
				"ColorEmissive",																							// 38
				"Debug",																									// 39
				"Experimental",																								// 40
				"Eye1Layer",																								// 41
				"EyeWetness1Layer",																							// 42
				"GlobalLayer",																								// 43
				"Hair1Layer",																								// 44
				"Invisible",																								// 45
				"Occlusion",																								// 46
				"PlanetaryRing",																							// 47
				"Skin5Layer",																								// 48
				"StarmapBodyEffect",																						// 49
				"Terrain1Layer",																							// 50
				"TerrainEmissive1Layer",																					// 51
				"TranslucentThin1Layer",																					// 52
				"TranslucentTwoSided1Layer",																				// 53
				"TwoSided1Layer",																							// 54
				"Unknown",																									// 55
				"VegetationEmissive1Layer",																					// 56
				"VegetationEmissive2Layer",																					// 57
				"VegetationStandard1Layer",																					// 58
				"VegetationStandard2Layer",																					// 59
				"VegetationTranslucent1Layer",																				// 60
				"VegetationTranslucent2Layer",																				// 61
				"Water",																									// 62
				"Water1Layer"																								// 63
			};

			static String[]			shaderRouteNames		= new String[] { "Deferred", 		// 0
				"Effect", 			// 1
				"PlanetaryRing", 	// 2			
				"PrecomputedScattering", // 3
				"Water", 			// 4
				"Unknown", 			// 5
				"Unknown", 			// 6
				"Unknown"			// 7
				};

			static String[]			colorChannelNames		= new String[] {"Red", "Green", "Blue", "Alpha"};

			static String[]			alphaBlendModeNames		= new String[] {"Linear", "Additive", "PositionContrast",
				"None", "CharacterCombine", "Skin", "Unknown", "Unknown"};

			static String[]			blenderModeNames		= new String[] {"Lerp", "Additive", "Subtractive",
				"Multiplicative"};

			static String[]			textureAddressModeNames	= new String[] {"Wrap", "Clamp", "Mirror", "Border"};

			static String[]			colorModeNames			= new String[] {"Multiply", "Lerp"};

			static String[]			effectBlendModeNames	= new String[] {"AlphaBlend", "Additive", "SourceSoftAdditive",
				"Multiply", "DestinationSoftAdditive", "DestinationInvertedSoftAdditive", "TakeSmaller", "None"};

			static String[]			effectFlagNames			= new String[] {"UseFallOff", "UseRGBFallOff", "", "", "", "",
				"VertexColorBlend", "IsAlphaTested", "", "NoHalfResOptimization", "SoftEffect", "", "EmissiveOnlyEffect",
				"EmissiveOnlyAutomaticallyApplied", "ReceiveDirectionalShadows", "ReceiveNonDirectionalShadows", "IsGlass",
				"Frosting", "", "", "", "ZTest", "ZWrite", "", "BackLightingEnable", "", "", "", "ForceRenderBeforeClouds",
				"DepthMVFixup", "DepthMVFixupEdgesOnly", "ForceRenderBeforeOIT"};

			static String[]			decalBlendModeNames		= new String[] {"None", "Additive"};

			static String[]			decalRenderLayerNames	= new String[] {"Top", "Middle", "Bottom"};

			static String[]			maskSourceBlenderNames	= new String[] {"None", "Blender1", "Blender2", "Blender3"};

			static String[]			channelNames			= new String[] {"Zero", "One", "Two", "Three"};

			static String[]			resolutionSettingNames	= new String[] {"Tiling", "UniqueMap", "DetailMapTiling",
				"HighResUniqueMap"};

			static String[]			physicsMaterialNames	= new String[] {"None", "Carpet", "Mat",
				"MaterialGroundTileVinyl", "MaterialMat", "MaterialPHYIceDebrisLarge", "Metal", "Wood"};
		}
		  
	
		public static HashMap<BSResourceID, CE2MaterialObject> materialObjectMap = new HashMap<BSResourceID, CE2MaterialObject>();
	 
		
		/**  
		 * This requires the p.components linked list to be filled in
		 * @param p
		 * @return
		 */
		static CE2MaterialObject findMaterialObject(MaterialObject p) {
			if (p == null)
				return null;
	
			CE2MaterialObject o = materialObjectMap.get(p.persistentID);
			if (o != null)
				return (CE2MaterialObject)(o);
	
			o = null;
	
			MaterialObject q = p;//q is about to go to to the topmost base object to figure which of the 6 base types we are loading in
			while (q.baseObject != null)
				q = q.baseObject;
	
			if (q.persistentID.dir != 0x1D95562F || // "materials\\layered\\root"
				q.persistentID.ext != 0x0074616D)// "mat\0"
			{
				return null;
			}
			// allocate object and initialize with defaults	    
			if (q.persistentID.file == 0x7EA3660C) // "layeredmaterials"
				o = new CE2Material();
			else if (q.persistentID.file == 0x8EBE84FF) // "blenders"
				o = new CE2Material.Blender();
			else if (q.persistentID.file == 0x574A4CF3) // "layers"
				o = new CE2Material.Layer();
			else if (q.persistentID.file == 0x7D1E021B) // "materials"
				o = new CE2Material.Material();
			else if (q.persistentID.file == 0x06F52154) // "texturesets"
				o = new CE2Material.TextureSet();
			else if (q.persistentID.file == 0x4298BB09) // "uvstreams"
				o = new CE2Material.UVStream();
	
			if (o == null)
				return null;
	
			o.cdbObject = p;
			o.name = null; 
			materialObjectMap.put(p.persistentID, o);
			o.parent = findMaterialObject(p.parent);
			// load components
			ComponentInfo componentInfo = new ComponentInfo(o);
			for (MaterialComponent q2 = p.components; q2 != null; q2 = q2.next) {
				componentInfo.loadComponent(q2);
			}
			return o;
		}
				
		public static CE2Material loadMaterial(String materialPath) {
			if (materialPath.length() == 0)
				return null;
			BSResourceID objectID = new BSResourceID(materialPath);
			CE2MaterialObject o = null;

			o = materialObjectMap.get(objectID);
			if (o == null) {
				o = findMaterialObject(getMaterial(objectID));
			}

			if (!(o != null && o.type == 1))
				return null;
			return (CE2Material)(o);
		}
				
		
		/*void getMaterialList(
			    std::set< std::string_view >& materialPaths, 
			    AllocBuffers& buf,
			    bool excludeJSONMaterials,
			    bool (*fileFilterFunc)(void *p, const std::string_view& s),
			    void *fileFilterFuncData)  {
			  std::map< std::uint32_t, std::string >  dirNameMap;
			  getStarfieldMaterialDirMap(dirNameMap);
			  std::vector< const BSMaterialsCDB::MaterialObject * > matObjects;
			  BSMaterialsCDB::getMaterials(matObjects);
			  std::string tmp;
			  for (size_t i = 0; i < matObjects.size(); i++) {
			    const BSMaterialsCDB::MaterialObject  *o = matObjects[i];
			    if (!o->parent && o->persistentID.ext == 0x0074616D &&      // "mat\0"
			        o->baseObject && o->baseObject->persistentID.file == 0x7EA3660C &&
			        )
			    {                                                   // "layeredmaterials"
			      const char  *baseName = nullptr;
			      for (const BSMaterialsCDB::MaterialComponent *j = o->components; j;
			           j = j->next)
			      {
			        if (j->className == BSReflStream::String_BSComponentDB_CTName &&
			            j->o && j->o->type == BSReflStream::String_BSComponentDB_CTName &&
			            j->o->childCnt >= 1 && j->o->children()[0] &&
			            j->o->children()[0]->type == BSReflStream::String_String)
			        {
			          baseName = j->o->children()[0]->stringValue();
			          break;
			        }
			      }
			      if (!(baseName && *baseName))
			        continue;
			      std::map< std::uint32_t, std::string >::const_iterator  j =
			          dirNameMap.find(o->persistentID.dir);
			      if (j == dirNameMap.end()) {	 
			          continue;
			      }
			      tmp = baseName;
			      std::uint32_t h = 0U;
			      for (size_t l = 0; l < tmp.length(); l++) {
			        if (tmp[l] >= 'A' && tmp[l] <= 'Z')
			          tmp[l] = tmp[l] + ('a' - 'A');
			        hashFunctionCRC32(h, (unsigned char) tmp[l]);
			      }
			      if (h != o->persistentID.file)
			        continue;
			      tmp.insert(0, j->second);
			      tmp += ".mat";
			      if (fileFilterFunc && !fileFilterFunc(fileFilterFuncData, tmp))
			        continue;
			      char    *s = buf.allocateObjects< char >(tmp.length() + 1);
			      std::memcpy(s, tmp.c_str(), tmp.length());
			      materialPaths.emplace(s, tmp.length());
			    }
			  }
			   
			}
			
			 */
		
			 
		
		
	}
	

	

	 

	//TODO: should this extend nifvec4 or something	
	public static class FloatVector4 {
		public float x, y, z, w;

		public FloatVector4(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}
		public FloatVector4(FloatVector4 v) {
			this.x = v.x;
			this.y = v.y;
			this.z = v.z;
			this.w = v.w;
		}

		public FloatVector4 minValues(FloatVector4 c) {
			this.x = c.x > this.x ? c.x : this.x;
			this.y = c.y > this.y ? c.y : this.y;
			this.z = c.z > this.z ? c.z : this.z;
			this.w = c.w > this.w ? c.w : this.w;
			return this;
		}

		public FloatVector4 maxValues(FloatVector4 c) {
			this.x = c.x < this.x ? c.x : this.x;
			this.y = c.y < this.y ? c.y : this.y;
			this.z = c.z < this.z ? c.z : this.z;
			this.w = c.w < this.w ? c.w : this.w;
			return this;
		}

		public FloatVector4() {
			this.x = 0;
			this.y = 0;
			this.z = 0;
			this.w = 0;
		}

		public FloatVector4(float v) {
			this.x = v;
			this.y = v;
			this.z = v;
			this.w = v;
		}

		public void mult(float v) {
			this.x *= v;
			this.y *= v;
			this.z *= v;
			this.w *= v;
		}
		
		public void div(float v) {
			this.x /= v;
			this.y /= v;
			this.z /= v;
			this.w /= v;
		}
		public void add(float v) {
			this.x += v;
			this.y += v;
			this.z += v;
			this.w += v;
		}
		public void sub(float v) {
			this.x -= v;
			this.y -= v;
			this.z -= v;
			this.w -= v;
		}
		public void set(FloatVector4 v) {
			this.x = v.x;
			this.y = v.y;
			this.z = v.z;
			this.w = v.w;
		}
		public void add(FloatVector4 v) {
			this.x += v.x;
			this.y += v.y;
			this.z += v.z;
			this.w += v.w;
		}
	}
	
	 

	private static long[] crc32Table_EDB88320 = new long[] {0x00000000L, 0x77073096L, 0xEE0E612CL, 0x990951BAL,
		0x076DC419L, 0x706AF48FL, 0xE963A535L, 0x9E6495A3L, 0x0EDB8832L, 0x79DCB8A4L, 0xE0D5E91EL, 0x97D2D988L,
		0x09B64C2BL, 0x7EB17CBDL, 0xE7B82D07L, 0x90BF1D91L, 0x1DB71064L, 0x6AB020F2L, 0xF3B97148L, 0x84BE41DEL,
		0x1ADAD47DL, 0x6DDDE4EBL, 0xF4D4B551L, 0x83D385C7L, 0x136C9856L, 0x646BA8C0L, 0xFD62F97AL, 0x8A65C9ECL,
		0x14015C4FL, 0x63066CD9L, 0xFA0F3D63L, 0x8D080DF5L, 0x3B6E20C8L, 0x4C69105EL, 0xD56041E4L, 0xA2677172L,
		0x3C03E4D1L, 0x4B04D447L, 0xD20D85FDL, 0xA50AB56BL, 0x35B5A8FAL, 0x42B2986CL, 0xDBBBC9D6L, 0xACBCF940L,
		0x32D86CE3L, 0x45DF5C75L, 0xDCD60DCFL, 0xABD13D59L, 0x26D930ACL, 0x51DE003AL, 0xC8D75180L, 0xBFD06116L,
		0x21B4F4B5L, 0x56B3C423L, 0xCFBA9599L, 0xB8BDA50FL, 0x2802B89EL, 0x5F058808L, 0xC60CD9B2L, 0xB10BE924L,
		0x2F6F7C87L, 0x58684C11L, 0xC1611DABL, 0xB6662D3DL, 0x76DC4190L, 0x01DB7106L, 0x98D220BCL, 0xEFD5102AL,
		0x71B18589L, 0x06B6B51FL, 0x9FBFE4A5L, 0xE8B8D433L, 0x7807C9A2L, 0x0F00F934L, 0x9609A88EL, 0xE10E9818L,
		0x7F6A0DBBL, 0x086D3D2DL, 0x91646C97L, 0xE6635C01L, 0x6B6B51F4L, 0x1C6C6162L, 0x856530D8L, 0xF262004EL,
		0x6C0695EDL, 0x1B01A57BL, 0x8208F4C1L, 0xF50FC457L, 0x65B0D9C6L, 0x12B7E950L, 0x8BBEB8EAL, 0xFCB9887CL,
		0x62DD1DDFL, 0x15DA2D49L, 0x8CD37CF3L, 0xFBD44C65L, 0x4DB26158L, 0x3AB551CEL, 0xA3BC0074L, 0xD4BB30E2L,
		0x4ADFA541L, 0x3DD895D7L, 0xA4D1C46DL, 0xD3D6F4FBL, 0x4369E96AL, 0x346ED9FCL, 0xAD678846L, 0xDA60B8D0L,
		0x44042D73L, 0x33031DE5L, 0xAA0A4C5FL, 0xDD0D7CC9L, 0x5005713CL, 0x270241AAL, 0xBE0B1010L, 0xC90C2086L,
		0x5768B525L, 0x206F85B3L, 0xB966D409L, 0xCE61E49FL, 0x5EDEF90EL, 0x29D9C998L, 0xB0D09822L, 0xC7D7A8B4L,
		0x59B33D17L, 0x2EB40D81L, 0xB7BD5C3BL, 0xC0BA6CADL, 0xEDB88320L, 0x9ABFB3B6L, 0x03B6E20CL, 0x74B1D29AL,
		0xEAD54739L, 0x9DD277AFL, 0x04DB2615L, 0x73DC1683L, 0xE3630B12L, 0x94643B84L, 0x0D6D6A3EL, 0x7A6A5AA8L,
		0xE40ECF0BL, 0x9309FF9DL, 0x0A00AE27L, 0x7D079EB1L, 0xF00F9344L, 0x8708A3D2L, 0x1E01F268L, 0x6906C2FEL,
		0xF762575DL, 0x806567CBL, 0x196C3671L, 0x6E6B06E7L, 0xFED41B76L, 0x89D32BE0L, 0x10DA7A5AL, 0x67DD4ACCL,
		0xF9B9DF6FL, 0x8EBEEFF9L, 0x17B7BE43L, 0x60B08ED5L, 0xD6D6A3E8L, 0xA1D1937EL, 0x38D8C2C4L, 0x4FDFF252L,
		0xD1BB67F1L, 0xA6BC5767L, 0x3FB506DDL, 0x48B2364BL, 0xD80D2BDAL, 0xAF0A1B4CL, 0x36034AF6L, 0x41047A60L,
		0xDF60EFC3L, 0xA867DF55L, 0x316E8EEFL, 0x4669BE79L, 0xCB61B38CL, 0xBC66831AL, 0x256FD2A0L, 0x5268E236L,
		0xCC0C7795L, 0xBB0B4703L, 0x220216B9L, 0x5505262FL, 0xC5BA3BBEL, 0xB2BD0B28L, 0x2BB45A92L, 0x5CB36A04L,
		0xC2D7FFA7L, 0xB5D0CF31L, 0x2CD99E8BL, 0x5BDEAE1DL, 0x9B64C2B0L, 0xEC63F226L, 0x756AA39CL, 0x026D930AL,
		0x9C0906A9L, 0xEB0E363FL, 0x72076785L, 0x05005713L, 0x95BF4A82L, 0xE2B87A14L, 0x7BB12BAEL, 0x0CB61B38L,
		0x92D28E9BL, 0xE5D5BE0DL, 0x7CDCEFB7L, 0x0BDBDF21L, 0x86D3D2D4L, 0xF1D4E242L, 0x68DDB3F8L, 0x1FDA836EL,
		0x81BE16CDL, 0xF6B9265BL, 0x6FB077E1L, 0x18B74777L, 0x88085AE6L, 0xFF0F6A70L, 0x66063BCAL, 0x11010B5CL,
		0x8F659EFFL, 0xF862AE69L, 0x616BFFD3L, 0x166CCF45L, 0xA00AE278L, 0xD70DD2EEL, 0x4E048354L, 0x3903B3C2L,
		0xA7672661L, 0xD06016F7L, 0x4969474DL, 0x3E6E77DBL, 0xAED16A4AL, 0xD9D65ADCL, 0x40DF0B66L, 0x37D83BF0L,
		0xA9BCAE53L, 0xDEBB9EC5L, 0x47B2CF7FL, 0x30B5FFE9L, 0xBDBDF21CL, 0xCABAC28AL, 0x53B39330L, 0x24B4A3A6L,
		0xBAD03605L, 0xCDD70693L, 0x54DE5729L, 0x23D967BFL, 0xB3667A2EL, 0xC4614AB8L, 0x5D681B02L, 0x2A6F2B94L,
		0xB40BBE37L, 0xC30C8EA1L, 0x5A05DF1BL, 0x2D02EF8DL};
	
	//from PrimitiveBytes in tools
	public static byte[] getBytesFast(String str)
	{
		final char buffer[] = new char[str.length()];
		final int length = str.length();
		str.getChars(0, length, buffer, 0);
		final byte b[] = new byte[length];
		for (int j = 0; j < length; j++)
			b[j] = (byte) buffer[j];
		return b;
	}
	 
}

	 
