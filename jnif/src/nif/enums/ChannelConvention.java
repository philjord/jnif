package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class ChannelConvention
{
	/**
	 * 
	 */
	public static final int CC_FIXED = 0;//Fixed</option>

	public static final int CC_INDEX = 3;//Palettized</option>

	public static final int CC_COMPRESSED = 4;//Compressed</option>

	public static final int CC_EMPTY = 5;//Empty</option>

	public int convention;

	public ChannelConvention(ByteBuffer stream) throws IOException
	{
		convention = ByteConvert.readInt(stream);
	}

}
