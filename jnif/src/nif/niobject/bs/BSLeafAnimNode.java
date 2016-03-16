package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiNode;

public class BSLeafAnimNode extends NiNode
{
	/**
	 *     <niobject name="BSLeafAnimNode" inherit="NiNode">
	    Unknown, related to trees.
	    </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
