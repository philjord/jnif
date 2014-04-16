package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiBoolTimelineInterpolator extends NiBoolInterpolator
{
	/**
	 <niobject name="NiBoolTimelineInterpolator" abstract="0" inherit="NiBoolInterpolator" ver1="20.0.0.5">
	 Unknown.
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}