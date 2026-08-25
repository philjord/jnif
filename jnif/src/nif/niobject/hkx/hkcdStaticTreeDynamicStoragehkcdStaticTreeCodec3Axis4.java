package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis4' version='0' signature='0x27cc0eb4'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdStaticTreeCodec3Axis4&gt;' ctype='hkcdStaticTreeCodec3Axis4' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis4 {
	public hkcdStaticTreeCodec3Axis4[] nodes;

	public hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis4(	HKXReaderConnector connector, ByteBuffer stream,
																	int classOffset)
			throws IOException, InvalidPositionException {

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			nodes = new hkcdStaticTreeCodec3Axis4[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdStaticTreeCodec3Axis4(connector, stream,
						(int)arrValue.to + (i * hkcdStaticTreeCodec3Axis4.size));
			}
		}
	}

	/**
		Outline for Havok_TagType hkcdStaticTree::DynamicStorage
		Havok_TagMember nodes of type hkArray
		
		*/
	public hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis4(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;
		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		nodes = new hkcdStaticTreeCodec3Axis4[arrSize];
		for (int i = 0; i < arrSize; i++) {
			nodes[i] = new hkcdStaticTreeCodec3Axis4(value.listObjectArray.get(i));
		}
	}
}