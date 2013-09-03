package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class StencilAction
{
	/**
	 * This enum defines the various actions used in conjunction with the stencil buffer. For a detailed description of the individual options please
	 * refer to the OpenGL docs.
	 */
	public static final int ACTION_KEEP = 0;//

	public static final int ACTION_ZERO = 1;//

	public static final int ACTION_REPLACE = 2;//

	public static final int ACTION_INCREMENT = 3;//

	public static final int ACTION_DECREMENT = 4;//

	public static final int ACTION_INVERT = 5;//

	public int action;

	public StencilAction(InputStream stream) throws IOException
	{
		action = ByteConvert.readInt(stream);
	}

}
