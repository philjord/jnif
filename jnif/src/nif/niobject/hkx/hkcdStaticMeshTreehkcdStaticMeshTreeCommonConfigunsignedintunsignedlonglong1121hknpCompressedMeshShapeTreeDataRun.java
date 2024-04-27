package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

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
</struct>*/

public class hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun extends hkcdStaticMeshTreeBase {
	public int[] packedVertices;
	public long[] sharedVertices;
	public hknpCompressedMeshShapeTreeDataRun[] primitiveDataRuns;
	
	public hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);
				
		//<member name='packedVertices' type='hkArray&lt;hkUint32&gt;' offset='112' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 112);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 112;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			packedVertices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				packedVertices[i] = s2.getInt(i*4);
			}
		}
		//<member name='sharedVertices' type='hkArray&lt;hkUint64&gt;' offset='128' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT64' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 128);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 128;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			sharedVertices = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sharedVertices[i] = s2.getLong(i*8);
			}
		}
		//<member name='primitiveDataRuns' type='hkArray&lt;struct hknpCompressedMeshShapeTreeDataRun&gt;' ctype='hknpCompressedMeshShapeTreeDataRun' offset='144' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 144);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 144;
			primitiveDataRuns = new hknpCompressedMeshShapeTreeDataRun[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primitiveDataRuns[i] = new hknpCompressedMeshShapeTreeDataRun(connector, stream, (int)arrValue.to + (i * hknpCompressedMeshShapeTreeDataRun.size));
			}
		}
	}
}