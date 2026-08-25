package nif.niobject.hkx.reader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import nif.niobject.hkx.hkBaseObject;
// https://github.com/blueskythlikesclouds/TagTools
// https://github.com/Olganix/LibXenoverse2/blob/master/LibXenoverse/LibXenoverse/Havok.h
// https://github.com/blueskythlikesclouds/TagTools/blob/70d05f892fe84083dc4cc360f5ca37bacb612327/TagTools.py#L1428

public class TAG0Reader {

	public static boolean		sop						= false;

	private static final String	Havok_TAG0_SIGNATURE	= "TAG0";
	private static final String	Havok_SDKV_SIGNATURE	= "SDKV";
	private static final String	Havok_v2015_SIGNATURE	= "2015";
	private static final String	Havok_major01_SIGNATURE	= "01";
	private static final String	Havok_minor00_SIGNATURE	= "00";
	private static final String	Havok_DATA_SIGNATURE	= "DATA";
	private static final String	Havok_TYPE_SIGNATURE	= "TYPE";
	private static final String	Havok_TSTR_SIGNATURE	= "TSTR";
	private static final String	Havok_TNAM_SIGNATURE	= "TNAM";
	private static final String	Havok_FSTR_SIGNATURE	= "FSTR";
	private static final String	Havok_TBOD_SIGNATURE	= "TBOD";
	private static final String	Havok_TBDY_SIGNATURE	= "TBDY";
	private static final String	Havok_TPAD_SIGNATURE	= "TPAD";
	private static final String	Havok_INDX_SIGNATURE	= "INDX";
	private static final String	Havok_ITEM_SIGNATURE	= "ITEM";
	private static final String	Havok_TPTR_SIGNATURE	= "TPTR";	// not decoded
	private static final String	Havok_THSH_SIGNATURE	= "THSH";	// not decoded
	private static final String	Havok_PTCH_SIGNATURE	= "PTCH";	// not decoded

	public static class Havok_PartHeader {
		public int		size;		// 0	// not considered the 2 left bits. Size considered the header included.
		public int		part_id;
		public String	signature;

		Havok_PartHeader(ByteBuffer buf, int offset, boolean b) {
			int val = buf.getInt(offset + 0);
			part_id = (val & 0xC0000000) >> 30;
			size = val & 0x3FFFFFFF;
			byte[] sigb = new byte[4]; // 4
			buf.position(offset + 4).get(sigb);
			signature = new String(sigb);
		}

		@Override
		public String toString() {
			return "Havok_PartHeader " + signature + " size " + size;
		}
	}

	//static_assert(sizeof(Havok_PartHeader) == 0x8, "Incorrect structure size.");
	public static int sizeof_Havok_PartHeader = 0x8;

	public static class Havok_Version {
		public String	year;	// 0
		public String	major;	// 4
		public String	minor;	// 6

		Havok_Version(ByteBuffer buf, int offset, boolean b) {
			byte[] yearb = new byte[4]; // 0
			byte[] majorb = new byte[2]; // 4
			byte[] minorb = new byte[2]; // 6
			buf.position(offset + 0).get(yearb);
			buf.position(offset + 4).get(majorb);
			buf.position(offset + 6).get(minorb);
			year = new String(yearb);
			major = new String(majorb);
			minor = new String(minorb);
		}
	}

	//static_assert(sizeof(Havok_Version) == 0x8, "Incorrect structure size.");
	public static int sizeof_Havok_Version = 0x8;

	public static class Havok_TagMember {

		public String			name;
		public int				flags;
		public int				byteOffset;
		public Havok_TagType	type;

		//Havok_TagMember(String name = "v", size_t flags = 0, size_t byteOffset = 0, Havok_TagType type = 0) {
		Havok_TagMember(String name, int flags, int byteOffset, Havok_TagType type) {
			this.name = name;
			this.flags = flags;
			this.byteOffset = byteOffset;
			this.type = type;
		}

		public Havok_TagMember(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Havok_TagMember " + name;
		}
	}

	public static class Havok_TagTemplate {

		public String			name;
		public int				value;
		public Havok_TagType	value_ptr;	//if isType, target a Havok_TagType

		//Havok_TagTemplate(string name = "v", size_t value = 0) { 
		Havok_TagTemplate(String name, int value) {
			this.name = name;
			this.value = value;
			value_ptr = null;
		}

		boolean isValue() {
			return (name.substring(0, 1).equals("v"));
		}

		boolean isType() {
			return (name.substring(0, 1).equals("t"));
		}

		@Override
		public String toString() {
			return "Havok_TagTemplate " + name;
		}
	}

	public static class Havok_TagInterface {

		public Havok_TagType	type;
		public int				value;

		//Havok_TagInterface(Havok_TagType* type = 0, size_t value = 0) { 
		Havok_TagInterface(Havok_TagType type, int value) {
			this.type = type;
			this.value = value;
		}
	}

	public static class Havok_TagType {

		public int								id;
		public String							name;

		public Havok_TagType					parent;
		public Havok_TagType					pointer;

		public int								version;
		public int								flags;
		public int								subTypeFlags;

		public int								byteSize;
		public int								alignment;
		public int								abstractValue;

