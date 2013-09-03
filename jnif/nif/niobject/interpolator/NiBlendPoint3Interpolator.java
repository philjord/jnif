package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifVector3;

public class NiBlendPoint3Interpolator extends NiBlendInterpolator
{
	/**
	 
	 <niobject name="NiBlendPoint3Interpolator" abstract="0" inherit="NiBlendInterpolator" ver1="20.0.0.5">

	 Interpolates a point?
	 
	 <add name="Point Value" type="Vector3">The interpolated point?</add>
	 </niobject>
	 */

	public NifVector3 pointValue;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		pointValue = new NifVector3(stream);

		return success;
	}
}