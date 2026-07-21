package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpBallSocketConstraintAtom' version='5' signature='0x6ba88f7a' parent='hkpConstraintAtom'>
	<members>
		<member name='solvingMethod' type='enum SolvingMethod' etype='SolvingMethod' offset='2' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bodiesToNotify' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0'/>
		<member name='velocityStabilizationFactor' type='struct hkUFloat8' ctype='hkUFloat8' offset='4' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='enableLinearImpulseLimit' type='hkBool' offset='5' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='false'/>
		<member name='breachImpulse' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='340282001837565597733306976381245063168.000000'/>
		<member name='inertiaStabilizationFactor' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
	</members>
</struct>
*/

public class hkpBallSocketConstraintAtom extends hkpConstraintAtom {

	public SolvingMethod	solvingMethod;
	public byte				bodiesToNotify;
	public byte				velocityStabilizationFactor;
	public boolean			enableLinearImpulseLimit;
	public float			breachImpulse;
	public float			inertiaStabilizationFactor;

	public hkpBallSocketConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		int solvingMethodv = stream.get(classOffset + 2);
		bodiesToNotify = stream.get(classOffset + 3);
		velocityStabilizationFactor = stream.get(classOffset + 4);//FIXME!!!! 8 bit float/ like a half half float? 255 rather than -1 type thign
		enableLinearImpulseLimit = stream.get(classOffset + 5) != 0;
		//TODO: am I missing or is this 4 byte align?
		breachImpulse = stream.getFloat(classOffset + 8);
		inertiaStabilizationFactor = stream.getFloat(classOffset + 12);
	}
}