		public ArrayList<Havok_TagTemplate>		listTemplate	= new ArrayList<Havok_TagTemplate>();
		public ArrayList<Havok_TagMember>		members			= new ArrayList<Havok_TagMember>();
		public ArrayList<Havok_TagInterface>	interfaces		= new ArrayList<Havok_TagInterface>();

		//Havok_TagType(size_t id, string name = "", Havok_TagType* parent = 0, size_t flags = 0, size_t subTypeFlags = 0, Havok_TagType* pointer = 0, size_t version = 0, size_t byteSize = 0, size_t alignment = 0, size_t abstractValue = 0) 
		Havok_TagType(	int id, String name, Havok_TagType parent, int flags, int subTypeFlags, Havok_TagType pointer,
						int version, int byteSize, int alignment, int abstractValue) {
			this.id = id;
			this.name = name;
			this.parent = parent;
			this.flags = flags;
			this.subTypeFlags = subTypeFlags;
			this.pointer = pointer;
			this.version = version;
			this.byteSize = byteSize;
			this.alignment = alignment;
			this.abstractValue = abstractValue;
		}

		public Havok_TagType(int id, String name) {
			this.id = id;
			this.name = name;
		}

		Havok_TagType superType() {
			return (((flags & TagFlag.TF_SubType._val) != 0) ? this : parent.superType());
		}

		int subType() {
			return (subTypeFlags & TagSubType.TST_TypeMask._val);
		}

		ArrayList<Havok_TagMember> allMembers() {
			ArrayList<Havok_TagMember> ret;
			if (parent != null)
				ret = parent.allMembers();
			else
				ret = new ArrayList<Havok_TagMember>();
			int nbMembers = members.size();
			for (int i = 0; i < nbMembers; i++)
				ret.add(members.get(i));
			return ret;
		}

		int tupleSize() {
			return (subTypeFlags >> 8);
		}

		/**
		 * Will output the members names and type up the hierarchy
		 */
		public void outputOutline() {
			if (parent != null)
				parent.outputOutline();

			System.out.println("" + this);
			int nbMembers = members.size();
			for (int i = 0; i < nbMembers; i++) {
				Havok_TagMember tag = members.get(i);
				System.out.println("" + tag + " of type " + tag.type.name);
			}
		}

		@Override
		public String toString() {
			return "Havok_TagType " + name;
		}
	}

	public static class Havok_TagItem {

		public Havok_TagType				type;
		public int							offset;
		public int							count;
		public boolean						isPtr;
		public ArrayList<Havok_TagObject>	value	= new ArrayList<Havok_TagObject>();	//TODO: how about when not filled?

		public int							xmlTaglevel;

		//Havok_TagItem(size_t xmlTaglevel = 0, Havok_TagType* type = 0, size_t offset = 0, size_t count = 0, bool isPtr = false) 
		Havok_TagItem(int xmlTaglevel, Havok_TagType type, int offset, int count, boolean isPtr) {
			this.xmlTaglevel = xmlTaglevel;
			this.type = type;
			this.offset = offset;
			this.count = count;
			this.isPtr = isPtr;
		}

		public Havok_TagItem() {

		}

		static boolean offsetOrder(Havok_TagItem a, Havok_TagItem b) {
			return (a.offset < b.offset);
		}

		static boolean levelOrder(Havok_TagItem a, Havok_TagItem b) {
			return (a.xmlTaglevel < b.xmlTaglevel);
		}

		void remove(Havok_TagObject obj) {
			value.remove(obj);
		}

		/**
		 * Will output the members names and type up the hierarchy
		 */
		public void outputOutline() {
			System.out.println("Outline for " + this);
			type.outputOutline();
		}

		@Override
		public String toString() {
			return "Havok_TagItem of type " + type.name;
		}
	}

	public static class Havok_TagObject {

		public boolean						b_value;
		public String						s_value;
		public byte							byte_value;
		public int							i_value;
		public long							l_value;
		public float						f_value;

		public Havok_TagObject				objectPointer;
		public ArrayList<Havok_TagObject>	listObjectString;
		public ArrayList<Havok_TagObject>	listObjectClass	= new ArrayList<Havok_TagObject>();
		public ArrayList<Havok_TagObject>	listObjectArray;
		public ArrayList<Havok_TagObject>	listObjectTuple	= new ArrayList<Havok_TagObject>();

		public Havok_TagType				type;
		public Havok_TagItem				attachement;										//for rebuild

		//Havok_TagObject(Havok_TagType* type = 0) {
		Havok_TagObject(Havok_TagType type) {
			this.type = type;
			b_value = false;
			s_value = "";
			byte_value = 0;
			i_value = 0;
			l_value = 0;
			f_value = 0.0f;
			attachement = null;
			objectPointer = null;
		}

		public Havok_TagObject() {

		}

		/**
		 * Will output the members names and type up the hierarchy
		 */
		public void outputOutline() {
			System.out.println("Outline for " + this);
			type.outputOutline();
		}

		@Override
		public String toString() {
			return "Havok_TagObject of type " + type.name;
		}
	}

