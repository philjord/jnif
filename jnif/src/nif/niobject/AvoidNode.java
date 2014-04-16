package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class AvoidNode extends NiNode
{
	/**
	 
	 <niobject name="AvoidNode" abstract="0" inherit="NiNode">
	 Morrowind specific?
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}