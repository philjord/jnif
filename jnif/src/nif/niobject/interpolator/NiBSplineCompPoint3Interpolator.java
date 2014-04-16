package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiBSplineCompPoint3Interpolator extends NiBSplinePoint3Interpolator
{
	/**
	 
	 <niobject name="NiBSplineCompPoint3Interpolator" abstract="0" inherit="NiBSplinePoint3Interpolator" ver1="20.0.0.4">
	 Unknown.
	 </niobject>	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}