package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysVortexFieldModifier extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysVortexFieldModifier" inherit="NiPSysFieldModifier">

	 Particle system modifier, used for controlling the particle velocity in force field.
	 
	 <add name="Direction" type="Vector3">Direction of the particle velocity</add>
	 </niobject>
	 */

	public NifVector3 direction;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		direction = new NifVector3(stream);

		return success;
	}
}