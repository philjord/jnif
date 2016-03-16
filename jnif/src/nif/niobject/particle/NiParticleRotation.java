package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiParticleRotation extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiParticleRotation" abstract="0" inherit="NiParticleModifier">

	 Unknown.
	 
	 <add name="Random Initial Axis?" type="byte">Unknown.</add>
	 <add name="Initial Axis?" type="Vector3">Unknown.</add>
	 <add name="Rotation Speed?" type="float">Unknown.</add>
	 </niobject>
	 */

	public byte randomInitialAxis;

	public NifVector3 initialAxis;

	public float rotationSpeed;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		randomInitialAxis = ByteConvert.readByte(stream);
		initialAxis = new NifVector3(stream);
		rotationSpeed = ByteConvert.readFloat(stream);

		return success;
	}
}