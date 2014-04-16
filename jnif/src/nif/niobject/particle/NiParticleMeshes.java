package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public class NiParticleMeshes extends NiParticles
{
	/**
	 <niobject name="NiParticleMeshes" abstract="0" inherit="NiParticles" ver1="4.1.0.12">
	 Mesh particle node?
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}