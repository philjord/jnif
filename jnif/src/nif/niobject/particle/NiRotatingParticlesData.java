package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifQuaternion;

public class NiRotatingParticlesData extends NiParticlesData
{
	/**
	 <niobject name="NiRotatingParticlesData" abstract="0" inherit="NiParticlesData">

	 Rotating particles data object.
	 
	 <add name="Has Rotations 2" type="bool" ver2="4.2.2.0">Is the particle rotation array present?</add>
	 <add name="Rotations 2" type="Quaternion" arr1="Num Vertices" cond="Has Rotations 2 != 0" ver2="4.2.2.0">The individual particle rotations.</add>
	 </niobject>
	 */

	public boolean HasRotations2;

	public NifQuaternion[] Rotations2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			HasRotations2 = ByteConvert.readBool(stream, nifVer);
			if (HasRotations2)
			{
				Rotations2 = new NifQuaternion[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					Rotations2[i] = new NifQuaternion(stream);
				}
			}
		}
		return success;
	}

}