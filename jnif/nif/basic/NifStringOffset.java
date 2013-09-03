package nif.basic;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifStringOffset
{
	public int offset = -1;

	public NifStringOffset(InputStream stream) throws IOException
	{
		offset = ByteConvert.readInt(stream);
	}
}
