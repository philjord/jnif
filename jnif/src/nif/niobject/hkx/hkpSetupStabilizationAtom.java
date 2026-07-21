package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpSetupStabilizationAtom' version='3' signature='0x870ee10a' parent='hkpConstraintAtom'>
	<members>
		<member name='enabled' type='hkBool' offset='2' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='false'/>
		<member name='padding' type='hkUint8[1]' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='1' flags='SERIALIZE_IGNORED'/>
		<member name='maxLinImpulse' type='hkReal' offset='4' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='340282001837565597733306976381245063168.000000'/>
		<member name='maxAngImpulse' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='340282001837565597733306976381245063168.000000'/>
		<member name='maxAngle' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
	</members>
</struct>
*/

public class hkpSetupStabilizationAtom extends hkpConstraintAtom {

	public boolean	enabled;
	public byte		padding;
	public float	maxLinImpulse;
	public float	maxAngImpulse;
	public float	maxAngle;

	public hkpSetupStabilizationAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		enabled = stream.get(classOffset + 2) != 0;
		padding = stream.get(classOffset + 3);
		maxLinImpulse = stream.getFloat(classOffset + 4);
		maxAngImpulse = stream.getFloat(classOffset + 8);
		maxAngle = stream.getFloat(classOffset + 12);
	}
}