package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticMeshTreeBase' version='0' signature='0xf885dcd0' parent='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5'>
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
</struct>*/
public class hkcdStaticMeshTreeBase extends hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5 {
	public int numPrimitiveKeys;
	public int bitsPerKey;
	public int maxKeyValue;
	public hkcdStaticMeshTreeBaseSection[] sections;
	public hkcdStaticMeshTreeBasePrimitive[] primitives;
	public int[] sharedVerticesIndex;
	
	public hkcdStaticMeshTreeBase(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='numPrimitiveKeys' type='hkInt32' offset='48' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numPrimitiveKeys =  stream.getInt(48);
		//<member name='bitsPerKey' type='hkInt32' offset='52' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		bitsPerKey =  stream.getInt(52);
		//<member name='maxKeyValue' type='hkUint32' offset='56' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxKeyValue =  stream.getInt(56);
		//<member name='sections' type='hkArray&lt;struct hkcdStaticMeshTreeBaseSection&gt;' ctype='hkcdStaticMeshTreeBaseSection' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 64);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 64;			
			sections = new hkcdStaticMeshTreeBaseSection[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sections[i] = new hkcdStaticMeshTreeBaseSection(connector, (int)arrValue.to + (i * hkcdStaticMeshTreeBaseSection.size));
			}
		}
		
		//<member name='primitives' type='hkArray&lt;struct hkcdStaticMeshTreeBasePrimitive&gt;' ctype='hkcdStaticMeshTreeBasePrimitive' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 80);
		baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 80;
			primitives = new hkcdStaticMeshTreeBasePrimitive[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primitives[i] = new hkcdStaticMeshTreeBasePrimitive(connector, (int)arrValue.to + (i * hkcdStaticMeshTreeBasePrimitive.size));
			}
		}
		//<member name='sharedVerticesIndex' type='hkArray&lt;hkUint16&gt;' offset='96' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 96);
		baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 96;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			sharedVerticesIndex = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				sharedVerticesIndex[i] = Short.toUnsignedInt(s2.getShort(i*2));
			}
		}
	}
}