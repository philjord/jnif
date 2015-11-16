package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSHalfFloatTexCoord2
{
	public float u;

	public float v;

	public BSHalfFloatTexCoord2(InputStream stream) throws IOException
	{
		u = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		v = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
	}
}
