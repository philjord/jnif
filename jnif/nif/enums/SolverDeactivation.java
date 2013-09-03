package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class SolverDeactivation
{
	/**
	 * A list of possible solver deactivation settings. This value defines how the solver deactivates objects. The solver works on a per object basis.
	 * Note: Solver deactivation does not save CPU, but reduces creeping of movable objects in a pile quite dramatically.
	 */

	public static final int SOLVER_DEACTIVATION_INVALID = 0;// Invalid</option>

	public static final int SOLVER_DEACTIVATION_OFF = 1;// No solver deactivation</option>

	public static final int SOLVER_DEACTIVATION_LOW = 2;// Very conservative deactivation, typically no visible artifacts.

	public static final int SOLVER_DEACTIVATION_MEDIUM = 3;// Normal deactivation, no serious visible artifacts in most cases

	public static final int SOLVER_DEACTIVATION_HIGH = 4;// Fast deactivation, visible artifacts</option>

	public static final int SOLVER_DEACTIVATION_MAX = 5;// Very fast deactivation, visible artifacts</option>

	public int deactivation;

	public SolverDeactivation(InputStream stream) throws IOException
	{
		deactivation = ByteConvert.readByte(stream);
	}
}
