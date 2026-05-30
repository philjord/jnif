package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.tools.FP16;

public class BSHalfFloatColor4
{

	public float r;

	public float g;

	public float b;

	public float a;

	public BSHalfFloatColor4(ByteBuffer stream) throws IOException
	{
		r = FP16.toFloat(ByteConvert.readShort(stream));
		g = FP16.toFloat(ByteConvert.readShort(stream));
		b = FP16.toFloat(ByteConvert.readShort(stream));
		a = FP16.toFloat(ByteConvert.readShort(stream));
	}

	public String toString()
	{
		return "BSHalfFloatColor4: r" + r + " b" + b + " g" + g + " a" + a;

	}
}
