package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.bs.BSbhkNPObject;

public class bhkPhysicsSystem extends BSbhkNPObject
{

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}
