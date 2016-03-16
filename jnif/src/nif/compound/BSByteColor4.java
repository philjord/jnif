package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSByteColor4
{
	public float r;

	public float g;

	public float b;

	public float a;

	public BSByteColor4(ByteBuffer stream) throws IOException
	{
		r = ByteConvert.readUnsignedByte(stream) / 255f;
		g = ByteConvert.readUnsignedByte(stream) / 255f;
		b = ByteConvert.readUnsignedByte(stream) / 255f;
		a = ByteConvert.readUnsignedByte(stream) / 255f;
	}

	public String toString()
	{
		return "BSByteColor4: r" + r + " b" + b + " g" + g + " a" + a;

	}
}
