package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSEyeCenterExtraData extends NiExtraData
{
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		return success;
	}
}
