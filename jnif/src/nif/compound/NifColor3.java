package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

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

	public NifColor3(ByteBuffer stream) throws IOException
	{
		r = ByteConvert.readFloat(stream);
		g = ByteConvert.readFloat(stream);
		b = ByteConvert.readFloat(stream);
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o instanceof NifColor3)
		{
			NifColor3 o2 = (NifColor3) o;
			return this.r == o2.r && this.g == o2.g && this.b == o2.b;
		}
		return false;
	}

	public String toString()
	{
		return "[NPColor3]" + r + " " + g + " " + b;
	}
}
