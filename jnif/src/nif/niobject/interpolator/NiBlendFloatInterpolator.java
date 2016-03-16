package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBlendFloatInterpolator extends NiBlendInterpolator
{
	/**
	 
	 <niobject name="NiBlendFloatInterpolator" abstract="0" inherit="NiBlendInterpolator" ver1="20.0.0.5">

	 An interpolator for a float.
	 
	 <add name="Float Value" type="float">The interpolated float?</add>
	 </niobject>
	 */

	// I think these interp mean, go from 0  to 1 (or vise versa if float_max) and multiply byt eh targets value to get teh proportion over tiem
	// so tecture transform would go from time 0 to 10, f = 0 to 1 moving the U of texture f*U.length
	public float floatValue;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		floatValue = ByteConvert.readFloat(stream);

		return success;
	}
}