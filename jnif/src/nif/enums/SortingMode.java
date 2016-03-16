package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SortingMode
{
	/**	
	 */
	public static final int SORTING_INHERIT = 0;// Inherit

	public static final int SORTING_OFF = 1;// Disable

	public int mode;

	public SortingMode(ByteBuffer stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}
}
