package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBlendBoolInterpolator extends NiBlendInterpolator
{
	/**
	 
	 <niobject name="NiBlendBoolInterpolator" abstract="0" inherit="NiBlendInterpolator" ver1="20.0.0.5">

	 An interpolator for a bool.
	 
	 <add name="Bool Value" type="byte">The interpolated bool?</add>
	 </niobject>
	 */

	public byte boolValue;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		boolValue = ByteConvert.readByte(stream);
		return success;
	}
}