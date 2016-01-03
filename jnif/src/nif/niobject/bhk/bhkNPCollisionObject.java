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
		//hknpScaledConvexShape ?what is jonwd7 talking about?
		//OK only two false positives for that list of 5590. It was because Convex was in the name.   So there aren't any other types of Convex hknp 
		//and hknpScaledConvexShape is only used alongside the polytope shape, on some _OC files 

		return success;
	}
}
