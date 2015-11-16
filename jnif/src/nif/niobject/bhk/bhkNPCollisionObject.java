package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.NiCollisionObject;

public class bhkNPCollisionObject extends NiCollisionObject
{
	 
 

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
	 

		// needs to read 10 bytes
		
		return success;
	}
}
