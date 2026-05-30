package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.tools.FP16;

public class BSHalfFloatVector3 extends NifVector3
{
	public BSHalfFloatVector3(ByteBuffer stream) throws IOException
	{
		super();
		x = FP16.toFloat(ByteConvert.readShort(stream));
		y = FP16.toFloat(ByteConvert.readShort(stream));
		z = FP16.toFloat(ByteConvert.readShort(stream));

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}

}
