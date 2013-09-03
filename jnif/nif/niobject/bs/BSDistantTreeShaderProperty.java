package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class BSDistantTreeShaderProperty extends BSShaderProperty
{
	/**
	 
	 <niobject name="BSDistantTreeShaderProperty" abstract="0" inherit="BSShaderProperty" ver1="20.2.0.7" userver="11">
	 Bethesda-specific node.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}