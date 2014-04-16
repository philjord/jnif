package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class TexFilterMode
{
	/**
	 * Specifies the availiable texture filter modes. That is, the way pixels within a texture are blended together when textures are displayed on the
	 * screen at a size other than their original dimentions.
	 */
	public static final int FILTER_NEAREST = 0;// Simply uses the nearest pixel. Very grainy.</option>

	public static final int FILTER_BILERP = 1;// Uses bilinear filtering.</option>

	public static final int FILTER_TRILERP = 2;// Uses trilinear filtering.</option>

	public static final int FILTER_NEAREST_MIPNEAREST = 3;// Uses the nearest pixel from the mipmap that is closest to the display size.

	public static final int FILTER_NEAREST_MIPLERP = 4;// Blends the two mipmaps closest to the display size linearly, and then uses the nearest

	// pixel from the result.

	public static final int FILTER_BILERP_MIPNEAREST = 5;// Uses the closest mipmap to the display size and then uses bilinear filtering on the

	// pixels.

	public int mode;

	public TexFilterMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
