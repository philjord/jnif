package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiScreenElements extends NiTriShape
{
	/**
	 
	 <niobject name="NiScreenElements" inherit="NiTriShape">
	 Two dimensional screen elements.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}