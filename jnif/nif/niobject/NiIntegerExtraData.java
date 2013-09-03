package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiIntegerExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiIntegerExtraData" abstract="0" inherit="NiExtraData" ver1="10.0.1.0">

	 Extra integer data.
	 
	 <add name="Integer Data" type="uint">The value of the extra data.</add>
	 </niobject>
	 */

	public int integerData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		integerData = ByteConvert.readInt(stream);
		return success;
	}
}