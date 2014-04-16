package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class CycleType
{
	/**
	 * The animation cyle behavior.
	 */
	public static final int CYCLE_LOOP = 0;// Loop

	public static final int CYCLE_REVERSE = 1;// Reverse

	public static final int CYCLE_CLAMP = 2;// Clamp

	public int type;

	public CycleType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
