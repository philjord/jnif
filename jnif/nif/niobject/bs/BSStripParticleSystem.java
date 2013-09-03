package nif.niobject.bs;

import java.io.InputStream;

import nif.NifVer;
import nif.niobject.particle.NiParticleSystem;

public class BSStripParticleSystem extends NiParticleSystem
{
	/**
	 <niobject name="BSStripParticleSystem" inherit="NiParticleSystem" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific (mesh?) Particle System.
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
