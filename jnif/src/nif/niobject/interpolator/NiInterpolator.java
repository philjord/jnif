package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.NiObject;

public abstract class NiInterpolator extends NiObject
{
	/**
	 <niobject name="NiInterpolator" abstract="1" inherit="NiObject" ver1="10.2.0.0">

	 Interpolator objects - function unknown.
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}