package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiObject;

public class BSMultiBoundData extends NiObject
{
	/**
	     <niobject name="BSMultiBoundData" inherit="NiObject">
	        Abstract base type for bounding data.
	    </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
