package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class NiBSplineFloatInterpolator extends NiBSplineInterpolator
{
	/**
	 
	 <niobject name="NiBSplineFloatInterpolator" abstract="1" inherit="NiBSplineInterpolator" ver1="20.0.0.4">
	 Unknown.
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}