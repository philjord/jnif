package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiObject;

public class BSAnimNotes extends NiObject
{
	/**
	 
	 <niobject name="BSAnimNotes" abstract="0" inherit="NiObject">

	 Bethesda-specific node.
	 
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 </niobject>
	 */

	public short unknownShort1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort1 = ByteConvert.readShort(stream);

		return success;
	}
}