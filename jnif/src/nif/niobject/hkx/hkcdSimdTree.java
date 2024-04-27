package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hkcdSimdTree' version='1' signature='0x84c43960' parent='hkBaseObject'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdSimdTreeNode&gt;' ctype='hkcdSimdTreeNode' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hkcdSimdTree extends hkBaseObject {
	hkcdSimdTreeNode[] nodes;
	public hkcdSimdTree(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		//<member name='nodes' type='hkArray&lt;struct hkcdSimdTreeNode&gt;' ctype='hkcdSimdTreeNode' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 8);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 8;
			nodes = new hkcdSimdTreeNode[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdSimdTreeNode(connector, stream, (int)arrValue.to + (i * hkcdSimdTreeNode.size));
			}
		}
	}
}