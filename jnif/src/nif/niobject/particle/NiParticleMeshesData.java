package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;

public class NiParticleMeshesData extends NiRotatingParticlesData
{
	/**
	 
	 <niobject name="NiParticleMeshesData" abstract="0" inherit="NiRotatingParticlesData" ver1="4.1.0.12">

	 Particle meshes data.
	 
	 <add name="Unknown Link 2" type="Ref" template="NiAVObject">Refers to the mesh that makes up a particle?</add>
	 </niobject>
	 */

	public NifRef unknownLink2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownLink2 = new NifRef(NiAVObject.class, stream);

		return success;
	}
}