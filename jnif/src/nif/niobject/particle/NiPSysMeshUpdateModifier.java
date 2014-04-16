package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;

public class NiPSysMeshUpdateModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysMeshUpdateModifier" abstract="0" inherit="NiPSysModifier" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Num Meshes" type="uint">The number of object references that follow.</add>
	 <add name="Meshes" type="Ref" template="NiAVObject" arr1="Num Meshes">Group of target NiNodes or NiTriShapes?</add>
	 </niobject>
	 */

	public int numMeshes;

	public NifRef[] meshes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_VER == NifVer.VER_20_3_0_9)
		{
			ByteConvert.readBytes(1, stream);
		}
		
		numMeshes = ByteConvert.readInt(stream);
		meshes = new NifRef[numMeshes];
		for (int i = 0; i < numMeshes; i++)
		{
			meshes[i] = new NifRef(NiAVObject.class, stream);
		}

		return success;
	}
}
