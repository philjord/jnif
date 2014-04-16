package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class bhkConvexShape extends bhkSphereRepShape
{
	/**
	 <niobject name="bhkConvexShape" abstract="1" inherit="bhkSphereRepShape">
	 A havok shape.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}