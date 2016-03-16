package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class ConsistencyType
{
	/**
	 * Used by NiGeometryData to control the volatility of the mesh. While they appear to be flags they behave as an enum.
	 */
	public static final int CT_MUTABLE = 0;// Mutable Mesh</option>

	public static final int CT_STATIC = 16384;// Static Mesh</option>

	public static final int CT_VOLATILE = 32768;// Volatile Mesh</option>

	public short type;

	public ConsistencyType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readShort(stream);
	}

}
