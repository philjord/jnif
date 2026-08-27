package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun' version='0' signature='0x1da85e02' parent='hkcdStaticMeshTreeBase'>
	<enums>
		<enum name='TriangleMaterial' flags='00000000'>
			<enumitem name='TM_SET_FROM_TRIANGLE_DATA_TYPE' value='0'/>
			<enumitem name='TM_SET_FROM_PRIMITIVE_KEY' value='1'/>
		</enum>
	</enums>
	<members>
		<member name='packedVertices' type='hkArray&lt;hkUint32&gt;' offset='112' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sharedVertices' type='hkArray&lt;hkUint64&gt;' offset='128' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT64' arrsize='0' flags='FLAGS_NONE'/>
		<member name='primitiveDataRuns' type='hkArray&lt;struct hknpCompressedMeshShapeTreeDataRun&gt;' ctype='hknpCompressedMeshShapeTreeDataRun' offset='144' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun
		extends hkcdStaticMeshTreeBase {
	public int[]								packedVertices;
	public long[]								sharedVertices;
	public hknpCompressedMeshShapeTreeDataRun[]	primitiveDataRuns;

	public hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun(	HKXReaderConnector connector,
																																ByteBuffer stream,
																																int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 112));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 112;
			packedVertices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				packedVertices[i] = stream.getInt((int)arrValue.to + (i * 4));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 128));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 128;
			sharedVertices = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sharedVertices[i] = stream.getLong((int)arrValue.to + (i * 8));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 144));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 144;
			primitiveDataRuns = new hknpCompressedMeshShapeTreeDataRun[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primitiveDataRuns[i] = new hknpCompressedMeshShapeTreeDataRun(connector, stream,
						(int)arrValue.to + (i * hknpCompressedMeshShapeTreeDataRun.size));
			}
		}
	}

	/**
	Outline for Havok_TagType hkcdStaticMeshTree
	Havok_TagMember packedVertices of type hkArray
	Havok_TagMember sharedVertices of type hkArray
	Havok_TagMember primitiveDataRuns of type hkArray
	*/

	public hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun(Havok_TagObject item) {
		super(item);
		//item.outputOutline();

		int memberIdx = 9;
		//shape = TAG0Reader.getRefPtr(item.listObjectClass.get(memberIdx++));		
		
		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		packedVertices = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			packedVertices[i] = value.listObjectArray.get(i).i_value;
		}	
		 		 
		value = item.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		sharedVertices = new long[arrSize];
		for (int i = 0; i < arrSize; i++) {
			sharedVertices[i] = value.listObjectArray.get(i).l_value;
		} 

		value = item.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		primitiveDataRuns = new hknpCompressedMeshShapeTreeDataRun[arrSize];
		for (int i = 0; i < arrSize; i++) {
			primitiveDataRuns[i] = new hknpCompressedMeshShapeTreeDataRun(value.listObjectArray.get(i));
		} 

	}
}