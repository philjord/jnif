package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class MipMapFormat
{
	/**
	 * An unsigned 32-bit integer, describing how mipmaps are handled in a texture.
	 */
	public static final int MIP_FMT_NO = 0;// Texture does not use mip maps.

	public static final int MIP_FMT_YES = 0;// Texture uses mip maps.

	public static final int MIP_FMT_DEFAULT = 0;// Use default setting.

	public int format; // uint

	public MipMapFormat(ByteBuffer stream) throws IOException
	{
		format = ByteConvert.readInt(stream);
	}
}
