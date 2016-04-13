package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSByteVector3 extends NifVector3
{

	public BSByteVector3(ByteBuffer stream) throws IOException
	{
		super();
		x = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		y = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
		z = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}
	
	public String toString()
	{
		return "[BSByteVector3] " + x + " " + y + " " + z;
	}
}
