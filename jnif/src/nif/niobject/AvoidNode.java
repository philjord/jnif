package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class AvoidNode extends NiNode
{
	/**
	 
	 <niobject name="AvoidNode" abstract="0" inherit="NiNode">
	 Morrowind specific?
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}