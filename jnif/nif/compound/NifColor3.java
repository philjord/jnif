package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifColor3
{
	/**
	 A color without alpha (red, green, blue).
	 
	 <add name="r" type="float">Red color component.</add>
	 <add name="g" type="float">Green color component.</add>
	 <add name="b" type="float">Blue color component.</add>
	 */

	public float r;

	public float g;

	public float b;

	public NifColor3(InputStream stream) throws IOException
	{
		r = ByteConvert.readFloat(stream);
		g = ByteConvert.readFloat(stream);
		b = ByteConvert.readFloat(stream);
	}

	public String toString()
	{
		return "[NPColor3]" + r + " " + g + " " + b;
	}
}
