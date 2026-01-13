package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSByteVector4 extends NifVector4
{

	public BSByteVector4(ByteBuffer stream) throws IOException
	{
		super();
		x = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		y = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		z = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		w = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		
		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z) || Float.isNaN(w))
		{
			x = 0;
			y = 0;
			z = 0;
			w = 0;
		}
	}
	
	@Override
	public String toString()
	{
		return "[BSByteVector4] " + x + " " + y + " " + z;
	}
}
