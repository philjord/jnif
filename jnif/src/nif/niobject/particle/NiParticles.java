package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiGeometry;

public class NiParticles extends NiGeometry
{
	/**
	 </niobject>
     <niobject name="NiParticles" inherit="NiGeometry" module="NiMain">
        Generic particle system node.
    </niobject>
	 */
	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}