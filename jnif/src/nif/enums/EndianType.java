package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class EndianType
{
	/**
	 */
	public static final int ENDIAN_BIG = 0;// The numbers are stored in big endian format, such as those used by PowerPC Mac processors.

	public static final int ENDIAN_LITTLE = 1;// The numbers are stored in little endian format, such as those used by Intel and AMD x86 processors.

	public int type;

	public EndianType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
