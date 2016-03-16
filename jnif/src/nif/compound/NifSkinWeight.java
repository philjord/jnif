package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifSkinWeight
{
	/**
	 <compound name="SkinWeight">

	 A weighted vertex.
	 
	 <add name="Index" type="ushort">The vertex index, in the mesh.</add>
	 <add name="Weight" type="float">The vertex weight - between 0.0 and 1.0</add>
	 </compound>
	 */

	public short index;

	public float weight;

	public NifSkinWeight(ByteBuffer stream) throws IOException
	{
		index = ByteConvert.readShort(stream);
		weight = ByteConvert.readFloat(stream);
	}
}
