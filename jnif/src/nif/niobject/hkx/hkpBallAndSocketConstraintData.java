package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkpBallAndSocketConstraintData' version='0' signature='0xd093f6ec' parent='hkpConstraintData'>
	<members>
		<member name='atoms' type='struct hkpBallAndSocketConstraintDataAtoms' ctype='hkpBallAndSocketConstraintDataAtoms' offset='32' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
	</members>
</class>
*/



public class hkpBallAndSocketConstraintData extends hkpConstraintData {

	public hkpBallAndSocketConstraintDataAtoms atoms;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		atoms = new hkpBallAndSocketConstraintDataAtoms(connector,  stream,classOffset + 32);
		return success;
	}

}