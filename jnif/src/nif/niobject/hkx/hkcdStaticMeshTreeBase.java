package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticMeshTreeBase' version='0' signature='0xf885dcd0' parent='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5'>
	<enums>
		<enum name='CompressionMode' flags='00000000'>
			<enumitem name='CM_GLOBAL' value='0'/>
			<enumitem name='CM_LOCAL_4' value='1'/>
			<enumitem name='CM_LOCAL_2' value='2'/>
			<enumitem name='CM_AUTO' value='3'/>
		</enum>
	</enums>
	<members>
		<member name='numPrimitiveKeys' type='hkInt32' offset='48' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bitsPerKey' type='hkInt32' offset='52' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxKeyValue' type='hkUint32' offset='56' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sections' type='hkArray&lt;struct hkcdStaticMeshTreeBaseSection&gt;' ctype='hkcdStaticMeshTreeBaseSection' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='primitives' type='hkArray&lt;struct hkcdStaticMeshTreeBasePrimitive&gt;' ctype='hkcdStaticMeshTreeBasePrimitive' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sharedVerticesIndex' type='hkArray&lt;hkUint16&gt;' offset='96' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkcdStaticMeshTreeBase extends hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5 {
	public int									numPrimitiveKeys;
	public int									bitsPerKey;
	public int									maxKeyValue;
	public hkcdStaticMeshTreeBaseSection[]		sections;
	public hkcdStaticMeshTreeBasePrimitive[]	primitives;
	public int[]								sharedVerticesIndex;

	public hkcdStaticMeshTreeBase(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		numPrimitiveKeys = stream.getInt(classOffset + 48);
		bitsPerKey = stream.getInt(classOffset + 52);
		maxKeyValue = stream.getInt(classOffset + 56);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 64;
			sections = new hkcdStaticMeshTreeBaseSection[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sections[i] = new hkcdStaticMeshTreeBaseSection(connector, stream,
						(int)arrValue.to + (i * hkcdStaticMeshTreeBaseSection.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 80));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 80;
			primitives = new hkcdStaticMeshTreeBasePrimitive[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primitives[i] = new hkcdStaticMeshTreeBasePrimitive(connector, stream,
						(int)arrValue.to + (i * hkcdStaticMeshTreeBasePrimitive.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 96));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 96;
			sharedVerticesIndex = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sharedVerticesIndex[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
			}
		}
	}

	/**
	Outline for Havok_TagType hkcdStaticMeshTreeBase
	Havok_TagMember numPrimitiveKeys of type int
	Havok_TagMember bitsPerKey of type int
	Havok_TagMember maxKeyValue of type hkUint32
	Havok_TagMember primitiveStoresIsFlatConvex of type hkUint8
	Havok_TagMember sections of type hkArray
	Havok_TagMember primitives of type hkArray
	Havok_TagMember sharedVerticesIndex of type hkArray
	
	*/
	int primitiveStoresIsFlatConvex;
	public hkcdStaticMeshTreeBase(Havok_TagObject item) {
		super(item);
		//item.outputOutline();

		int memberIdx = 2;		
		
		numPrimitiveKeys = item.listObjectClass.get(memberIdx++).i_value;
		bitsPerKey = item.listObjectClass.get(memberIdx++).i_value;
		maxKeyValue = item.listObjectClass.get(memberIdx++).i_value;		
		primitiveStoresIsFlatConvex = item.listObjectClass.get(memberIdx++).i_value;

		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		sections = new hkcdStaticMeshTreeBaseSection[arrSize];
		for (int i = 0; i < arrSize; i++) {
			sections[i] = new hkcdStaticMeshTreeBaseSection(value.listObjectArray.get(i));
		}
		
		value = item.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		primitives = new hkcdStaticMeshTreeBasePrimitive[arrSize];
		for (int i = 0; i < arrSize; i++) {
			primitives[i] = new hkcdStaticMeshTreeBasePrimitive(value.listObjectArray.get(i));
		} 

		value = item.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		sharedVerticesIndex = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			sharedVerticesIndex[i] = value.listObjectArray.get(i).i_value;
		}		
	}
}