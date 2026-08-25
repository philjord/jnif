package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hknpMotionProperties' version='3' signature='0x5dee8641'>
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
</struct>
*/

public class hknpMotionProperties {

	public static final int	size	= 60 + 4;
	int						isExclusive;
	int						flags;
	float					gravityFactor;
	float					timeFactor;
	float					maxLinearSpeed;
	float					maxAngularSpeed;
	float					linearDamping;
	float					angularDamping;
	float					solverStabilizationSpeedThreshold;
	float					solverStabilizationSpeedReduction;
	float					maxDistSqrd;
	float					maxRotSqrd;
	float					invBlockSize;
	int						pathingUpperThreshold;
	int						pathingLowerThreshold;
	int						numDeactivationFrequencyPasses;
	int						deactivationVelocityScaleSquare;
	int						minimumPathingVelocityScaleSquare;
	int						spikingVelocityScaleThresholdSquared;
	int						minimumSpikingVelocityScaleSquared;

	public hknpMotionProperties(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		isExclusive = stream.getInt(classOffset + 0);
		flags = stream.getInt(classOffset + 4);
		gravityFactor = stream.getFloat(classOffset + 8);
		timeFactor = stream.getFloat(classOffset + 12);
		maxLinearSpeed = stream.getFloat(classOffset + 16);
		maxAngularSpeed = stream.getFloat(classOffset + 20);
		linearDamping = stream.getFloat(classOffset + 24);
		angularDamping = stream.getFloat(classOffset + 28);
		solverStabilizationSpeedThreshold = stream.getFloat(classOffset + 32);
		solverStabilizationSpeedReduction = stream.getFloat(classOffset + 36);
		maxDistSqrd = stream.getFloat(classOffset + 40);
		maxRotSqrd = stream.getFloat(classOffset + 44);
		invBlockSize = stream.getFloat(classOffset + 48);
		pathingUpperThreshold = stream.getShort(classOffset + 52);
		pathingLowerThreshold = stream.getShort(classOffset + 54);
		numDeactivationFrequencyPasses = Byte.toUnsignedInt(stream.get(classOffset + 56));
		deactivationVelocityScaleSquare = Byte.toUnsignedInt(stream.get(classOffset + 57));
		minimumPathingVelocityScaleSquare = Byte.toUnsignedInt(stream.get(classOffset + 58));
		spikingVelocityScaleThresholdSquared = Byte.toUnsignedInt(stream.get(classOffset + 59));
		minimumSpikingVelocityScaleSquared = Byte.toUnsignedInt(stream.get(classOffset + 60));
	}

	
	
	/**
	 Outline for Havok_TagObject of type hknpMotionProperties
	Havok_TagType None
	Havok_TagType hknpMotionProperties
	Havok_TagMember isExclusive of type hkUint32
	Havok_TagMember flags of type unsigned int
	Havok_TagMember gravityFactor of type hkReal
	Havok_TagMember timeFactor of type hkReal
	Havok_TagMember maxLinearSpeed of type hkReal
	Havok_TagMember maxAngularSpeed of type hkReal
	Havok_TagMember linearDamping of type hkReal
	Havok_TagMember angularDamping of type hkReal
	Havok_TagMember solverStabilizationSpeedThreshold of type hkReal
	Havok_TagMember solverStabilizationSpeedReduction of type hkReal
	Havok_TagMember maxDistSqrd of type hkReal
	Havok_TagMember maxRotSqrd of type hkReal
	Havok_TagMember invBlockSize of type hkReal
	Havok_TagMember pathingUpperThreshold of type hkInt16
	Havok_TagMember pathingLowerThreshold of type hkInt16
	Havok_TagMember numDeactivationFrequencyPasses of type hkUint8
	Havok_TagMember deactivationVelocityScaleSquare of type hkUint8
	Havok_TagMember minimumPathingVelocityScaleSquare of type hkUint8
	Havok_TagMember spikingVelocityScaleThresholdSquared of type hkUint8
	Havok_TagMember minimumSpikingVelocityScaleSquared of type hkUint8
	 */
	public hknpMotionProperties(Havok_TagObject item) { 
		//item.outputOutline();
		int memberIdx = 0;		
		isExclusive = item.listObjectClass.get(memberIdx++).i_value;
		flags = item.listObjectClass.get(memberIdx++).i_value;
		gravityFactor = item.listObjectClass.get(memberIdx++).f_value;
		timeFactor = item.listObjectClass.get(memberIdx++).f_value;
		maxLinearSpeed = item.listObjectClass.get(memberIdx++).f_value;
		maxAngularSpeed = item.listObjectClass.get(memberIdx++).f_value;
		linearDamping = item.listObjectClass.get(memberIdx++).f_value;
		angularDamping = item.listObjectClass.get(memberIdx++).f_value;
		solverStabilizationSpeedThreshold = item.listObjectClass.get(memberIdx++).f_value;
		solverStabilizationSpeedReduction = item.listObjectClass.get(memberIdx++).f_value;
		maxDistSqrd = item.listObjectClass.get(memberIdx++).f_value;
		maxRotSqrd = item.listObjectClass.get(memberIdx++).f_value;
		invBlockSize = item.listObjectClass.get(memberIdx++).f_value;
		pathingUpperThreshold = item.listObjectClass.get(memberIdx++).i_value;
		pathingLowerThreshold = item.listObjectClass.get(memberIdx++).i_value;
		numDeactivationFrequencyPasses = item.listObjectClass.get(memberIdx++).i_value;
		deactivationVelocityScaleSquare = item.listObjectClass.get(memberIdx++).i_value;
		minimumPathingVelocityScaleSquare = item.listObjectClass.get(memberIdx++).i_value;
		spikingVelocityScaleThresholdSquared = item.listObjectClass.get(memberIdx++).i_value;
		minimumSpikingVelocityScaleSquared = item.listObjectClass.get(memberIdx++).i_value;
	}
}
