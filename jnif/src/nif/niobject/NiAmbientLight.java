package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiAmbientLight extends NiLight
{
	/**
	 <niobject name="NiAmbientLight" abstract="0" inherit="NiLight">
	 Ambient light source.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}