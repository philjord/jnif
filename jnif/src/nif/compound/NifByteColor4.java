package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifByteColor4
{
	/**
	 * <compound name="ByteColor4">
	 <add name="r" type="byte">Red color component.</add>
	 <add name="g" type="byte">Green color component.</add>
	 <add name="b" type="byte">Blue color component.</add>
	 <add name="a" type="byte">Alpha color component.</add>
	 </compound> 
	 */

	public byte r;

	public byte g;

	public byte b;

	public byte a;

	public NifByteColor4(InputStream stream) throws IOException
	{
		r = ByteConvert.readByte(stream);
		g = ByteConvert.readByte(stream);
		b = ByteConvert.readByte(stream);
		a = ByteConvert.readByte(stream);
	}

	public String toString()
	{
		return "[NPByteColor4]" + r + " " + g + " " + b;
	}
}
