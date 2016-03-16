package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysGravityFieldModifier extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysGravityFieldModifier" inherit="NiPSysFieldModifier">

	 Particle system modifier, used for controlling the particle velocity in gravity field.
	 
	 <add name="Direction" type="Vector3">Direction of the particle velocity</add>
	 </niobject>
	 */

	public NifVector3 direction;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		direction = new NifVector3(stream);

		return success;
	}
}
