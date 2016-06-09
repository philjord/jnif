package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiNode;

public class NiBSParticleNode extends NiNode
{
	/**
	 
	 <niobject name="NiBSParticleNode" abstract="0" inherit="NiNode">
	 Unknown.
	 </niobject>	 
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}