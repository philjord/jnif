package nif.enums;

import java.io.IOException;
import java.io.InputStream;

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

	public SymmetryType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
