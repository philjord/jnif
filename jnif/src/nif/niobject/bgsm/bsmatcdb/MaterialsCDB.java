package nif.niobject.bgsm.bsmatcdb;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.bgsm.BSMaterial;

/**
 * 
 * More on the material database, it consists of two objects describing the list of materials, material objects and
 * components, followed by all components.
 * 
 * The first object in the CDB is of type BSMaterial::Internal::CompiledDB:
 * 
 * BSMaterial::Internal::CompiledDB { 
 * String BuildVersion 
 * Map HashMap 
 * List Collisions 
 * List Circular }
 * 
 * BuildVersion is the version of the game (currently "1.8.86.0"), and HashMap is a map from BSResource::ID to uint64_t.
 * It maps material paths (represented as CRC32 hashes of the base name without the .mat extension and the directory
 * name, and the extension that is always "mat\0") to an unknown 64-bit hash. Note that while the definition of
 * BSResource::ID has the fields in Dir, File, Ext order, File is actually the first in the data.
 * 
 * 
 * The second object is BSComponentDB2::DBFileIndex:
 * 
 * BSComponentDB2::DBFileIndex { 
 * Map ComponentTypes 
 * List Objects 
 * List Components 
 * List Edges 
 * Bool Optimized }
 * 
 * 'ComponentTypes' maps 16-bit component type IDs to string format class names. 'Objects' is a list of all material
 * objects, in this format:
 * 
 * BSComponentDB2::DBFileIndex::ObjectInfo { 
 * BSResource::ID PersistentID 
 * BSComponentDB2::ID DBID 
 * BSComponentDB2::ID Parent 
 * Bool HasData }
 * 
 * PersistentID is similar to the keys used in HashMap above, and contains the same information for the externally
 * visible layered material (.mat) objects. DBID is the internal 32-bit ID of the object (it cannot be 0), while Parent
 * is used as the base object to construct this object from. HasData is true for all except 6 "root" objects from which
 * all others are derived, and only for those, the Parent is 0. These 6 objects are for the 6 material object types,
 * denoted by the base names "layeredmaterials", "blenders", "layers", "materials", "texturesets" and "uvstreams".
 * 
 * 'Components' links material components to material objects:
 * 
 * BSComponentDB2::DBFileIndex::ComponentInfo { 
 * BSComponentDB2::ID ObjectID 
 * UInt16 Index 
 * UInt16 Type }
 * 
 * ObjectID is one of the DBID values from the object list, Index is a component slot for component types of which there
 * can be more than one for a single object (e.g. a TextureSet object may have texture files associated with it at Index
 * = 0 to 20), otherwise it is 0, and Type is one of the 16-bit component type IDs previously defined in ComponentTypes.
 * 
 * Finally, 'Edges' describes how the material objects are organized in a tree structure:
 * 
 * BSComponentDB2::DBFileIndex::EdgeInfo { 
 * BSComponentDB2::ID SourceID 
 * BSComponentDB2::ID TargetID 
 * UInt16 Index 
 * UInt16 Type }
 * 
 * Index seems to be always 0, and Type is always the ID of BSComponentDB2::OuterEdge. This defines TargetID as
 * logically the parent of SourceID.
 * 
 * After BSMaterial::Internal::CompiledDB and BSComponentDB2::DBFileIndex, all material components are stored as OBJT
 * and DIFF chunks, the total number and order of these is exactly the same as in 'Components' above. All components of
 * derived objects are always stored after all components of their base object (the Parent in ObjectInfo), so they can
 * be copy constructed using the data that has already been read.
 * 
 * //https://forums.nexusmods.com/topic/13361451-starfields-cdb-material-database/
 * //https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/bsmatcdb.cpp
 * 
 */
public class MaterialsCDB extends REFLArchive {
	public MaterialsCDB(ByteBuffer in) {
		super(in);
	}

	public BSMaterial getMaterialFileCDB(int hash) throws IOException {
		return null;

		//TODO: so I'm here, and what I need to have done above is get myself into a 
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

	/*BSResource::ID {
		uint Dir
		uint File
		uint Ext
	}*/
	public static class BSResource_ID {

	}

	/*BSComponentDB2::ID {
		uint Value
	}*/
	public static class BSComponentDB2_ID {

	}

	/*
	BSMaterial::Internal::CompiledDB {
		String BuildVersion
		Map HashMap
		List Collisions
		List Circular
	}*/
	public static class CompiledDB {

	}

	/*BSComponentDB2::DBFileIndex {
		Map ComponentTypes
		List Objects
		List Components
		List Edges
		bool Optimized
	}*/
	public static class DBFileIndex {

	}

}
