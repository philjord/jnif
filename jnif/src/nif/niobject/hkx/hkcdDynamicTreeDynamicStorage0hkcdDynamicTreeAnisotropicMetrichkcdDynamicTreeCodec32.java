package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32' version='0' signature='0x0e38e7e3' parent='hkcdDynamicTreeAnisotropicMetric'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdDynamicTreeCodec32&gt;' ctype='hkcdDynamicTreeCodec32' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='firstFree' type='hkUint16' offset='16' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32
		extends hkcdDynamicTreeAnisotropicMetric {
	hkcdDynamicTreeCodec32[]	nodes;
	int							firstFree;

	public hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32(HKXReaderConnector connector,
																								ByteBuffer stream,
																								int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			nodes = new hkcdDynamicTreeCodec32[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdDynamicTreeCodec32(connector, stream,
						(int)arrValue.to + (i * hkcdDynamicTreeCodec32.size));
			}
		}

		firstFree = Short.toUnsignedInt(stream.getShort(classOffset + 16));
	}
}