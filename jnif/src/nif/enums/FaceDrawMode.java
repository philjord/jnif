package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class FaceDrawMode
{
	/**
	 * This enum lists the different face culling options.
	 */

	public static final int DRAW_CCW_OR_BOTH = 0;// use application defaults?</option>

	public static final int DRAW_CCW = 1;// Draw counter clock wise faces, cull clock wise faces. This is the default for most (all?) Nif Games so

	// far.

	public static final int DRAW_CW = 2;// Draw clock wise faces, cull counter clock wise faces. This will flip all the faces.

	public static final int DRAW_BOTH = 3;// Draw double sided faces.</option>

	public int mode;

	public FaceDrawMode(ByteBuffer stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
