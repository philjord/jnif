package nif;

import java.io.IOException;
import java.io.InputStream;

import nif.niobject.NiObject;

public class UnknownBlock extends NiObject
{
	public String objectType = "";

	public int blockSize = 0;

	public UnknownBlock(String objectType, int blockSize)
	{
		this.objectType = objectType;
		this.blockSize = blockSize;
	}

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		this.nVer = nifVer;
		ByteConvert.readBytes(blockSize, stream);
		return true;
	}
}
