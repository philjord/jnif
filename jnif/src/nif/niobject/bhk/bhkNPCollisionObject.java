package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class bhkNPCollisionObject extends bhkCollisionObject
{

	public int UnknownInt2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownInt2 = ByteConvert.readInt(stream);

		return success;
	}
}
