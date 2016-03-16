package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiClodSkinInstance extends NiSkinInstance
{
	/**
	 <niobject name="NiClodSkinInstance" abstract="0" inherit="NiSkinInstance">

	 A copy of NISkinInstance for use with NiClod meshes.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}