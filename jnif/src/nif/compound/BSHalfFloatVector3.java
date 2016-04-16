package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.tools.MiniFloat;

public class BSHalfFloatVector3 extends NifVector3
{
	public BSHalfFloatVector3(ByteBuffer stream) throws IOException
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
