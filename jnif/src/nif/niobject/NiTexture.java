package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.controller.NiObjectNET;

public abstract class NiTexture extends NiObjectNET
{

	/**
	 <niobject name="NiTexture" abstract="1" inherit="NiObjectNET">
	 A texture.
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}