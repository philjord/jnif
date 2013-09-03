package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiNode;

public class NiMeshPSysData extends NiPSysData
{
	/**
	 <niobject name="NiMeshPSysData" abstract="0" inherit="NiPSysData" ver1="10.1.0.0">

	 Particle meshes data.
	 
	 <add name="Unknown Int 2" type="uint" ver1="10.2.0.0">Unknown. Possible vertex count but probably not.</add>
	 <add name="Unknown Byte 3" type="byte" default="0" ver1="10.2.0.0">Unknown. 0?</add>
	 <add name="Num Unknown Ints 1" type="uint" ver1="10.2.0.0">Unknown.</add>
	 <add name="Unknown Ints 1" type="uint" ver1="10.2.0.0" arr1="Num Unknown Ints 1">Unknown integers</add>
	 <add name="Unknown Node" type="Ref" template="NiNode">Unknown NiNode.</add>
	 </niobject>
	 */

	public int unknownInt3;

	public byte unknownByte3;

	public int numUnknownInts1;

	public int[] unknownInts1;

	public NifRef unknownLink2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt3 = ByteConvert.readInt(stream);
		unknownByte3 = ByteConvert.readByte(stream);
		numUnknownInts1 = ByteConvert.readInt(stream);
		unknownInts1 = new int[numUnknownInts1];
		for (int i = 0; i < numUnknownInts1; i++)
		{
			unknownInts1[i] = ByteConvert.readInt(stream);
		}
		unknownLink2 = new NifRef(NiNode.class, stream);

		return success;
	}

}
