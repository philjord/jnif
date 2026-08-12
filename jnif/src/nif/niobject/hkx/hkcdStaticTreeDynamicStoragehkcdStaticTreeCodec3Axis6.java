package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 * <struct name='hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis6' version='0' signature='0xdabf38d5'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdStaticTreeCodec3Axis6&gt;' ctype='hkcdStaticTreeCodec3Axis6' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis6 {
	hkcdStaticTreeCodec3Axis6[] nodes;

	public hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis6(	HKXReaderConnector connector, ByteBuffer stream,
																	int classOffset)
			throws IOException, InvalidPositionException {

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0 ) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			nodes = new hkcdStaticTreeCodec3Axis6[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdStaticTreeCodec3Axis6(connector, stream,
						(int)arrValue.to + (i * hkcdStaticTreeCodec3Axis6.size));
			}
		}
	}
}