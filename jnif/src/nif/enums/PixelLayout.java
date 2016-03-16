package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class PixelLayout
{
	/**
	 * An unsigned 32-bit integer, describing the color depth of a texture.
	 */
	public static final int PIX_LAY_PALETTISED = 0;// Texture is in 8-bit paletized format.</option>

	public static final int PIX_LAY_HIGH_COLOR_16 = 1;// Texture is in 16-bit high color format.</option>

	public static final int PIX_LAY_TRUE_COLOR_32 = 2;// Texture is in 32-bit true color format.</option>

	public static final int PIX_LAY_COMPRESSED = 3;// Texture is compressed.</option>

	public static final int PIX_LAY_BUMPMAP = 4;// Texture is a grayscale bump map.</option>

	public static final int PIX_LAY_PALETTISED_4 = 5;// Texture is in 4-bit paletized format.</option>

	public static final int PIX_LAY_DEFAULT = 6;// Use default setting.</option>

	public int pixelLayout; // uint

	public PixelLayout(ByteBuffer stream) throws IOException
	{
		pixelLayout = ByteConvert.readInt(stream);
	}
}
