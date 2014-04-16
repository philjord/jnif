package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class ImageType
{
	/**
	 * Determines how the raw image data is stored in NiRawImageData.               
	 */

	public static final int RGB = 1; // Colors store red, blue, and green components.

	public static final int RGBA = 2; // Colors store red, blue, green, and alpha components.

	public int type = RGB;

	public ImageType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
