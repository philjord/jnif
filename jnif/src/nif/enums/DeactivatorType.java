package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class DeactivatorType
{
	/**
	 * The motion system. 4 (Box) is used for everything movable. 7 (Keyframed) is used on statics and animated stuff.
	 */

	public static final int DEACTIVATOR_INVALID = 0;// Invalid</option>

	public static final int DEACTIVATOR_NEVER = 1;// This will force the rigid body to never deactivate.

	public static final int DEACTIVATOR_SPATIAL = 2;// Tells Havok to use a spatial deactivation scheme.

	// This makes use of high and low frequencies of positional motion to determine when deactivation should occur.

	public int type;

	public DeactivatorType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readByte(stream);
	}
}
