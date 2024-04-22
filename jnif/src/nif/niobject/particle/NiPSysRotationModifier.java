package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;
import nif.compound.NifVector4;

public class NiPSysRotationModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysRotationModifier" inherit="NiPSysModifier" module="NiParticle">
        Particle modifier that adds rotations to particles.
        <field name="Rotation Speed" type="float">Initial Rotation Speed in radians per second.</field>
        <field name="Rotation Speed Variation" type="float" since="20.0.0.2">Distributes rotation speed over the range [Speed - Variation, Speed + Variation].</field>
        <field name="Unknown Vector" type="Vector4" vercond="#BS_GTE_F76#" />
        <field name="Unknown Byte" type="byte" vercond="#BS_GTE_F76#" />
        <field name="Rotation Angle" type="float" since="20.0.0.2">Initial Rotation Angle in radians.</field>
        <field name="Rotation Angle Variation" type="float" since="20.0.0.2">Distributes rotation angle over the range [Angle - Variation, Angle + Variation].</field>
        <field name="Random Rot Speed Sign" type="bool" since="20.0.0.2">Randomly negate the initial rotation speed?</field>
        <field name="Random Axis" type="bool" default="true">Assign a random axis to new particles?</field>
        <field name="Axis" type="Vector3" default="#X_AXIS#">Initial rotation axis.</field>
    </niobject>
	 */

	public float initialRotationSpeed;

	public float initialRotationSpeedVariation;

	public float initialRotationAngle;

	public float initialRotationAngleVariation;

	public boolean randomRotSpeedSign;

	public boolean randomInitialAxis;

	public NifVector3 initialAxis;

	public NifVector4 UnknownVector;

	public byte UnknownByte;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		initialRotationSpeed = ByteConvert.readFloat(stream);
		//<field name="Rotation Speed Variation" type="float" since="20.0.0.2">Distributes rotation speed over the range [Speed - Variation, Speed + Variation].</field>
		if(nifVer.LOAD_VER >= NifVer.VER_20_0_0_2)
			initialRotationSpeedVariation = ByteConvert.readFloat(stream);
		
		
		if(nifVer.BS_GTE_F76()) {
			//<field name="Unknown Vector" type="Vector4" vercond="#BS_GTE_F76#" />
			UnknownVector = new NifVector4(stream);
			//<field name="Unknown Byte" type="byte" vercond="#BS_GTE_F76#" />
			UnknownByte = ByteConvert.readByte(stream);
		}
		
		if(nifVer.LOAD_VER >= NifVer.VER_20_0_0_2) {
			//<field name="Rotation Angle" type="float" since="20.0.0.2">Initial Rotation Angle in radians.</field>
			initialRotationAngle = ByteConvert.readFloat(stream);
			//<field name="Rotation Angle Variation" type="float" since="20.0.0.2">Distributes rotation angle over the range [Angle - Variation, Angle + Variation].</field>
			initialRotationAngleVariation = ByteConvert.readFloat(stream);
			//<field name="Random Rot Speed Sign" type="bool" since="20.0.0.2">Randomly negate the initial rotation speed?</field>
			randomRotSpeedSign = ByteConvert.readBool(stream, nifVer);
		}
	        	
		//<field name="Random Axis" type="bool" default="true">Assign a random axis to new particles?</field>       
		randomInitialAxis = ByteConvert.readBool(stream, nifVer);
		 //<field name="Axis" type="Vector3" default="#X_AXIS#">Initial rotation axis.</field>
		initialAxis = new NifVector3(stream);

		return success;
	}
}
