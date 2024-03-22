package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifQuaternion;
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
	NifQuaternion orientation;
	NifVector4 linearVelocity;
	NifVector4 angularVelocity;
	
	public hknpMotionCinfo(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		//<member name='motionPropertiesId' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		motionPropertiesId = Short.toUnsignedInt(stream.getShort(0));
		//<member name='enableDeactivation' type='hkBool' offset='2' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='true'/>
		enableDeactivation = stream.get(2) != 0;
		//<member name='inverseMass' type='hkReal' offset='4' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		inverseMass = stream.getFloat(4);
		//<member name='massFactor' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='1.000000'/>
		massFactor = stream.getFloat(8);
		//<member name='maxLinearAccelerationDistancePerStep' type='hkReal' offset='12' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		maxLinearAccelerationDistancePerStep = stream.getFloat(12);
		//<member name='maxRotationToPreventTunneling' type='hkReal' offset='16' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='18446726481523507200.000000'/>
		maxRotationToPreventTunneling = stream.getFloat(16);
		//<member name='inverseInertiaLocal' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		inverseInertiaLocal = new NifVector4(stream, 32);
		//<member name='centerOfMassWorld' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		centerOfMassWorld = new NifVector4(stream, 48);
		//<member name='orientation' type='hkQuaternion' offset='64' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		orientation = new NifQuaternion(stream, 64);
		//<member name='linearVelocity' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		linearVelocity = new NifVector4(stream, 80);
		//<member name='angularVelocity' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		angularVelocity = new NifVector4(stream, 96);
		
		
	}
}