package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBSplineCompFloatInterpolator extends NiBSplineFloatInterpolator
{
	/**
	 <niobject name="NiBSplineCompFloatInterpolator" abstract="0" inherit="NiBSplineFloatInterpolator" ver1="20.0.0.4">

	 Unknown.
	 
	 <add name="Base" type="float">Base value when curve not defined.</add>
	 <add name="Offset" type="uint">
	 Starting offset for the data. (USHRT_MAX for no data.)
	 </add>
	 <add name="Bias" type="float">Bias</add>
	 <add name="Multiplier" type="float">Multiplier</add>
	 </niobject>
	 */

	public float base;

	public int offset;

	public float bias;

	public float multiplier;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		base = ByteConvert.readFloat(stream);
		offset = ByteConvert.readInt(stream);
		bias = ByteConvert.readFloat(stream);
		multiplier = ByteConvert.readFloat(stream);

		return success;
	}
}