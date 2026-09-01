package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpHingeConstraintDataAtoms' version='1' signature='0x1f6f4807'>
	<enums>
		<enum name='Axis' flags='00000000'>
			<enumitem name='AXIS_AXLE' value='0'/>
		</enum>
	</enums>
	<members>
		<member name='transforms' type='struct hkpSetLocalTransformsConstraintAtom' ctype='hkpSetLocalTransformsConstraintAtom' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='setupStabilization' type='struct hkpSetupStabilizationAtom' ctype='hkpSetupStabilizationAtom' offset='144' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='2dAng' type='struct hkp2dAngConstraintAtom' ctype='hkp2dAngConstraintAtom' offset='160' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='ballSocket' type='struct hkpBallSocketConstraintAtom' ctype='hkpBallSocketConstraintAtom' offset='176' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkpHingeConstraintDataAtoms {
	enum Axis {
		AXIS_AXLE
	};

	public hkpSetLocalTransformsConstraintAtom	transforms;
	public hkpSetupStabilizationAtom			setupStabilization;
	public hkp2dAngConstraintAtom				_2dAng;
	public hkpBallSocketConstraintAtom			ballSocket;

	public hkpHingeConstraintDataAtoms(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		transforms = new hkpSetLocalTransformsConstraintAtom(connector, stream, classOffset + 0);
		setupStabilization = new hkpSetupStabilizationAtom(connector, stream, classOffset + 144);
		_2dAng = new hkp2dAngConstraintAtom(connector, stream, classOffset + 160);
		ballSocket = new hkpBallSocketConstraintAtom(connector, stream, classOffset + 176);
	}
}