	enum TagSubType {
		TST_Void(0x0), //
		TST_Invalid(0x1), //
		TST_Bool(0x2), //
		TST_String(0x3), //
		TST_Int(0x4), //
		TST_Float(0x5), //
		TST_Pointer(0x6), //
		TST_Class(0x7), //
		TST_Array(0x8), //
		TST_Tuple(0x28), //
		TST_TypeMask(0xff), //
		TST_IsSigned(0x200), //
		TST_Float32(0x1746), //
		TST_Int8(0x2000), //
		TST_Int16(0x4000), //
		TST_Int32(0x8000), //
		TST_Int64(0x10000);

		private int _val;

		TagSubType(int val) {
			_val = val;
		}

		public int getValue() {
			return _val;
		}

	};

	enum TagFlag {
		TF_SubType(0x1), TF_Pointer(0x2), //
		TF_Version(0x4), //
		TF_ByteSize(0x8), TF_AbstractValue(0x10), //
		TF_Members(0x20), //
		TF_Interfaces(0x40), TF_Unknown(0x80);

		private int _val;

		TagFlag(int val) {
			_val = val;
		}

		public int getValue() {
			return _val;
		}

	};

	public Havok_PartHeader					version_hdr;
	public Havok_Version					version_info;
	public Havok_TagObject					rootObject	= null;
	public ArrayList<Havok_TagType>			listType	= new ArrayList<Havok_TagType>();
	public ArrayList<Havok_TagItem>			listItem	= new ArrayList<Havok_TagItem>();		//list of item is not really necessary (because we could find with analyze object hierarchy), but it's for making binary in the same order than original. Todo find order from object hierrachy and remove it
	public HashMap<Integer, Havok_TagItem>	hashItem	= new HashMap<Integer, Havok_TagItem>();
	public HKXContents						hkxContents;

	//https://reshax.com/topic/198-havok-middleware/  
	public TAG0Reader(ByteBuffer buf) {
		load(buf);

		// Create the return object
		HeaderData headerData = new HeaderData();
		headerData.is64bit = true;
		headerData.versionName = version_info.toString();
		hkxContents = new HKXContents(headerData);

		for (int i = 0; i < listItem.size(); i++) {
			Havok_TagItem item = listItem.get(i);

			// skip things that aren't pointed to by other things
			if (((item.type == null) || (item.type.name.equals("None")) || !item.isPtr))
				continue;

			String className = item.type.name;

			// the following skyrim hkp objects don't have xml to decode, they only appear in skeleton.hkx
			// I only need the bones mappings from a skeleton.hkx so I'm going to hope to ignore them
			// these look like the bhk versions found in nif files

			//hkpRigidBody hkpPhysicsSystem hkpPhysicsData hkpShapeInfo hkpCapsuleShape
			if (className.equals("hkpRigidBody")	|| className.equals("hkpPhysicsSystem")
				|| className.equals("hkpPhysicsData") || className.equals("hkpShapeInfo")
				|| className.equals("hkpCapsuleShape")) {
				// no notice cos it's too many filling up me damn console with nonsense
				break;
			}

			//System.out.println("TAG0Reader: " + className);

			hkBaseObject obj = HKXReader.constructHKXObject(className, true);

			// Check for an unknown block type
			if (obj == null) {
				System.out.println("Unknown object type encountered during file read:  " + className);
				break;
			}

			int fieldsRead = obj.readFromTAG0(item);

			//-1 means bad news
			if (fieldsRead < 0) {
				new Throwable("bum read").printStackTrace();
			}

			//System.out.println("hkxContents.add(item.offset, obj); " + item.offset + " " + obj);
			hkxContents.add(item.offset, obj);
		}
	}

