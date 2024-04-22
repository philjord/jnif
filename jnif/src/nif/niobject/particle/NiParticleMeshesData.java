package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;

public class NiParticleMeshesData extends NiRotatingParticlesData
{
	/**
	<niobject name="NiParticleMeshesData" inherit="NiRotatingParticlesData" module="NiLegacy" until="V10_0_1_0">
        LEGACY (pre-10.1). Particle meshes data.
        <field name="Container Node" type="Ref" template="NiNode" />
    </niobject>
	 */

	public NifRef ContainerNode;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		ContainerNode = new NifRef(NiAVObject.class, stream);

		return success;
	}
}