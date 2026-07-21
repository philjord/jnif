package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaMeshBindingMapping' version='0' signature='0x48aceb75'>
	<members>
		<member name='mapping' type='hkArray&lt;hkInt16&gt;' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaMeshBindingMapping {
	public static final int	size	= 0 + 16;
	public int[]			mapping;

	public hkaMeshBindingMapping(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			mapping = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				mapping[i] = Short.toUnsignedInt(s2.getShort(i * 2));
			}
		}
	}

}