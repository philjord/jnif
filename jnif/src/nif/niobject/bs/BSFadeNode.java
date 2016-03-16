package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiNode;

public class BSFadeNode extends NiNode
{
	/**
	 <niobject name="BSFadeNode" abstract="0" inherit="NiNode" ver1="20.2.0.7" userver="11">
	 Bethesda-specific fade node.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}