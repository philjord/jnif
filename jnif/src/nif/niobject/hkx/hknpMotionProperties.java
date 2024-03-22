package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpMotionProperties' version='3' signature='0x5dee8641'>
	<enums>
		<enum name='FlagsEnum' flags='00000000'>
			<enumitem name='NEVER_REBUILD_MASS_PROPERTIES' value='2'/>
			<enumitem name='ENABLE_GRAVITY_MODIFICATION' value='536870912'/>
			<enumitem name='ENABLE_TIME_FACTOR' value='1073741824'/>
			<enumitem name='FLAGS_MASK' value='-536870912'/>
			<enumitem name='AUTO_FLAGS_MASK' value='66060288'/>
		</enum>
		<enum name='SolverStabilizationType' flags='00000000'>
			<enumitem name='SOLVER_STABILIZATION_OFF' value='0'/>
			<enumitem name='SOLVER_STABILIZATION_LOW' value='1'/>
			<enumitem name='SOLVER_STABILIZATION_MEDIUM' value='2'/>
			<enumitem name='SOLVER_STABILIZATION_HIGH' value='3'/>
			<enumitem name='SOLVER_STABILIZATION_AGGRESSIVE' value='4'/>
		</enum>
		<enum name='DeactivationStrategy' flags='00000000'>
			<enumitem name='DEACTIVATION_STRATEGY_AGGRESSIVE' value='3'/>
			<enumitem name='DEACTIVATION_STRATEGY_BALANCED' value='4'/>
			<enumitem name='DEACTIVATION_STRATEGY_ACCURATE' value='5'/>
		</enum>
	</enums>
	<members>
		<member name='isExclusive' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='4' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='gravityFactor' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		<member name='timeFactor' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		<member name='maxLinearSpeed' type='hkReal' offset='16' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxAngularSpeed' type='hkReal' offset='20' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='linearDamping' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		<member name='angularDamping' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		<member name='solverStabilizationSpeedThreshold' type='hkReal' offset='32' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		<member name='solverStabilizationSpeedReduction' type='hkReal' offset='36' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		<member name='maxDistSqrd' type='hkReal' offset='40' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxRotSqrd' type='hkReal' offset='44' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='invBlockSize' type='hkReal' offset='48' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='pathingUpperThreshold' type='hkInt16' offset='52' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='pathingLowerThreshold' type='hkInt16' offset='54' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numDeactivationFrequencyPasses' type='hkUint8' offset='56' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='deactivationVelocityScaleSquare' type='hkUint8' offset='57' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='minimumPathingVelocityScaleSquare' type='hkUint8' offset='58' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='spikingVelocityScaleThresholdSquared' type='hkUint8' offset='59' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='minimumSpikingVelocityScaleSquared' type='hkUint8' offset='60' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpMotionProperties  {
	
	public static final int size = 61; // or does align 16 want this to be 64?
	int isExclusive;
	int flags;
	float gravityFactor;
	float timeFactor;
	float maxLinearSpeed;
	float maxAngularSpeed;
	float linearDamping;
	float angularDamping;
	float solverStabilizationSpeedThreshold;
	float solverStabilizationSpeedReduction;
	float maxDistSqrd;
	float maxRotSqrd;
	float invBlockSize;
	int pathingUpperThreshold;
	int pathingLowerThreshold;
	int numDeactivationFrequencyPasses;
	int deactivationVelocityScaleSquare;
	int minimumPathingVelocityScaleSquare;
	int spikingVelocityScaleThresholdSquared;
	int minimumSpikingVelocityScaleSquared;
	
	public hknpMotionProperties(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='isExclusive' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		isExclusive = stream.getInt(0);
		//<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='4' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		flags = stream.getInt(4);
		//<member name='gravityFactor' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		gravityFactor = stream.getFloat(8);
		//<member name='timeFactor' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		timeFactor = stream.getFloat(12);
		//<member name='maxLinearSpeed' type='hkReal' offset='16' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxLinearSpeed = stream.getFloat(16);
		//<member name='maxAngularSpeed' type='hkReal' offset='20' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxAngularSpeed = stream.getFloat(20);
		//<member name='linearDamping' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		linearDamping = stream.getFloat(24);
		//<member name='angularDamping' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		angularDamping = stream.getFloat(28);
		//<member name='solverStabilizationSpeedThreshold' type='hkReal' offset='32' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		solverStabilizationSpeedThreshold = stream.getFloat(32);
		//<member name='solverStabilizationSpeedReduction' type='hkReal' offset='36' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		solverStabilizationSpeedReduction = stream.getFloat(36);
		//<member name='maxDistSqrd' type='hkReal' offset='40' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxDistSqrd = stream.getFloat(40);
		//<member name='maxRotSqrd' type='hkReal' offset='44' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxRotSqrd = stream.getFloat(44);
		//<member name='invBlockSize' type='hkReal' offset='48' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		invBlockSize = stream.getFloat(48);
		//<member name='pathingUpperThreshold' type='hkInt16' offset='52' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		pathingUpperThreshold = stream.getShort(52);
		//<member name='pathingLowerThreshold' type='hkInt16' offset='54' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		pathingLowerThreshold = stream.getShort(54);
		//<member name='numDeactivationFrequencyPasses' type='hkUint8' offset='56' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numDeactivationFrequencyPasses = Byte.toUnsignedInt(stream.get(56));
		//<member name='deactivationVelocityScaleSquare' type='hkUint8' offset='57' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		deactivationVelocityScaleSquare = Byte.toUnsignedInt(stream.get(57));
		//<member name='minimumPathingVelocityScaleSquare' type='hkUint8' offset='58' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		minimumPathingVelocityScaleSquare = Byte.toUnsignedInt(stream.get(58));
		//<member name='spikingVelocityScaleThresholdSquared' type='hkUint8' offset='59' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		spikingVelocityScaleThresholdSquared = Byte.toUnsignedInt(stream.get(59));
		//<member name='minimumSpikingVelocityScaleSquared' type='hkUint8' offset='60' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		minimumSpikingVelocityScaleSquared = Byte.toUnsignedInt(stream.get(60));
	}
}
