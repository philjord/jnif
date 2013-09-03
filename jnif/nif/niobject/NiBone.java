package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiBone extends NiNode
{
	/**
	 
	 <niobject name="NiBone" abstract="0" inherit="NiNode" ver2="3.03">
	 A NiNode used as a skeleton bone?
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}