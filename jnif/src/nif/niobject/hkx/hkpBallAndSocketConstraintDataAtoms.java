package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpBallAndSocketConstraintDataAtoms' version='1' signature='0xe51e4bcc'>
	<members>
		<member name='pivots' type='struct hkpSetLocalTranslationsConstraintAtom' ctype='hkpSetLocalTranslationsConstraintAtom' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='setupStabilization' type='struct hkpSetupStabilizationAtom' ctype='hkpSetupStabilizationAtom' offset='48' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='ballSocket' type='struct hkpBallSocketConstraintAtom' ctype='hkpBallSocketConstraintAtom' offset='64' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkpBallAndSocketConstraintDataAtoms {

	public hkpSetLocalTranslationsConstraintAtom	pivots;
	public hkpSetupStabilizationAtom				setupStabilization;
	public hkpBallSocketConstraintAtom				ballSocket;

	public hkpBallAndSocketConstraintDataAtoms(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		pivots = new hkpSetLocalTranslationsConstraintAtom(connector, stream, classOffset + 0);
		setupStabilization = new hkpSetupStabilizationAtom(connector, stream, classOffset + 48);
		ballSocket = new hkpBallSocketConstraintAtom(connector, stream, classOffset + 64);
	}
}