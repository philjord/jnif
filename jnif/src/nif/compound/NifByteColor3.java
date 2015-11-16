package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifByteColor3
{
	/**
	 * <compound name="ByteColor3">
	 <add name="r" type="byte">Red color component.</add>
	 <add name="g" type="byte">Green color component.</add>
	 <add name="b" type="byte">Blue color component.</add>
	 </compound>
	 */

	public byte r;

	public byte g;

	public byte b;

	public NifByteColor3(InputStream stream) throws IOException
	{
		r = ByteConvert.readByte(stream);
		g = ByteConvert.readByte(stream);
		b = ByteConvert.readByte(stream);
	}

	public String toString()
	{
		return "[NPByteColor3] " + (r & 0xff) + " " + (g & 0xff) + " " + (b & 0xff);
	}
}
