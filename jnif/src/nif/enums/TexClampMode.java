package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class TexClampMode
{
	/**
	 * Specifies the available texture clamp modes. That is, the behavior of pixels outside the range of the texture.
	 */
	public static final int CLAMP_S_CLAMP_T = 0;// Clamp in both directions.</option>

	public static final int CLAMP_S_WRAP_T = 1;// Clamp in the S(U) direction but wrap in the T(V) direction.

	public static final int WRAP_S_CLAMP_T = 2;// Wrap in the S(U) direction but clamp in the T(V) direction.

	public static final int WRAP_S_WRAP_T = 3;// Wrap in both directions.</option> 

	public static final int MIRRORED_S_MIRRORED_T = 0xFF00;


	public int mode;

	public TexClampMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
