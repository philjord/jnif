package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public class NiRotatingParticles extends NiParticles
{
	/**
	 <niobject name="NiRotatingParticles" abstract="0" inherit="NiParticles">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}