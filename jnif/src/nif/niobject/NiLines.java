package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiLines extends NiTriBasedGeom
{
	/**
	 
	 <niobject name="NiLines" inherit="NiTriBasedGeom" ver1="4.2.2.0">
	 Wireframe geometry.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}