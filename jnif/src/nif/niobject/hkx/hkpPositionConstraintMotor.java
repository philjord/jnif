package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkpPositionConstraintMotor' version='0' signature='0x143dd400' parent='hkpLimitedForceConstraintMotor'>
	<members>
		<member name='tau' type='hkReal' offset='32' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='damping' type='hkReal' offset='36' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='proportionalRecoveryVelocity' type='hkReal' offset='40' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='constantRecoveryVelocity' type='hkReal' offset='44' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkpPositionConstraintMotor extends hkpLimitedForceConstraintMotor {

	public float	tau;
	public float	damping;
	public float	proportionalRecoveryVelocity;
	public float	constantRecoveryVelocity;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		tau = stream.getFloat(classOffset + 32);
		damping = stream.getFloat(classOffset + 36);
		proportionalRecoveryVelocity = stream.getFloat(classOffset + 40);
		constantRecoveryVelocity = stream.getFloat(classOffset + 44);
		return success;
	}

}