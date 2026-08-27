package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdSimdTreeNode' version='0' signature='0xc4e406c7' parent='hkcdFourAabb'>
	<enums>
		<enum name='Flags' flags='00000000'>
			<enumitem name='HAS_INTERNALS' value='1'/>
			<enumitem name='HAS_LEAVES' value='2'/>
			<enumitem name='HAS_NULLS' value='4'/>
		</enum>
	</enums>
	<members>
		<member name='data' type='hkUint32[4]' offset='96' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkcdSimdTreeNode extends hkcdFourAabb {
	public static final int	size	= 112;
	int[]					data	= new int[4];

	public hkcdSimdTreeNode(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		data[0] = stream.getInt(classOffset + 96);
		data[1] = stream.getInt(classOffset + 100);
		data[2] = stream.getInt(classOffset + 104);
		data[3] = stream.getInt(classOffset + 108);
	}

	/**
	 Outline for Havok_TagType hkcdSimdTree::Node
	Havok_TagMember data of type T[N]
	 */
	public hkcdSimdTreeNode(Havok_TagObject item) {
		super(item);
		//item.outputOutline();
		int memberIdx = 6;

		Havok_TagObject value = item.listObjectClass.get(memberIdx++);

		data[0] = value.listObjectTuple.get(0).i_value;
		data[1] = value.listObjectTuple.get(1).i_value;
		data[2] = value.listObjectTuple.get(2).i_value;
		data[3] = value.listObjectTuple.get(3).i_value;
	}
}