package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class KeyType
{
	/**
	 * The type of animation interpolation (blending) that will be used on the associated key frames.        
	 */

	public static final int LINEAR_KEY = 1; // Use linear interpolation. 

	public static final int QUADRATIC_KEY = 2; // 	Use quadratic interpolation.  Forward and back tangents will be stored.

	public static final int TBC_KEY = 3; // Use Tension Bias Continuity interpolation.  Tension, bias, and continuity will be stored.

	public static final int XYZ_ROTATION_KEY = 4; // For use only with rotation data.  Separate X, Y, and Z keys will be stored instead of using quaternions.

	public static final int UNKNOWN_KEY = 5; // Unknown.  Step function? 

	public int type = LINEAR_KEY;

	public KeyType()
	{
	}

	public KeyType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
