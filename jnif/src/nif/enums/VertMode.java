package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class VertMode
{
	/**
	 * An unsigned 32-bit integer, which describes how to apply vertex colors.
	 */
	public static final int VERT_MODE_SRC_IGNORE = 0;// Source Ignore.</option>

	public static final int VERT_MODE_SRC_EMISSIVE = 1;// Source Emissive.</option>

	public static final int VERT_MODE_SRC_AMB_DIF = 2;// Source Ambient/Diffuse. (Default)

	public int mode;

	public VertMode(ByteBuffer stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
