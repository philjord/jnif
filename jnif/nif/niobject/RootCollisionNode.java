package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class RootCollisionNode extends NiNode
{
	/*
	 
	 <niobject name="RootCollisionNode" abstract="0" inherit="NiNode">

	 Morrowind-specific node for collision mesh.
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}