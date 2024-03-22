package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' version='0' signature='0x99a1f34c'>
	<members>
		<member name='elements' type='hkArray&lt;struct hknpShapeInstance&gt;' ctype='hknpShapeInstance' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='firstFree' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance  {
	hknpShapeInstance[] elements;
	int firstFree;
	
	public hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='elements' type='hkArray&lt;struct hknpShapeInstance&gt;' ctype='hknpShapeInstance' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			elements = new hknpShapeInstance[arrSize];
			for (int i = 0; i < arrSize; i++) {
				elements[i] = new hknpShapeInstance(connector, (int)arrValue.to + (i*hknpShapeInstance.size));
			}
		}
		//<member name='firstFree' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		firstFree = stream.getInt(16);
		
	}
}
	