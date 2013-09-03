package nif.niobject;

import java.io.InputStream;

import nif.NifVer;

public abstract class NiAVObjectPalette extends NiObject
{
	/**
	 <niobject name="NiAVObjectPalette" abstract="1" inherit="NiObject">
	 Unknown.
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}