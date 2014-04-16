package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPhysXBodyDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXBodyDesc" inherit="NiObject" ver1="20.3.0.6">

	 Unknown PhysX node.
	 
	 <add name="Unknown Bytes" type="byte" arr1="136" ver1="20.3.0.6">Unknown</add>
	 </niobject>
	 */

	public byte[] unknownBytes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownBytes = ByteConvert.readBytes(136, stream);

		return success;
	}
}