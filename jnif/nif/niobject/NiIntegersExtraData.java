package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiIntegersExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiIntegersExtraData" abstract="0" inherit="NiExtraData" ver1="10.1.0.0">

	 Integers data.
	 
	 <add name="Num Integers" type="uint">Number of integers.</add>
	 <add name="Data" type="uint" arr1="Num Integers">Integers.</add>
	 </niobject>
	 
	 */

	public int numIntegers;

	public int[] data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numIntegers = ByteConvert.readInt(stream);
		data = ByteConvert.readInts(numIntegers, stream);
		return success;
	}
}