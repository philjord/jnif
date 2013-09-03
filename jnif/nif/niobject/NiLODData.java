package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class NiLODData extends NiObject
{
	/**
	 
	 <niobject name="NiLODData" abstract="1" inherit="NiObject">

	 Abstract class used for different types of LOD selections.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
