package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class bhkPhysicsSystem extends bhkSerializable
{
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}
