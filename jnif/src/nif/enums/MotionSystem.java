package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class MotionSystem
{
	/**
	 * The motion system. 4 (Box) is used for everything movable. 7 (Keyframed) is used on statics and animated stuff.
	 */

	public static final int MO_SYS_INVALID = 0;// Invalid</option>

	public static final int MO_SYS_DYNAMIC = 1;// A fully-simulated, movable rigid body. At construction time the engine checks the input inertia and

	// selects MO_SYS_SPHERE_INERTIA or MO_SYS_BOX_INERTIA as appropriate.

	public static final int MO_SYS_SPHERE = 2;// Simulation is performed using a sphere inertia tensor.

	public static final int MO_SYS_SPHERE_INERTIA = 3;// This is the same as MO_SYS_SPHERE_INERTIA, except that simulation of the rigid body is

	// "softened".

	public static final int MO_SYS_BOX = 4;// Simulation is performed using a box inertia tensor.

	public static final int MO_SYS_BOX_STABILIZED = 5;// This is the same as MO_SYS_BOX_INERTIA, except that simulation of the rigid body is

	// "softened".

	public static final int MO_SYS_KEYFRAMED = 6;// Simulation is not performed as a normal rigid body. The keyframed rigid body has an infinite

	// mass when viewed by the rest of the system. (used for creatures)

	public static final int MO_SYS_FIXED = 7;// This motion type is used for the static elements of a game scene, e.g. the landscape. Faster than

	// MO_SYS_KEYFRAMED at velocity 0. (used for weapons)

	public static final int MO_SYS_THIN_BOX = 8;// A box inertia motion which is optimized for thin boxes and has less stability problems

	public static final int MO_SYS_CHARACTER = 9;// A specialized motion used for character controllers

	public int system;

	public MotionSystem(ByteBuffer stream) throws IOException
	{
		system = ByteConvert.readByte(stream);
	}
}
