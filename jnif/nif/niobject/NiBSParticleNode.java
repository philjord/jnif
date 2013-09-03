package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiBSParticleNode extends NiNode
{
	/**
	 
	 <niobject name="NiBSParticleNode" abstract="0" inherit="NiNode">
	 Unknown.
	 </niobject>	 
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}