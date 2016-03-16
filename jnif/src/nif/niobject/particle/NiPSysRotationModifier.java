package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysRotationModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysRotationModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Particle modifier that adds rotations to particles.
	 
	 <add name="Initial Rotation Speed" type="float">The initial speed of rotation.</add>
	 <add name="Initial Rotation Speed Variation" type="float" ver1="20.0.0.4">Adds a ranged randomness to rotation speed.</add>
	 <add name="Initial Rotation Angle" type="float" ver1="20.0.0.4">
	 Sets the intial angle for particles to be birthed in.
	 </add>
	 <add name="Initial Rotation Angle Variation" type="float" ver1="20.0.0.4">Adds a random range to Initial angle.</add>
	 <add name="Random Rot Speed Sign" type="bool" ver1="20.0.0.4">Unknown</add>
	 <add name="Random Initial Axis" type="bool">Unknown.</add>
	 <add name="Initial Axis" type="Vector3">Unknown.</add>
	 </niobject>
	 */

	public float initialRotationSpeed;

	public float initialRotationSpeedVariation;

	public float initialRotationAngle;

	public float initialRotationAngleVariation;

	public boolean randomRotSpeedSign;

	public boolean randomInitialAxis;

	public NifVector3 initialAxis;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		initialRotationSpeed = ByteConvert.readFloat(stream);
		initialRotationSpeedVariation = ByteConvert.readFloat(stream);
		initialRotationAngle = ByteConvert.readFloat(stream);
		initialRotationAngleVariation = ByteConvert.readFloat(stream);
		randomRotSpeedSign = ByteConvert.readBool(stream, nifVer);
		randomInitialAxis = ByteConvert.readBool(stream, nifVer);
		initialAxis = new NifVector3(stream);

		return success;
	}
}
