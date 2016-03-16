package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class hkResponseType
{
	/**
	 * 
	 */
	public static final int RESPONSE_INVALID = 0;// Invalid Response</option>

	public static final int RESPONSE_SIMPLE_CONTACT = 1;// Do normal collision resolution</option>

	public static final int RESPONSE_REPORTING = 2;// No collision resolution is performed but listeners are called

	public static final int RESPONSE_NONE = 3;// Do nothing, ignore all the results.</option>

	public byte type;

	public hkResponseType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readByte(stream);
	}

}
