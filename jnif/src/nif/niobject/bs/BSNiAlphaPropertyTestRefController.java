package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.controller.NiAlphaController;

public class BSNiAlphaPropertyTestRefController extends NiAlphaController
{
	/**
	 	<niobject name="BSNiAlphaPropertyTestRefController" inherit="NiAlphaController">
	    Unkown
		</niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
