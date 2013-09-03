package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiTimeController;

public class BSFrustumFOVController extends NiTimeController
{
	/**
	 
	 <niobject name="BSFrustumFOVController" inherit="NiTimeController">

	 Bethesda-specific node.
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 </niobject>
	 
	 */

	public int unknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}