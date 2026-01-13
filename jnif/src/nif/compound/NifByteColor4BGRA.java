package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifByteColor4BGRA
{
	public float b;

	public float g;

	public float r;

	public float a;

	public NifByteColor4BGRA(ByteBuffer stream) throws IOException
	{
		b = ByteConvert.readUnsignedByte(stream) / 255f;
		g = ByteConvert.readUnsignedByte(stream) / 255f;
		r = ByteConvert.readUnsignedByte(stream) / 255f;
		a = ByteConvert.readUnsignedByte(stream) / 255f;
	}

	public String toString()
	{
		return "ByteColor4BGRA: b" + b + " g" + g + " r" + r + " a" + a;

	}
}