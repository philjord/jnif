package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class ApplyMode
{
	/**
	 * An unsigned 32-bit integer, describing the apply mode of a texture.
	 */

	public static final int APPLY_REPLACE = 0;// Replaces existing color

	public static final int APPLY_DECAL = 1;// For placing images on the object like stickers

	public static final int APPLY_MODULATE = 2;// Modulates existing color. (Default)

	public static final int APPLY_HILIGHT = 3;// PS2 Only. Function Unknown.

	public static final int APPLY_HILIGHT2 = 4;// PS2 Only. Function Unknown.

	public int applyMode;

	public ApplyMode(InputStream stream) throws IOException
	{
		applyMode = ByteConvert.readInt(stream);
	}

}
