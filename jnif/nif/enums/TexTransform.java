package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class TexTransform
{
	/**
	 * Determines how a NiTextureTransformController animates the UV coordinates.
	 */
	public static final int TT_TRANSLATE_U = 0;// Means this controller moves the U texture cooridnates.

	public static final int TT_TRANSLATE_V = 1;// Means this controller moves the V texture cooridnates.

	public static final int TT_ROTATE = 2;// Means this controller roates the UV texture cooridnates.

	public static final int TT_SCALE_U = 3;// Means this controller scales the U texture cooridnates.

	public static final int TT_SCALE_V = 4;// Means this controller scales the V texture cooridnates.

	public int transform;

	public TexTransform(InputStream stream) throws IOException
	{
		transform = ByteConvert.readInt(stream);
	}

}
