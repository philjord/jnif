package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class ChannelType
{
	/**
	 * 
	 */
	public static final int CHNL_RED = 0;//Red</option>

	public static final int CHNL_GREEN = 1;//Green</option>

	public static final int CHNL_BLUE = 2;//Blue</option>

	public static final int CHNL_ALPHA = 3;//Alpha</option>

	public static final int CHNL_COMPRESSED = 4;//Compressed</option>

	public static final int CHNL_INDEX = 5;//Index</option>

	public static final int CHNL_EMPTY = 6;//Empty</option>

	public int type;

	public ChannelType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
