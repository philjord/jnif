package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class PropagationMode
{
	/**	
	 */
	public static final int PROPAGATE_ON_SUCCESS = 0;// On Success</option>

	public static final int PROPAGATE_ON_FAILURE = 1;// On Failure</option>

	public static final int PROPAGATE_ALWAYS = 2;// Always</option>

	public static final int PROPAGATE_NEVER = 3;// Never</option>

	public int mode;

	public PropagationMode(ByteBuffer stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}
}
