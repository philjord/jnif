package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import tools.MiniFloat;
import nif.ByteConvert;

public class BSHalfFloatVector3 extends NifVector3
{
	public BSHalfFloatVector3(InputStream stream) throws IOException
	{
		super();
		x = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		y = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		z = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}

}
