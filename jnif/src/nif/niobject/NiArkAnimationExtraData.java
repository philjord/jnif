package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiArkAnimationExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiArkAnimationExtraData" inherit="NiExtraData">

	 Unknown node.
	 
	 <add name="Unknown Ints" type="int" arr1="4"/>
	 <add name="Unknown Bytes" type="byte" arr1="37" ver2="4.1.0.12"/>
	 </niobject>
	 */

	public int[] unknownInts;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInts = ByteConvert.readInts(4, stream);

		return success;
	}
}