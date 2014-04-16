package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class SortingMode
{
	/**	
	 */
	public static final int SORTING_INHERIT = 0;// Inherit

	public static final int SORTING_OFF = 1;// Disable

	public int mode;

	public SortingMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}
}
