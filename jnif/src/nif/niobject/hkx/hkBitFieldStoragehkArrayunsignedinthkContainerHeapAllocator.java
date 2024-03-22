package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/*<struct name='hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' version='0' signature='0xda41bd9b'>
	<members>
		<member name='words' type='hkArray&lt;hkUint32&gt;' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numBits' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator  {
	int[] words;
	int numBits;
	public hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{		
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='words' type='hkArray&lt;hkUint32&gt;' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			words = new int[arrSize];
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < arrSize; i++) {
				words[i] = s2.getInt((i * 4));
			}
		}
		
		//<member name='numBits' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numBits = stream.getInt(16);		
	}
}
