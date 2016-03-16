package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;

public class NiParticleMeshModifier extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiParticleMeshModifier" abstract="0" inherit="NiParticleModifier" ver1="4.1.0.12">

	 Unknown.
	 
	 <add name="Num Particle Meshes" type="uint">
	 The number of particle mesh references that follow.
	 </add>
	 <add name="Particle Meshes" arr1="Num Particle Meshes" type="Ref" template="NiAVObject">Links to nodes of particle meshes?</add>
	 </niobject>		
	 */

	public int numParticleMeshes;

	public NifRef[] particleMeshes;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numParticleMeshes = ByteConvert.readInt(stream);
		particleMeshes = new NifRef[numParticleMeshes];
		for (int i = 0; i < numParticleMeshes; i++)
		{
			particleMeshes[i] = new NifRef(NiAVObject.class, stream);
		}

		return success;
	}
}