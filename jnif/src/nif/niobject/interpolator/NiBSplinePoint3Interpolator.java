package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public abstract class NiBSplinePoint3Interpolator extends NiBSplineInterpolator
{
	/**
	 <niobject name="NiBSplinePoint3Interpolator" abstract="1" inherit="NiBSplineInterpolator" ver1="20.0.0.4">

	 Unknown.
	 
	 <add name="Unknown Floats" type="float" arr1="6">Unknown.</add>
	 </niobject>
	 
	 */
	public float[] unknownFloats;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloats = ByteConvert.readFloats(6, stream);
		return success;
	}
}