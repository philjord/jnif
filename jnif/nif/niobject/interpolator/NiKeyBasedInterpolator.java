package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class NiKeyBasedInterpolator extends NiInterpolator
{
	/**
	<niobject name="NiKeyBasedInterpolator" abstract="1" inherit="NiInterpolator" ver1="10.2.0.0">
	    Interpolator objects that use keys?
	</niobject>
	 
	*/

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}