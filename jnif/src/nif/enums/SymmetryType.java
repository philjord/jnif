package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SymmetryType
{
	/**
	 * Determines symetry type used by NiPSysBombModifier.

	 */
	public static final int SPHERICAL_SYMMETRY = 0;//Spherical Symmetry.

	public static final int CYLINDRICAL_SYMMETRY = 1;//Cylindrical Symmetry.

	public static final int PLANAR_SYMMETRY = 2;//Planar Symmetry.

	public int type;

	public SymmetryType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
