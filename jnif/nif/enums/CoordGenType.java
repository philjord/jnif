package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class CoordGenType
{
	/**
	 * Determines the way that UV texture coordinates are generated.
	 */
	public static final int CG_WORLD_PARALLEL = 0;// Use plannar mapping.</option>

	public static final int CG_WORLD_PERSPECTIVE = 1;// Use perspective mapping.</option>

	public static final int CG_SPHERE_MAP = 2;// Use spherical mapping.</option>

	public static final int CG_SPECULAR_CUBE_MAP = 3;// Use specular cube mapping.</option>

	public static final int CG_DIFFUSE_CUBE_MAP = 4;// Use Diffuse cube mapping.</option>

	public int type;

	public CoordGenType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
