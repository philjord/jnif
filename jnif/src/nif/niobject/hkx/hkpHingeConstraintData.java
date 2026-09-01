package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkpHingeConstraintData' version='0' signature='0x901a5ffa' parent='hkpConstraintData'>
	<members>
		<member name='atoms' type='struct hkpHingeConstraintDataAtoms' ctype='hkpHingeConstraintDataAtoms' offset='32' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
	</members>
</class>
*/

public class hkpHingeConstraintData extends hkpConstraintData {

	public hkpHingeConstraintDataAtoms atoms;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		atoms = new hkpHingeConstraintDataAtoms(connector, stream, classOffset + 32);
		return success;
	}

}