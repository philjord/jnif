package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class MotionQuality
{
	/**
	 * The motion type. Determines quality of motion?
	 */
	public static final int MO_QUAL_INVALID = 0;// Automatically assigned to MO_QUAL_FIXED, MO_QUAL_KEYFRAMED or MO_QUAL_DEBRIS

	public static final int MO_QUAL_FIXED = 1;// Use this for fixed bodies. </option>

	public static final int MO_QUAL_KEYFRAMED = 2;// Use this for moving objects with infinite mass.</option>

	public static final int MO_QUAL_DEBRIS = 3;// Use this for all your debris objects</option>

	public static final int MO_QUAL_MOVING = 4;// Use this for moving bodies, which should not leave the world.

	public static final int MO_QUAL_CRITICAL = 5;// Use this for all objects, which you cannot afford to tunnel through the world at all

	public static final int MO_QUAL_BULLET = 6;// Use this for very fast objects </option>

	public static final int MO_QUAL_USER = 7;// For user.</option>

	public static final int MO_QUAL_CHARACTER = 8;// Use this for rigid body character controllers</option>

	public static final int MO_QUAL_KEYFRAMED_REPORT = 9;// Use this for moving objects with infinite mass which should report contact points and

	// Toi-collisions against all other bodies, including other fixed and keyframed bodies.

	public int quality;

	public MotionQuality(InputStream stream) throws IOException
	{
		quality = ByteConvert.readByte(stream);
	}
}
