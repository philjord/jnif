package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkpLimitedForceConstraintMotor' version='0' signature='0x109808fd' parent='hkpConstraintMotor'>
	<members>
		<member name='minForce' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxForce' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkpLimitedForceConstraintMotor extends hkpConstraintMotor {

	public float	minForce;
	public float	maxForce;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		minForce = stream.getFloat(classOffset + 24);
		maxForce = stream.getFloat(classOffset + 28);
		return success;
	}

}