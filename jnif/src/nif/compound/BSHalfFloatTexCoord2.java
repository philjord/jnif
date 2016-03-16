package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSHalfFloatTexCoord2
{
	public float u;

	public float v;

	public BSHalfFloatTexCoord2(ByteBuffer stream) throws IOException
	{
		u = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		v = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
	}

	public String toString()
	{
		return "[BSHalfFloatTexCoord2] " + u + " " + v;
	}
}
