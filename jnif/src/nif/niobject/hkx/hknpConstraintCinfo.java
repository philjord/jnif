package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hknpConstraintCinfo' version='2' signature='0x67ea986d'>
	<members>
		<member name='constraintData' type='struct hkpConstraintData*' ctype='hkpConstraintData' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bodyA' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bodyB' type='hkUint32' offset='12' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='16' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpConstraintCinfo {

	public static final int	size	= 16 + 1;
	public long				constraintData;
	public int				bodyA;
	public int				bodyB;
	public int				flags;

	public hknpConstraintCinfo(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		constraintData = HKXReader.getPointer(connector, classOffset + 0);
		bodyA = stream.getInt(classOffset + 8);
		bodyB = stream.getInt(classOffset + 12);
		flags = Byte.toUnsignedInt(stream.get(classOffset + 16));
	}

	public hknpConstraintCinfo(Havok_TagObject item) {
		item.outputOutline();
		int memberIdx = 2;

		System.out.println("NOT DONE!");
		constraintData = item.listObjectClass.get(memberIdx++).i_value;// needs to be an offset
		bodyA = item.listObjectClass.get(memberIdx++).i_value;
		bodyB = item.listObjectClass.get(memberIdx++).i_value;
		flags = item.listObjectClass.get(memberIdx++).i_value;
	}
}