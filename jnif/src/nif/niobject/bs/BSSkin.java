package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.NiObject;

//TODO: does BSSkin itself ever get read? or is it just a collection?
public class BSSkin
{

	public static class Instance extends NiObject
	{
		public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);

			return success;
		}
	}

	public static class BoneData extends NiObject
	{
		public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);

			return success;
		}
	}
}
