package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiBSplineBasisData extends NiObject
{
	/**
	 <niobject name="NiBSplineBasisData" abstract="0" inherit="NiObject" ver1="20.0.0.4">

	 Stores the number of control points of a B-spline.
	 
	 <add name="Num Control Points" type="uint">
	 The number of control points of the B-spline (number of frames of animation plus degree of B-spline minus one).
	 </add>
	 </niobject>
	 */

	public int numControlPoints;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numControlPoints = ByteConvert.readInt(stream);
		return success;
	}
}