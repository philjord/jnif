package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSByteVector3 extends NifVector3
{

	public BSByteVector3(InputStream stream) throws IOException
	{
		super();
		x = ByteConvert.readByte(stream) / 127f;
		y = ByteConvert.readByte(stream) / 127f;
		z = ByteConvert.readByte(stream) / 127f;

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}
}
