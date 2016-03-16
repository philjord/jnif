package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiGeometry;

public class NiParticles extends NiGeometry
{
	/**
	 <niobject name="NiParticles" abstract="0" inherit="NiGeometry">
	 Generic particle system node.
	 </niobject>
	 */
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}