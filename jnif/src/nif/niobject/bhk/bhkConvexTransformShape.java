package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class bhkConvexTransformShape extends bhkTransformShape
{
	/**
	 
	 <!--
	 Should inherit from bhkConvexShape according to hierarchy, but seems to be exactly the same as bhkTransformShape.
	 -->
	 <niobject name="bhkConvexTransformShape" abstract="0" inherit="bhkTransformShape" ver1="20.0.0.5">
	 A convex transformed shape?
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}