package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hkRefCountedProperties' version='1' signature='0x7c574867'>
	<enums>
		<enum name='ReferenceCountHandling' flags='00000000'>
			<enumitem name='REFERENCE_COUNT_INCREMENT' value='0'/>
			<enumitem name='REFERENCE_COUNT_IGNORE' value='1'/>
		</enum>
	</enums>
	<members>
		<member name='entries' type='hkArray&lt;struct hkRefCountedPropertiesEntry&gt;' ctype='hkRefCountedPropertiesEntry' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/


public class hkRefCountedProperties  extends hkReferencedObject {
	
	public hkRefCountedProperties.Entry[] entries;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		
		//<member name='entries' type='hkArray&lt;struct hkRefCountedPropertiesEntry&gt;' ctype='hkRefCountedPropertiesEntry' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			entries = new hkRefCountedProperties.Entry[arrSize];
			for (int i = 0; i < arrSize; i++) {
				entries[i] = new hkRefCountedProperties.Entry(connector, stream, (int)arrValue.to + (i*hkRefCountedProperties.Entry.size));
			}
		}
		
		return true;
	}
	
	/**
	 Outline for Havok_TagType hkRefCountedProperties
	Havok_TagMember entries of type hkArray
	 */
	
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);
		Havok_TagObject value = value0.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		entries = new hkRefCountedProperties.Entry[arrSize];
		for (int i = 0; i < arrSize; i++) {
			entries[i] = new hkRefCountedProperties.Entry(value.listObjectArray.get(i));
		}

		return memberIdx;
	}
	
	
	
	
	
	/**
	<struct name='hkRefCountedPropertiesEntry' version='0' signature='0x28ef93ed'>
		<members>
			<member name='object' type='struct hkReferencedObject*' ctype='hkReferencedObject' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
			<member name='key' type='hkUint16' offset='8' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
			<member name='flags' type='hkUint16' offset='10' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		</members>
	</struct>
	*/
	public static class Entry {

		public static final int	size	= 10 + 2;
		public long				object;
		public int				key;
		public int				flags;

		public Entry(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
				throws IOException, InvalidPositionException {
			object = HKXReader.getPointer(connector, classOffset + 0);
			key = Short.toUnsignedInt(stream.getShort(classOffset + 8));
			flags = Short.toUnsignedInt(stream.getShort(classOffset + 10));
		}

		/**
		 Outline for Havok_TagObject of type hkRefCountedProperties::Entry
		Havok_TagType None
		Havok_TagType hkRefCountedProperties::Entry
		Havok_TagMember object of type hkRefPtr
		Havok_TagMember key of type hkUint16
		Havok_TagMember flags of type hkUint16 	 
		 */
		public Entry(Havok_TagObject item) {
			int memberIdx = 0;
			//item.outputOutline();
			object = item.listObjectClass.get(memberIdx++).i_value;
			key = item.listObjectClass.get(memberIdx++).i_value;
			flags = item.listObjectClass.get(memberIdx++).i_value;
		}
	}
}