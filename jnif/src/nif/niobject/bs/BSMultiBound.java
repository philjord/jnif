package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiObject;

public class BSMultiBound extends NiObject
{
	/**
	 
	 <niobject name="BSMultiBound" inherit="NiObject">

	 Bethesda-specific node.
	 
	 <add name="Unknown Int 1" type="int">Unknown Flag</add>
	 </niobject>
	 */

	public int unknownInt1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}