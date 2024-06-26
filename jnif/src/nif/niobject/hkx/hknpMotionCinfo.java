package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
public class hknpMotionCinfo  {
	
	public static final int size = 112;  
	int motionPropertiesId;
	boolean enableDeactivation;
	float inverseMass;
	float massFactor;
	float maxLinearAccelerationDistancePerStep;
	float maxRotationToPreventTunneling;
	NifVector4 inverseInertiaLocal;
	NifVector4 centerOfMassWorld;
	NifQuaternionXYZW orientation;
	NifVector4 linearVelocity;
	NifVector4 angularVelocity;
	
	public hknpMotionCinfo(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		//<member name='motionPropertiesId' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		motionPropertiesId = Short.toUnsignedInt(stream.getShort(classOffset + 0));
		//<member name='enableDeactivation' type='hkBool' offset='2' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='true'/>
		enableDeactivation = stream.get(classOffset + 2) != 0;
		//<member name='inverseMass' type='hkReal' offset='4' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		inverseMass = stream.getFloat(classOffset + 4);
		//<member name='massFactor' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		massFactor = stream.getFloat(classOffset + 8);
		//<member name='maxLinearAccelerationDistancePerStep' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		maxLinearAccelerationDistancePerStep = stream.getFloat(classOffset + 12);
		//<member name='maxRotationToPreventTunneling' type='hkReal' offset='16' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		maxRotationToPreventTunneling = stream.getFloat(classOffset + 16);
		//<member name='inverseInertiaLocal' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		inverseInertiaLocal = new NifVector4(stream, classOffset + 32);
		//<member name='centerOfMassWorld' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		centerOfMassWorld = new NifVector4(stream, classOffset + 48);
		//<member name='orientation' type='hkQuaternion' offset='64' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		orientation = new NifQuaternionXYZW(stream, classOffset + 64);
		//<member name='linearVelocity' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		linearVelocity = new NifVector4(stream, classOffset + 80);
		//<member name='angularVelocity' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		angularVelocity = new NifVector4(stream, classOffset + 96);
		
		
	}
}