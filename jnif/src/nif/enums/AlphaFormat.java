package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class AlphaFormat
{
	/**
	 * An unsigned 32-bit integer, describing how transparency is handled in a texture.
	 */

	public static final int ALPHA_NONE = 0;// No alpha blending; the texture is fully opaque.

	public static final int ALPHA_BINARY = 1;// Texture is either fully transparent or fully opaque. There are no partially

	// transparent areas.

	public static final int ALPHA_SMOOTH = 2;// Full range of alpha values can be used from fully transparent to fully opaque

	// including all partially transparent values in between.

	public static final int ALPHA_DEFAULT = 3;// Use default setting.

	public int format;

	public AlphaFormat(ByteBuffer stream) throws IOException
	{
		format = ByteConvert.readInt(stream);
	}
}
