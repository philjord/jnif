package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifTBC
{
	/**
	 <compound name="TBC" nifskopetype="vector3">

	 Tension, bias, continuity.
	 
	 <add name="t" type="float">Tension.</add>
	 <add name="b" type="float">Bias.</add>
	 <add name="c" type="float">Continuity.</add>
	 </compound>
	 */

	public float t;

	public float b;

	public float c;

	public NifTBC(ByteBuffer stream) throws IOException
	{
		t = ByteConvert.readFloat(stream);
		b = ByteConvert.readFloat(stream);
		c = ByteConvert.readFloat(stream);
	}
}
