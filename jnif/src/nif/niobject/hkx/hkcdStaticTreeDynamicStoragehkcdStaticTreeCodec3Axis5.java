package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis5' version='0' signature='0x0926c0cf'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdStaticTreeCodec3Axis5&gt;' ctype='hkcdStaticTreeCodec3Axis5' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/


public class hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis5 {
	public hkcdStaticTreeCodec3Axis5[] nodes;
	public hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis5(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		
		//<member name='nodes' type='hkArray&lt;struct hkcdStaticTreeCodec3Axis5&gt;' ctype='hkcdStaticTreeCodec3Axis5' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			nodes = new hkcdStaticTreeCodec3Axis5[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdStaticTreeCodec3Axis5(connector, (int)arrValue.to + (i * hkcdStaticTreeCodec3Axis5.size));
			}
		}
	}
}