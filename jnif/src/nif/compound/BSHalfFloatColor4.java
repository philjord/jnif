package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSHalfFloatColor4
{

	public float r;

	public float g;

	public float b;

	public float a;

	public BSHalfFloatColor4(ByteBuffer stream) throws IOException
	{
		r = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		g = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		b = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		a = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
	}

	public String toString()
	{
		return "BSHalfFloatColor4: r" + r + " b" + b + " g" + g + " a" + a;

	}
}
