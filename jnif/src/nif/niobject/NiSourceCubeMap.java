package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiSourceCubeMap extends NiSourceTexture
{
	/**
	 <niobject name="NiSourceCubeMap" abstract="0" inherit="NiSourceTexture">
	 Unknown node.  Found in Emerge Demo.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}