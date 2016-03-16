package nif.basic;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifStringOffset
{
	public int offset = -1;

	public NifStringOffset(ByteBuffer stream) throws IOException
	{
		offset = ByteConvert.readInt(stream);
	}
}
