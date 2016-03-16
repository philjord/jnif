package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiAutoNormalParticles extends NiParticles
{
	/**
	 <niobject name="NiAutoNormalParticles" abstract="0" inherit="NiParticles">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}