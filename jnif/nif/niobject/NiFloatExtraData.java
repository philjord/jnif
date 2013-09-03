package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiFloatExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiFloatExtraData" abstract="0" inherit="NiExtraData" ver1="20.0.0.4">

	 Float extra data.
	 
	 <add name="Float Data" type="float">The float data.</add>
	 </niobject>
	 
	 */

	public float floatData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		floatData = ByteConvert.readFloat(stream);
		return success;
	}
}