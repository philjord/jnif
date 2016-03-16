package nif.niobject.fx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class FxButton extends FxWidget
{
	/**
	 
	 <niobject name="FxButton" abstract="0" inherit="FxWidget" ver1="10.0.1.0">
	 Unknown.
	 </niobject>	 	
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}