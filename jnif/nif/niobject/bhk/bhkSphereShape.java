package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class bhkSphereShape extends bhkConvexShape
{
	/**
	 <niobject name="bhkSphereShape" abstract="0" inherit="bhkConvexShape" ver1="20.0.0.5">
	 A sphere.

	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}