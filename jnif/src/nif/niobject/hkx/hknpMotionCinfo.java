package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpMotionCinfo' version='1' signature='0x693d1b16'>
	<members>
		<member name='motionPropertiesId' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='enableDeactivation' type='hkBool' offset='2' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='true'/>
		<member name='inverseMass' type='hkReal' offset='4' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		<member name='massFactor' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		<member name='maxLinearAccelerationDistancePerStep' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		<member name='maxRotationToPreventTunneling' type='hkReal' offset='16' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		<member name='inverseInertiaLocal' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='centerOfMassWorld' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='orientation' type='hkQuaternion' offset='64' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='linearVelocity' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='angularVelocity' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hknpMotionCinfo {

	public static final int	size	= 96 + 16;
	public int						motionPropertiesId;
	public boolean					enableDeactivation;
	public float					inverseMass;
	public float					massFactor;
	public float					maxLinearAccelerationDistancePerStep;
	public float					maxRotationToPreventTunneling;
	public NifVector4				inverseInertiaLocal;
	public NifVector4				centerOfMassWorld;
	public NifQuaternionXYZW		orientation;
	public NifVector4				linearVelocity;
	public NifVector4				angularVelocity;

	public hknpMotionCinfo(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		motionPropertiesId = Short.toUnsignedInt(stream.getShort(classOffset + 0));
		enableDeactivation = stream.get(classOffset + 2) != 0;
		inverseMass = stream.getFloat(classOffset + 4);
		massFactor = stream.getFloat(classOffset + 8);
		maxLinearAccelerationDistancePerStep = stream.getFloat(classOffset + 12);
		maxRotationToPreventTunneling = stream.getFloat(classOffset + 16);
		inverseInertiaLocal = new NifVector4(stream, classOffset + 32);
		centerOfMassWorld = new NifVector4(stream, classOffset + 48);
		orientation = new NifQuaternionXYZW(stream, classOffset + 64);
		linearVelocity = new NifVector4(stream, classOffset + 80);
		angularVelocity = new NifVector4(stream, classOffset + 96);

	}
}