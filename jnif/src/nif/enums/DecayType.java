package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class DecayType
{
	/**
	 * Determines decay function.  Used by NiPSysBombModifier.
	    
	 */
	public static final int DECAY_NONE = 0;//No decay.

	public static final int DECAY_LINEAR = 1;//Linear decay.

	public static final int DECAY_EXPONENTIAL = 2;//Exponential decay.

	public int type;

	public DecayType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
