package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSClothExtraData extends BSExtraData
{
	public int NumBytes;
	public byte[] Data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumBytes = ByteConvert.readInt(stream);
		Data = ByteConvert.readBytes(NumBytes, stream);
		return success;
	}
}
