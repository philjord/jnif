package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class CollisionMode
{
	/**	
	 */
	public static final int CM_USE_OBB = 0;// Use Bounding Box</option>

	public static final int CM_USE_TRI = 1;// Use Triangles</option>

	public static final int CM_USE_ABV = 2;// Use Alternate Bounding Volumes</option>

	public static final int CM_NOTEST = 3;// No Test</option>

	public static final int CM_USE_NIBOUND = 4;// Use NiBound</option>

	public int mode;

	public CollisionMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}
}
