package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiObject;

public class BSPositionData extends NiObject
{
	public String name;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		// this is done in NiExtraData for example
		name = ByteConvert.readIndexString(stream, nifVer);
		
		
		return success;
	}
}