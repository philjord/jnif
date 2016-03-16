package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;
import nif.niobject.NiNode;

public class BSMasterParticleSystem extends NiNode
{
	/**
	 
	 <niobject name="BSMasterParticleSystem" inherit="NiNode" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific node.
	 
	 <add name="Max Emitter Objects" type="ushort">Unknown</add>
	 <add name="Num Particle Systems" type="int">Unknown</add>
	 <add name="Particle Systems" type="Ref" template="NiAVObject" arr1="Num Particle Systems">Unknown</add>
	 </niobject>
	 */

	public short maxEmitterObjects;

	public int numParticleSystems;

	public NifRef[] particleSystems;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		maxEmitterObjects = ByteConvert.readShort(stream);

		numParticleSystems = ByteConvert.readInt(stream);
		particleSystems = new NifRef[numParticleSystems];
		for (int i = 0; i < numParticleSystems; i++)
		{
			particleSystems[i] = new NifRef(NiAVObject.class, stream);
		}

		return success;
	}
}