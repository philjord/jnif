package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public class NiMeshParticleSystem extends NiParticleSystem
{
	/**
	 <niobject name="NiMeshParticleSystem" abstract="0" inherit="NiParticleSystem" ver1="10.2.0.0">
	 Particle system.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}