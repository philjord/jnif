package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiFloatData;

public class NiFloatInterpolator extends NiKeyBasedInterpolator
{
	/**
	 <niobject name="NiFloatInterpolator" abstract="0" inherit="NiKeyBasedInterpolator" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Float Value" type="float">Value when posed?  At time 0?</add>
	 <add name="Data" type="Ref" template="NiFloatData">Float data?</add>
	 </niobject>
	 */

	public float floatValue;

	public NifRef data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		floatValue = ByteConvert.readFloat(stream);
		data = new NifRef(NiFloatData.class, stream);
		return success;
	}
}