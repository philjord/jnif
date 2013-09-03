package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class LightMode
{
	/**
	 * An unsigned 32-bit integer, describing how vertex colors influence lighting.
	 */
	public static final int LIGHT_MODE_EMISSIVE = 0;// Emissive.

	public static final int LIGHT_MODE_EMI_AMB_DIF = 1;// Emissive + Ambient + Diffuse. (Default)

	public int mode;

	public LightMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
