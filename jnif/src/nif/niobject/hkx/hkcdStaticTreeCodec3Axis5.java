package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticTreeCodec3Axis5' version='0' signature='0x12d67453' parent='hkcdStaticTreeCodec3Axis'>
	<members>
		<member name='hiData' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='loData' type='hkUint8' offset='4' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticTreeCodec3Axis5 extends hkcdStaticTreeCodec3Axis {
	public static int	size	= 4 + 1;

	int					hiData;
	int					loData;

	public hkcdStaticTreeCodec3Axis5(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		hiData = Byte.toUnsignedInt(stream.get(classOffset + 3));
		loData = Byte.toUnsignedInt(stream.get(classOffset + 4));
	}

	/**
	 Outline for Havok_TagType hkcdStaticTree::Codec3Axis5
	Havok_TagMember hiData of type hkUint8
	Havok_TagMember loData of type hkUint8
	 */
	public hkcdStaticTreeCodec3Axis5(Havok_TagObject item) {
		super(item);
		int memberIdx = 1;
		hiData = item.listObjectClass.get(memberIdx++).i_value;
		loData = item.listObjectClass.get(memberIdx++).i_value;
	}
}