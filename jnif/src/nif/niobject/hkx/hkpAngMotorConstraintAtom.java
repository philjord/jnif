package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpAngMotorConstraintAtom' version='1' signature='0x42498456' parent='hkpConstraintAtom'>
	<members>
		<member name='isEnabled' type='hkBool' offset='2' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='motorAxis' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='initializedOffset' type='hkInt16' offset='4' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='previousTargetAngleOffset' type='hkInt16' offset='6' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='correspondingAngLimitSolverResultOffset' type='hkInt16' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='targetAngle' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='motor' type='struct hkpConstraintMotor*' ctype='hkpConstraintMotor' offset='16' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='padding' type='hkUint8[12]' offset='24' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='12' flags='SERIALIZE_IGNORED'/>
	</members>
</struct>
*/

public class hkpAngMotorConstraintAtom extends hkpConstraintAtom {

	public boolean	isEnabled;
	public byte		motorAxis;
	public int		initializedOffset;
	public int		previousTargetAngleOffset;
	public int		correspondingAngLimitSolverResultOffset;
	public float	targetAngle;
	public long		motor;
	//public byte[]	padding	= new byte[12];

	public hkpAngMotorConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		isEnabled = stream.get(classOffset + 2) != 0;
		motorAxis = stream.get(classOffset + 3);
		initializedOffset = stream.getShort(classOffset + 4);
		previousTargetAngleOffset = stream.getShort(classOffset + 6);
		correspondingAngLimitSolverResultOffset = stream.getShort(classOffset + 8);
		targetAngle = stream.getFloat(classOffset + 12);
		motor = HKXReader.getPointer(connector, classOffset + 16);
		// ignore padding
	}
}