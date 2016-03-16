package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiStringsExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiStringsExtraData" abstract="0" inherit="NiExtraData">

	 List of strings; for example, a list of all bone names.
	 
	 <add name="Num Strings" type="uint">Number of strings.</add>
	 <add name="Data" type="string" arr1="Num Strings">The strings.</add>
	 </niobject>
	 
	 */

	public int numStrings;

	public String[] data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numStrings = ByteConvert.readInt(stream);
		data = new String[numStrings];
		for (int i = 0; i < numStrings; i++)
		{
			data[i] = ByteConvert.readSizedString(stream);
		}
		return success;
	}
}