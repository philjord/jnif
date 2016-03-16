package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiBlendTransformInterpolator extends NiBlendInterpolator
{
	/**
	 
	 <niobject name="NiBlendTransformInterpolator" abstract="0" inherit="NiBlendInterpolator" ver1="20.0.0.5">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}