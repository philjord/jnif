package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPhysXKinematicSrc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXKinematicSrc" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Bytes" type="byte" arr1="6" ver1="20.3.0.6">Unknown</add>
	 </niobject>
	 */

	public byte[] unknownBytes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_20_3_0_6)
		{
			unknownBytes = ByteConvert.readBytes(6, stream);
		}

		return success;
	}
}