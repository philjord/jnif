package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifTriangle
{
	/**
	 <compound name="Triangle" niflibtype="Triangle" nifskopetype="triangle">

	 List of three vertex indices.
	 
	 <add name="v1" type="ushort">First vertex index.</add>
	 <add name="v2" type="ushort">Second vertex index.</add>
	 <add name="v3" type="ushort">Third vertex index.</add>
	 </compound>
	 */

	public int v1;

	public int v2;

	public int v3;

	public NifTriangle(ByteBuffer stream) throws IOException
	{
		/*
		 * v1 = ByteConvert.readShort(stream);
		v1 += v1 < 0 ? 65536 : 0;
		v2 = ByteConvert.readShort(stream);
		v2 += v2 < 0 ? 65536 : 0;
		v3 = ByteConvert.readShort(stream);
		v3 += v3 < 0 ? 65536 : 0;*/
		v1 = ByteConvert.readUnsignedShort(stream);	
		v2 = ByteConvert.readUnsignedShort(stream);
		v3 = ByteConvert.readUnsignedShort(stream);
		
	}

	public String toString()
	{
		return "[NifTriangle]" + v1 + " " + v2 + " " + v3;
	}
}
