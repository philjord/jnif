package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.bs.BSbhkNPObject;

public class bhkRagdollSystem extends BSbhkNPObject
{

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}
