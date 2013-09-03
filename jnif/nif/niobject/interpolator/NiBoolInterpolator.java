package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiBoolData;

public class NiBoolInterpolator extends NiKeyBasedInterpolator
{
	/**
	 <niobject name="NiBoolInterpolator" abstract="0" inherit="NiKeyBasedInterpolator" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Bool Value" type="bool">Value when posed?  At time 0?</add>
	 <add name="Data" type="Ref" template="NiBoolData">Refers to a NiBoolData object.</add>
	 </niobject>
	 */

	public boolean boolValue;

	public NifRef data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		boolValue = ByteConvert.readBool(stream, nifVer);
		data = new NifRef(NiBoolData.class, stream);
		return success;
	}
}