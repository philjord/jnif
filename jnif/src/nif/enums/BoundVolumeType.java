package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BoundVolumeType
{
	/**
	 * 
	 */
	public static final int BASE_BV = -1;// Default</option>

	public static final int SPHERE_BV = 0;// Sphere</option>

	public static final int BOX_BV = 1;// Box</option>

	public static final int CAPSULE_BV = 2;// Capsule</option>

	public static final int UNION_BV = 4;// Union</option>

	public static final int HALFSPACE_BV = 5;// Half Space</option>

	public int type;

	public BoundVolumeType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
