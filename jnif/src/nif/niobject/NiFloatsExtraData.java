package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiFloatsExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiFloatsExtraData" abstract="0" inherit="NiExtraData" ver1="20.0.0.4">

	 Unknown.
	 
	 <add name="Num Floats" type="uint">Number of floats in the next field.</add>
	 <add name="Data" type="float" arr1="Num Floats">Float data.</add>
	 </niobject>
	 
	 */

	public int numFloats;

	public float[] data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numFloats = ByteConvert.readInt(stream);
		data = ByteConvert.readFloats(numFloats, stream);
		return success;
	}
}