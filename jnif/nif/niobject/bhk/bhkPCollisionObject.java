package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class bhkPCollisionObject extends bhkNiCollisionObject
{
	/**
	 
	 <niobject name="bhkPCollisionObject" abstract="0" inherit="bhkNiCollisionObject" ver1="20.0.0.5">
	 Unknown.
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}