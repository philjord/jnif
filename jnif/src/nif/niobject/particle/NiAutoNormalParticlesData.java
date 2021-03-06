package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiAutoNormalParticlesData extends NiParticlesData
{
	/**
	 <niobject name="NiAutoNormalParticlesData" abstract="0" inherit="NiParticlesData" ver2="4.2.2.0">

	 Particle system data object (with automatic normals?).
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}