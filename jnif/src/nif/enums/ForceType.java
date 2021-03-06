package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class ForceType
{
	/**
	 * The type of force? May be more valid values.
	 */
	public static final int FORCE_PLANAR = 0;

	public static final int FORCE_SPHERICAL = 1;

	public static final int FORCE_UNKNOWN = 2;

	public int type;

	public ForceType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