	public boolean load(ByteBuffer buf) {

		String version; //only "2015.01.00" accepted

		buf.position(0);
		//TagFileType.Object		

		buf.order(ByteOrder.BIG_ENDIAN);// holy shit balls, but hdr confirms it
		int size = buf.capacity();

		int offset = 0;

		Havok_PartHeader hdr = new Havok_PartHeader(buf, offset, true); // so grab the bits of the hdr out of the buf
		if (size < sizeof_Havok_PartHeader || (!hdr.signature.equals(Havok_TAG0_SIGNATURE)) || (size < hdr.size)) {
			System.out.println("error: havok's version request : " + Havok_TAG0_SIGNATURE);
			return false;
		}
		offset += sizeof_Havok_PartHeader;

		version_hdr = new Havok_PartHeader(buf, offset, true);
		if (!version_hdr.signature.equals(Havok_SDKV_SIGNATURE)) {
			System.out.println("error: havok's version request : " + Havok_SDKV_SIGNATURE);
			return false;
		}
		offset += sizeof_Havok_PartHeader;

		version_info = new Havok_Version(buf, offset, true);
		if ((!version_info.year.equals(Havok_v2015_SIGNATURE))	|| (!version_info.major.equals(Havok_major01_SIGNATURE))
			|| (!version_info.minor.equals(Havok_minor00_SIGNATURE))) {
			System.out.println("error: havok's version request : "	+ Havok_v2015_SIGNATURE + "."
								+ Havok_major01_SIGNATURE + "." + Havok_minor00_SIGNATURE);
			return false;
		}
		offset += sizeof_Havok_Version;

		int offsetData = -1;
		int sizeOfData = 0;
		ArrayList<String> listTSTR = new ArrayList<String>();
		ArrayList<String> listFSTR = new ArrayList<String>();

		while (offset < hdr.size) {
			Havok_PartHeader part_hdr = new Havok_PartHeader(buf, offset, true);
			offset += sizeof_Havok_PartHeader;

			if (part_hdr.signature.equals(Havok_DATA_SIGNATURE)) {
				// will be used after getting the item list
				offsetData = offset;
				sizeOfData = part_hdr.size - sizeof_Havok_PartHeader;
				offset += part_hdr.size - sizeof_Havok_PartHeader;

			} else if (part_hdr.signature.equals(Havok_TYPE_SIGNATURE)) {

				int startoffset_Type = offset - sizeof_Havok_PartHeader;

				while (offset < startoffset_Type + part_hdr.size) {
					Havok_PartHeader type_subpart_hdr = new Havok_PartHeader(buf, offset, true);
					offset += sizeof_Havok_PartHeader;

					if (type_subpart_hdr.signature.equals(Havok_TSTR_SIGNATURE)) {
						int offset_tmp = 0;
						int inc = 0;
						while (offset_tmp < type_subpart_hdr.size - sizeof_Havok_PartHeader) {
							// this thing below pulls a string out with a nul termination that's all							
							int l = 0;
							while (offset_tmp + l < type_subpart_hdr.size - sizeof_Havok_PartHeader) {
								if (buf.get(offset + offset_tmp + l) == 0)
									break;
								else
									l++;
							}
							byte[] char_ptr = new byte[l];
							buf.position(offset + offset_tmp).get(char_ptr);
							String str = new String(char_ptr);

							listTSTR.add(str);
							offset_tmp += str.length() + 1;
						}
						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;

						//debug
						//for(int i=0;i< listTSTR.size();i++)  System.out.println("" + i + " "+listTSTR.get(i));	

					} else if (type_subpart_hdr.signature.equals(Havok_TNAM_SIGNATURE)) {

						int[] nbBytes = new int[] {0};
						int nbTypes = readPacked(buf, offset, size - offset, nbBytes);

						for (int i = 0; i < nbTypes; i++)
							listType.add(new Havok_TagType(i, "None"));

						int offset_tmp = offset + nbBytes[0];
						for (int i = 1; i < nbTypes; i++) {

							Havok_TagType tagType = listType.get(i);

							int idx = readPacked(buf, offset_tmp, size - offset_tmp, nbBytes);
							tagType.name = listTSTR.get(idx);

							//System.out.println("i " + i + " " + tagType.name);

							offset_tmp += nbBytes[0];

							int nbNextValues = readPacked(buf, offset_tmp, size - offset_tmp, nbBytes);
							offset_tmp += nbBytes[0];

							for (int j = 0; j < nbNextValues; j++) {

								int index = readPacked(buf, offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								String str = listTSTR.get(index);

								//if( str.startsWith("v"))
								//System.out.println("template " + str   + " i " + i + " j " + j);								

								if (str.equals("vINVALID_VALUE")) {
									// lets pull some rubbish values off								 

									//ArchiveFile:SeventySix - Meshes.ba2/meshes/ammo/10mm/10mmammo.nif
									//ArchiveFile:SeventySix - Meshes.ba2/meshes/architecture/airport/barricade/bosbarricadeaddon01.nif
									//both  requires 5 bytes pulled off
									offset_tmp += 5;

								} else {
									Havok_TagTemplate template_tmp = new Havok_TagTemplate(str,
											readPacked(buf, offset_tmp, size - offset_tmp, nbBytes));
									offset_tmp += nbBytes[0];

									if (template_tmp.isType()) {
										template_tmp.value_ptr = listType.get(template_tmp.value);
									}

									tagType.listTemplate.add(template_tmp);
								}
							}
						}

						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;

					} else if (type_subpart_hdr.signature.equals(Havok_FSTR_SIGNATURE)) {

						int offset_tmp = 0;
						int inc = 0;
						while (offset_tmp < type_subpart_hdr.size - sizeof_Havok_PartHeader) {
							// this thing below pulls a string out with a nul termination that's all
							int l = 0;
							while (offset_tmp + l < type_subpart_hdr.size - sizeof_Havok_PartHeader) {
								if (buf.get(offset + offset_tmp + l) == 0)
									break;
								else
									l++;
							}
							byte[] char_ptr = new byte[l];
							buf.position(offset + offset_tmp).get(char_ptr);
							String str = new String(char_ptr);
							listFSTR.add(str);
							offset_tmp += str.length() + 1;
						}

						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;

					} else if (type_subpart_hdr.signature.equals(Havok_TBOD_SIGNATURE)
								|| type_subpart_hdr.signature.equals(Havok_TBDY_SIGNATURE)) {

						int offset_tmp = 0;
						int[] nbBytes = new int[] {0};
						while (offset_tmp < type_subpart_hdr.size - sizeof_Havok_PartHeader) {
							int typeIndex = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
							offset_tmp += nbBytes[0];

							if (typeIndex == 0) //sequence of 0 padding
								continue;

							Havok_TagType tagType = listType.get(typeIndex);

							int typeIndexParent = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
							offset_tmp += nbBytes[0];
							tagType.parent = listType.get(typeIndexParent);

							int flags = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
							offset_tmp += nbBytes[0];
							tagType.flags = flags;

							if ((tagType.flags & TagFlag.TF_SubType._val) != 0) {
								int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.subTypeFlags = value;
							}

							if ((tagType.flags & TagFlag.TF_Pointer._val) != 0 && ((tagType.subTypeFlags & 0xF) >= 6)) {
								int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.pointer = listType.get(value);
							}

							if ((tagType.flags & TagFlag.TF_Version._val) != 0) {
								int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.version = value;
							}

							if ((tagType.flags & TagFlag.TF_ByteSize._val) != 0) {
								int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.byteSize = value;

								value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.alignment = value;
							}

							if ((tagType.flags & TagFlag.TF_AbstractValue._val) != 0) {
								int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];
								tagType.abstractValue = value;
							}

							if ((tagType.flags & TagFlag.TF_Members._val) != 0) {
								int nbMembers = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];

								for (int i = 0; i < nbMembers; i++) {
									int strIdex = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
									Havok_TagMember tagMember = new Havok_TagMember(listFSTR.get(strIdex));
									offset_tmp += nbBytes[0];

									tagMember.flags = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
									offset_tmp += nbBytes[0];

									tagMember.byteOffset = readPacked(buf, offset + offset_tmp, size - offset_tmp,
											nbBytes);
									offset_tmp += nbBytes[0];

									tagMember.type = listType
											.get(readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes));
									offset_tmp += nbBytes[0];

									tagType.members.add(tagMember);
								}
							}

							if ((tagType.flags & TagFlag.TF_Interfaces._val) != 0) {
								int nbTypeArray = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
								offset_tmp += nbBytes[0];

								for (int i = 0; i < nbTypeArray; i++) {
									int index_tmp = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
									offset_tmp += nbBytes[0];
									Havok_TagType type_temp = listType.get(index_tmp);

									int value = readPacked(buf, offset + offset_tmp, size - offset_tmp, nbBytes);
									offset_tmp += nbBytes[0];

									tagType.interfaces.add(new Havok_TagInterface(type_temp, value));
								}
							}

							if ((tagType.flags & TagFlag.TF_Unknown._val) != 0) {
								System.out.println("Error: in TBOD, Flag 0x80 exists, handle it!");
							}
						}

						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;

					} else if (type_subpart_hdr.signature.equals(Havok_TPAD_SIGNATURE)) {
						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;
					} else {

						if (sop) {
							System.out.println(
									"Warning : Type_subPartHeader signature unknow : "	+ type_subpart_hdr.signature
												+ " at " + offset + " with size of " + type_subpart_hdr.size);

							//https://reshax.com/topic/198-havok-middleware/ 
							//THSH: Class signatures 
							// I've seen 
							//TPTR at 3696 with size of 1176, but just 0 in every byte
							//THSH at 11444 with size of 192
							byte[] bytes = new byte[type_subpart_hdr.size - sizeof_Havok_PartHeader];
							buf.get(bytes);
							for (int i = 0; i < bytes.length; i++) {
								System.out.print("" + (bytes[i] & 0xff) + " ");
							}
							System.out.println("");
							System.out.println("" + new String(bytes));
						}

						offset += type_subpart_hdr.size - sizeof_Havok_PartHeader;
					}
				}

			} else if (part_hdr.signature.equals(Havok_INDX_SIGNATURE)) {

				int startIndexPart = offset - sizeof_Havok_PartHeader;

				while (offset < startIndexPart + part_hdr.size) {
					Havok_PartHeader indexation_subpart_hdr = new Havok_PartHeader(buf, offset, true);
					offset += sizeof_Havok_PartHeader;

					if (indexation_subpart_hdr.signature.equals(Havok_ITEM_SIGNATURE)) {
						int startoffset_itemList = offset - sizeof_Havok_PartHeader;
						int nbBytes = 0;
						buf.order(ByteOrder.LITTLE_ENDIAN);
						while (offset < startoffset_itemList + indexation_subpart_hdr.size) {
							int[] values = new int[3];

							values[0] = buf.getInt(offset + 0);
							values[1] = buf.getInt(offset + 4);
							values[2] = buf.getInt(offset + 8);
							offset += 3 * 4;

							Havok_TagItem item = new Havok_TagItem();
							listItem.add(item);

							int flag = values[0];
							item.type = listType.get(flag & 0xFFFFFF);
							item.isPtr = ((flag & 0x10000000) != 0);

							item.offset = offsetData + values[1];
							item.count = values[2];

							hashItem.put(item.offset, item);

							//System.out.println("Havok_TagItem with " + values[0] + " "+values[1] +" "+values[2]);
							//System.out.println("Havok_TagItem bin " + Integer.toBinaryString(values[0])
							//+ " "+Integer.toBinaryString(values[1]) +" "+Integer.toBinaryString(values[2]));
						}
						buf.order(ByteOrder.BIG_ENDIAN);

					} else {

						//I've seen
						//Warning : indexation PartHeader signature unknow : PTCH at 11924 with size of 228
						// looks like a lot of ints perhaps?
						//3 0 0 0, 1 0 0 0, 16 0 0 0, 5 0 0 0, 1 0 0 0, 48 0 0 0, 7 0 0 0, 1 0 0 0, 80 0 0 0, 21 0 0 0 
						//3 0 0 0, 128 1 0 0, 0 2 0 0, 144 2 0 0, 53 0 0 0, 1 0 0 0, 208 0 0 0, 74 0 0 0, 1 0 0 0, 184 1 0 0 81 0 0 0 1 0 0 0 120 2 0 0 89 0 0 0 1 0 0 0 16 3 0 0 92 0 0 0 2 0 0 0 8 2 0 0 32 3 0 0 99 0 0 0 1 0 0 0 88 3 0 0 105 0 0 0 1 0 0 0 48 3 0 0 106 0 0 0 1 0 0 0 64 3 0 0 113 0 0 0 1 0 0 0 240 2 0 0 114 0 0 0 1 0 0 0 0 3 0 0 128 0 0 0 1 0 0 0 176 2 0 0 140 0 0 0 2 0 0 0 176 3 0 0 16 4 0 0 143 0 0 0 1 0 0 0 8 14 0 0 

						//https://reshax.com/topic/198-havok-middleware/ PTCH: Index for pointer patches 

						if (sop) {
							System.out.println("Warning : indexation PartHeader signature unknow : "
												+ indexation_subpart_hdr.signature + " at " + offset + " with size of "
												+ indexation_subpart_hdr.size);

							byte[] bytes = new byte[indexation_subpart_hdr.size - sizeof_Havok_PartHeader];
							buf.get(bytes);
							for (int i = 0; i < bytes.length; i++) {
								System.out.print("" + (bytes[i] & 0xff) + " ");
							}
							System.out.println("");
							System.out.println("" + new String(bytes));
						}

						offset += indexation_subpart_hdr.size - sizeof_Havok_PartHeader;
					}
				}

			} else {
				System.out.println("Warning : PartHeader signature unknow : " + part_hdr.signature + " at " + offset);

				offset += part_hdr.size - sizeof_Havok_PartHeader;
			}
		}

		//TODO: I think this part csan be skipped and the same calls made within each actual class?
		//now we have the data and the informations about it.
		if ((offsetData != (4) - 1) && (listItem.size() != 0)) {
			int[] offset2 = new int[] {offsetData};

			int nbElements = 0;
			int nbItem = listItem.size();
			// note the break statement below
			for (int i = 0; i < nbItem; i++) {
				Havok_TagItem item = listItem.get(i);

				if (((item.type != null) && (item.type.name.equals("None"))) || (item.value.size() != 0))
					continue;

				Havok_TagType type = item.type;

				for (int j = 0; j < item.count; j++) {
					offset2[0] = item.offset + j * type.superType().byteSize;
					item.value.add(readObject(j, buf, size, type, offset2, listItem, listType, item));

					if (rootObject == null)
						rootObject = (Havok_TagObject)item.value.get(item.value.size() - 1);
				}

				break; //normally the first element is enough because of recursivity
			}
		}

		return (rootObject != null);

	}

	/*-------------------------------------------------------------------------------\
	|                             readObject					                     |
	\-------------------------------------------------------------------------------*/
	//Havok_TagObject* Havok::readObject(size_t index, const uint8_t *buf, size_t size, Havok_TagType* type, size_t &offset, std::vector<Havok_TagItem*> &listItem, std::vector<Havok_TagType*> &listType, Havok_TagItem* parentAttachement)
	private Havok_TagObject readObject(	int index, ByteBuffer buf, int size, Havok_TagType type, int[] offset,
										ArrayList<Havok_TagItem> listItem, ArrayList<Havok_TagType> listType,
										Havok_TagItem parentAttachement) {

		// notice - 0x20 assumes offsetData = 32
		if (sop)
			System.out.print("**** readObject(" + type.id + ", " + type.name + ") at " + (offset[0] - 0x20) + " ");

		Havok_TagType typeOrigin = type;
		type = type.superType();

		Havok_TagObject obj = new Havok_TagObject();
		obj.type = typeOrigin;
		obj.attachement = parentAttachement;

		int nbItem = listItem.size();
		for (int i = 0; i < nbItem; i++) {
			if (listItem.get(i).type.id == typeOrigin.id) {
				obj.attachement = listItem.get(i);
				break;
			}
		}

		String[] type_str = new String[] {""};
		if (type.subType() == TagSubType.TST_Bool._val) {
			if (sop)
				System.out.print("-> Bool at " + (offset[0] - 0x20));
			obj.b_value = (readFormat(buf, offset[0], size - offset[0], type.subTypeFlags, type_str) > 0);
			if (sop)
				System.out.print(" of value " + obj.b_value);
		} else if (type.subType() == TagSubType.TST_Int._val) {			
		/*	if ((
					//NOTICE all bytes this way, just design if needed
					//(type.subTypeFlags & TagSubType.TST_IsSigned._val) != 0 && 
					(type.subTypeFlags & TagSubType.TST_Int8._val) != 0)	) {
				//signed byte into byte holder (unsigned in the int holder)
				if (sop)
					System.out.print("-> UInt/Long at " + (offset[0] - 0x20));
				obj.byte_value = (byte)readFormat(buf, offset[0], size - offset[0], type.subTypeFlags, type_str);
				if (sop)
					System.out.print(" of value " + obj.byte_value);
			} else if (
				//	((type.subTypeFlags & TagSubType.TST_IsSigned._val) == 0
				//	&& (type.subTypeFlags & TagSubType.TST_Int32._val) != 0)		||
				//NOTICE using 2 variable is more difficult to debug, just de-sign when needed
				(type.subTypeFlags & TagSubType.TST_Int64._val) != 0) {
				//  long go into long holder
				if (sop)
					System.out.print("-> UInt/Long at " + (offset[0] - 0x20));
				obj.l_value = readFormat(buf, offset[0], size - offset[0], type.subTypeFlags, type_str);
				if (sop)
					System.out.print(" of value " + obj.l_value);
			} else {*/
				if (sop)
					System.out.print("-> Int at " + (offset[0] - 0x20));
				obj.i_value = (int)readFormat(buf, offset[0], size - offset[0], type.subTypeFlags, type_str);
				if (sop)
					System.out.print(" of value " + obj.i_value);
			//}		
		} else if (type.subType() == TagSubType.TST_Float._val) {
			if (sop)
				System.out.print("-> Float at " + (offset[0] - 0x20));
			obj.f_value = buf.getFloat(offset[0]);
			if (sop)
				System.out.print(" of value " + obj.f_value);
		} else if (type.subType() == TagSubType.TST_String._val) {
			if (sop)
				System.out.print("-> String at " + (offset[0] - 0x20));
			obj.listObjectString = readItemPtr(buf, size, offset, listItem, listType);
			obj.s_value = "";
			int nbObj = obj.listObjectString.size();
			for (int i = 0; i < nbObj; i++)
				obj.s_value += (char)(obj.listObjectString.get(i).i_value);
			if (sop)
				System.out.print(" of value " + obj.s_value);
		} else if (type.subType() == TagSubType.TST_Pointer._val) {
			if (sop)
				System.out.print("-> Pointer at " + (offset[0] - 0x20));
			ArrayList<Havok_TagObject> listObj = readItemPtr(buf, size, offset, listItem, listType);

			obj.objectPointer = null;
			if (listObj.size() == 1) {
				obj.objectPointer = listObj.get(0);
			} else {
				int nbObj = listObj.size();
				// this looks like throwing away the pointers to everythign in the arraylist, so just skip?
				//for (int i = 0; i < nbObj; i++)
				//	delete( listObj.get(i));//TODO: what?
			}
			if (sop)
				System.out.print(" Pointer of value " + obj.objectPointer);

		} else if (type.subType() == TagSubType.TST_Class._val) {
			ArrayList<Havok_TagMember> listMembers = type.allMembers();

			int nbMember = listMembers.size();
			int[] offset_tmp = new int[] {0};
			for (int i = 0; i < nbMember; i++) {
				offset_tmp[0] = offset[0] + listMembers.get(i).byteOffset;
				if (sop)
					System.out.println("-> Member " + listMembers.get(i).name + " at " + (offset_tmp[0] - 0x20));
				obj.listObjectClass.add(readObject(i, buf, size, (Havok_TagType)listMembers.get(i).type, offset_tmp,
						listItem, listType, obj.attachement));
			}
		} else if (type.subType() == TagSubType.TST_Array._val) {
			if (sop)
				System.out.println("-> Array at " + (offset[0] - 0x20));
			obj.listObjectArray = readItemPtr(buf, size, offset, listItem, listType);

		} else if (type.subType() == TagSubType.TST_Tuple._val) {
			if (sop)
				System.out.println("-> Tuple at " + (offset[0] - 0x20));

			int nbTuple = type.tupleSize();
			int[] offset_tmp = new int[] {0};
			for (int i = 0; i < nbTuple; i++) {//HERE I have superTpes of things like unsigned char etc, so my read shoul be more sensible				
				offset_tmp[0] = offset[0] + i * type.pointer.superType().byteSize;
				obj.listObjectTuple
						.add(readObject(i, buf, size, type.pointer, offset_tmp, listItem, listType, obj.attachement));
			}
		}

		offset[0] += type.byteSize;
		if (sop)
			System.out.println(" (" + type.id + ", " + type.name + ") offset + type->byteSize (" + type.byteSize
								+ ") = " + (offset[0] - 0x20) + " ****");
		return obj;
	}

	/*-------------------------------------------------------------------------------\
	|                             readFormat			                             |
	\-------------------------------------------------------------------------------*/
	//type_str = String[1]
	//	long long readFormat(const uint8_t *buf, size_t size, uint32_t flags, string &type_str, bool bigEndian = false, bool isSigned = false);
	long readFormat(ByteBuffer buf, int offset, int size, int flags, String[] type_str) {
		return readFormat(buf, offset, size, flags, type_str, false, false);
	}

	long readFormat(ByteBuffer buf, int offset, int size, int flags, String[] type_str, boolean bigEndian) {
		return readFormat(buf, offset, size, flags, type_str, bigEndian, false);
	}

	long readFormat(ByteBuffer buf, int offset, int size, int flags, String[] type_str, boolean bigEndian,
					boolean isSigned) {

		buf.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);

		if ((flags & TagSubType.TST_IsSigned._val) != 0)
			isSigned = true;

		if ((flags & TagSubType.TST_Int8._val) != 0) {
			type_str[0] = (isSigned ? "i" : "u") + ("8");
			return ((isSigned) ?  buf.get(offset + 0) :(buf.get(offset + 0) & 0xFF) );
		} else if ((flags & TagSubType.TST_Int16._val) != 0) {
			type_str[0] = (isSigned ? "i" : "u") + ("16");
			int value = (isSigned ? buf.getShort(offset + 0) : (buf.getShort(offset + 0) & 0xFF));
			//return (bigEndian) ? BE16(value) : value; 
			return value;
		} else if ((flags & TagSubType.TST_Int32._val) != 0) {
			type_str[0] = (isSigned ? "i" : "u") + ("32");
			//FIXME: this this reversed!!! I need a good long system, 
			//ArchiveFile:SeventySix - Meshes.ba2/meshes/architecture/airport/barricade/bosbarricadeaddon01.nif shows this issue
			long value = (isSigned ? (buf.getInt(offset + 0) & 0xFFL): buf.getInt(offset + 0) ); //TODO: unsigned is for someone else to do
			//return (bigEndian) ? BE32(value) : value;
			return value;
		} else if ((flags & TagSubType.TST_Int64._val) != 0) {
			type_str[0] = (isSigned ? "i" : "u") + ("64");
			//FIXME I need a long and a byte system for goodness sake!
			long value = (isSigned ? buf.getLong(offset + 0) : (buf.getLong(offset + 0) & 0xFF)); //TODO: unsigned is for someone else to do
			//return (bigEndian) ? BE64(value) : value;
			return value;
		}
		return 0;
	}

	/*-------------------------------------------------------------------------------\
	|                             readItemPtr					                     |
	\-------------------------------------------------------------------------------*/
	//std::vector<Havok_TagObject*> readItemPtr(const uint8_t *buf, size_t size, size_t &offset, std::vector<Havok_TagItem*> &listItem, std::vector<Havok_TagType*> &listType, bool indexInversed = false);
	ArrayList<Havok_TagObject> readItemPtr(	ByteBuffer buf, int size, int[] offset, ArrayList<Havok_TagItem> listItem,
											ArrayList<Havok_TagType> listType) {
		return readItemPtr(buf, size, offset, listItem, listType, false);
	}

	ArrayList<Havok_TagObject> readItemPtr(	ByteBuffer buf, int size, int[] offset, ArrayList<Havok_TagItem> listItem,
											ArrayList<Havok_TagType> listType, boolean indexInversed) {
		if (sop)
			System.out.println("---- readItemPtr() at " + (offset[0] - 0x20));

		String[] str = new String[1];
		int index = (int)readFormat(buf, offset[0], size, TagSubType.TST_Int32._val, str, indexInversed);
		if ((index <= 0) || (index >= listItem.size())) {
			if (index != 0) {
				System.out.println("Index out of bounds " + index + " at offset " + (offset[0] - 0x20));
				System.out.println(" " + Integer.toBinaryString(index));
			}
			ArrayList<Havok_TagObject> ret = new ArrayList<Havok_TagObject>();
			return ret;
		}

		Havok_TagItem item = listItem.get(index);
		if (item.value.size() == 0) {
			int[] offset_tmp = new int[] {0};
			for (int i = 0; i < item.count; i++) {
				offset_tmp[0] = item.offset + i * item.type.superType().byteSize;
				item.value.add(readObject(i, buf, size, item.type, offset_tmp, listItem, listType, item));
			}
		}

		if (sop)
			System.out.println("---- ");

		return item.value;
	}

	// buf at offset pulls a byte then that determines how many bytes in the value
	// and the count of read bytes is handed back in nbBytes
	// notice no negatives
	public int readPacked(ByteBuffer buf, int offset, int size, int[] nbBytes) {

		//apparently the 3 first left bits is for definied the size (in bytes) of the value:
		int value = (buf.get(offset + 0) & 0xff);
		nbBytes[0] = 1;

		if ((value & 0x80) == 0) //uint8
			return value;
		else if ((value & 0x40) == 0) {
			//uint16
			nbBytes[0] = 2;
			return (((value << 8) | (buf.get(offset + 1) & 0xff)) & 0x3fff);
		} else if ((value & 0x20) == 0) {
			//uint24
			nbBytes[0] = 3;//TODO: are these values any good?
			return (((value << 16) | ((buf.get(offset + 1) & 0xff) << 8) | (buf.get(offset + 2) & 0xff)) & 0x1fffff);
		} else {
			//uint32
			nbBytes[0] = 4;
			return (((value << 24)	| ((buf.get(offset + 1) & 0xff) << 16) | ((buf.get(offset + 2) & 0xff) << 8)
						| ((buf.get(offset + 3) & 0xff)))
					& 0x1fffffff);

		}
	}

	public static long getRefPtr(Havok_TagObject obj) {
		if (obj.objectPointer == null)
			return -1;
		else
			return obj.objectPointer.attachement.offset;

	}
}
