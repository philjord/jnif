package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiArkViewportInfoExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiArkViewportInfoExtraData" inherit="NiExtraData">

	 Unknown node.
	 
	 <add name="Unknown Bytes" type="byte" arr1="13"/>
	 </niobject>
	 */

	public byte[] unknownBytes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownBytes = ByteConvert.readBytes(13, stream);

		return success;
	}
}