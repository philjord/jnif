package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

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
	public static final int	size32	= 0 + 12;
	public int[]			mapping;

	public hkaMeshBindingMapping(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {

		if (connector.header.is64bit) {
			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 0;
				mapping = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					mapping[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}
		} else {
			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 0));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 0;
				mapping = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					mapping[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}
		}
	}

}