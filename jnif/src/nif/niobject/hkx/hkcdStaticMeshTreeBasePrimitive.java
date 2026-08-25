package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticMeshTreeBasePrimitive' version='0' signature='0x56da2f7c'>
	<enums>
		<enum name='Type' flags='00000000'>
			<enumitem name='INVALID' value='0'/>
			<enumitem name='TRIANGLE' value='1'/>
			<enumitem name='QUAD' value='2'/>
			<enumitem name='CUSTOM' value='3'/>
			<enumitem name='NUM_TYPES' value='4'/>
		</enum>
	</enums>
	<members>
		<member name='indices' type='hkUint8[4]' offset='0' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticMeshTreeBasePrimitive {
	public static final int	size	= 4;
	public int[]			indices	= new int[4];

	public hkcdStaticMeshTreeBasePrimitive(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		indices[0] = Byte.toUnsignedInt(stream.get(classOffset + 0));
		indices[1] = Byte.toUnsignedInt(stream.get(classOffset + 1));
		indices[2] = Byte.toUnsignedInt(stream.get(classOffset + 2));
		indices[3] = Byte.toUnsignedInt(stream.get(classOffset + 3));
	}

	/**
	 Outline for Havok_TagType hkcdStaticMeshTreeBase::Primitive
	Havok_TagMember indices of type T[N]
	 */
	public hkcdStaticMeshTreeBasePrimitive(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;

		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		
		indices[0] = value.listObjectTuple.get(0).i_value;
		indices[1] = value.listObjectTuple.get(1).i_value;
		indices[2] = value.listObjectTuple.get(2).i_value;
		indices[3] = value.listObjectTuple.get(3).i_value;

	}
}