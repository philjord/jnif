package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpAngLimitConstraintAtom' version='0' signature='0x01c5a0dd' parent='hkpConstraintAtom'>
	<members>
		<member name='isEnabled' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='limitAxis' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='minAngle' type='hkReal' offset='4' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxAngle' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='angularLimitsTauFactor' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
	</members>
</struct>
*/

public class hkpAngLimitConstraintAtom extends hkpConstraintAtom {

	public byte		isEnabled;
	public byte		limitAxis;
	public float	minAngle;
	public float	maxAngle;
	public float	angularLimitsTauFactor;

	public hkpAngLimitConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		isEnabled = stream.get(classOffset + 2);
		limitAxis = stream.get(classOffset + 3);
		minAngle = stream.getFloat(classOffset + 4);
		maxAngle = stream.getFloat(classOffset + 8);
		angularLimitsTauFactor = stream.getFloat(classOffset + 12);
	}
}