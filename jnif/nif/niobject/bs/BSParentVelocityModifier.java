package nif.niobject.bs;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSParentVelocityModifier extends NiPSysModifier
{
	/**
	 <niobject name="BSParentVelocityModifier" abstract="0" inherit="NiPSysModifier" ver1="20.0.0.5">

	 Particle modifier that adds a blend of object space translation and rotation to particles born in world space.
	 
	 <add name="Damping" type="float">Amount of blending?</add>
	 </niobject>
	 */

	public float damping;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		damping = ByteConvert.readFloat(stream);

		return success;
	}
}
