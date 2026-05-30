package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.tools.FP16;

public class BSHalfFloatTexCoord2
{
	public float u;

	public float v;

	public BSHalfFloatTexCoord2(ByteBuffer stream) throws IOException
	{
		u = FP16.toFloat(ByteConvert.readShort(stream));
		v = FP16.toFloat(ByteConvert.readShort(stream));
	}

	public String toString()
	{
		return "[BSHalfFloatTexCoord2] " + u + " " + v;
	}
}
