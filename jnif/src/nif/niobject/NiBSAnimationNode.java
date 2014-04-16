package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiBSAnimationNode extends NiNode
{
	/**
	 
	 <niobject name="NiBSAnimationNode" abstract="0" inherit="NiNode">

	 Bethesda-specific extension of Node with animation properties stored in the flags.
	 
	 </niobject>  
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}