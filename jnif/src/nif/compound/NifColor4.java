package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifColor4
{
	/**
	 <compound name="Color4" niflibtype="Color4" nifskopetype="color4">

	 A color with alpha (red, green, blue, alpha).
	 
	 <add name="r" type="float">Red component.</add>
	 <add name="g" type="float">Green component.</add>
	 <add name="b" type="float">Blue component.</add>
	 <add name="a" type="float">Alpha.</add>
	 </compound>
	 */

	public float r;

	public float g;

	public float b;

	public float a;

	public NifColor4(ByteBuffer stream) throws IOException
	{
		r = ByteConvert.readFloat(stream);
		g = ByteConvert.readFloat(stream);
		b = ByteConvert.readFloat(stream);
		a = ByteConvert.readFloat(stream);
	}

	public String toString()
	{
		return "NifColor4: r" + r + " b" + b + " g" + g + " a" + a;

	}
}
