package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <class name='hkcdSimdTree' version='1' signature='0x84c43960' parent='hkBaseObject'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdSimdTreeNode&gt;' ctype='hkcdSimdTreeNode' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkcdSimdTree extends hkBaseObject {
	hkcdSimdTreeNode[] nodes;

	public hkcdSimdTree(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 8));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 8;
			nodes = new hkcdSimdTreeNode[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdSimdTreeNode(connector, stream, (int)arrValue.to + (i * hkcdSimdTreeNode.size));
			}
		}
	}

	/**
	 Outline for Havok_TagObject of type hkcdSimdTree
	Havok_TagType None
	Havok_TagType hkBaseObject
	Havok_TagType hkcdSimdTree
	Havok_TagMember nodes of type hkArray
	 */
	public hkcdSimdTree(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;
		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		nodes = new hkcdSimdTreeNode[arrSize];
		for (int i = 0; i < arrSize; i++) {
			nodes[i] =  new hkcdSimdTreeNode(value.listObjectArray.get(i));		
		}
		
	}
}