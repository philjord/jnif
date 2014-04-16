package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import nif.niobject.NiPosData;

public class NiPoint3Interpolator extends NiKeyBasedInterpolator
{
	/**
	 <niobject name="NiPoint3Interpolator" abstract="0" inherit="NiKeyBasedInterpolator" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Point 3 Value" type="Vector3">Value when posed?  Value at time 0?</add>
	 <add name="Data" type="Ref" template="NiPosData">Reference to NiPosData.</add>
	 </niobject>
	 */

	public NifVector3 point3Value;

	public NifRef data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		point3Value = new NifVector3(stream);
		data = new NifRef(NiPosData.class, stream);
		return success;
	}
}