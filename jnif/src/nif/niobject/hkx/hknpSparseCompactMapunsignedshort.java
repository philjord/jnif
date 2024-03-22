package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/*
<struct name='hknpSparseCompactMapunsignedshort' version='0' signature='0x4558127c'>
	<members>
		<member name='secondaryKeyMask' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sencondaryKeyBits' type='hkUint32' offset='4' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='primaryKeyToIndex' type='hkArray&lt;hkUint16&gt;' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='valueAndSecondaryKeys' type='hkArray&lt;hkUint16&gt;' offset='24' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/


public class hknpSparseCompactMapunsignedshort  {
	int secondaryKeyMask;
	int sencondaryKeyBits;
	int[] primaryKeyToIndex;
	int[] valueAndSecondaryKeys;
	public hknpSparseCompactMapunsignedshort(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start

		
		//<member name='secondaryKeyMask' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		secondaryKeyMask =  stream.getInt(0);
		//<member name='sencondaryKeyBits' type='hkUint32' offset='4' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		sencondaryKeyBits =  stream.getInt(4);
		//<member name='primaryKeyToIndex' type='hkArray&lt;hkUint16&gt;' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 8);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 8;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			primaryKeyToIndex = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primaryKeyToIndex[i] = Short.toUnsignedInt(s2.getShort(i*2));
			}
		}
		//<member name='valueAndSecondaryKeys' type='hkArray&lt;hkUint16&gt;' offset='24' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 24);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 24;
			ByteBuffer s3 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			valueAndSecondaryKeys = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				valueAndSecondaryKeys[i] = Short.toUnsignedInt(s3.getShort(i*2));
			}
		}
		
	}
	
}