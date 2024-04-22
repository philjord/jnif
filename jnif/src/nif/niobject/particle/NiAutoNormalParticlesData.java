package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiAutoNormalParticlesData extends NiParticlesData
{
	/**
	<niobject name="NiAutoNormalParticlesData" inherit="NiParticlesData" module="NiLegacy" until="V10_0_1_0">
        Particle system data object (with automatic normals?).
    </niobject>
	 */

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}