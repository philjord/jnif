package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBSplineData extends NiObject
{
	/**
	 <niobject name="NiBSplineData" abstract="0" inherit="NiObject" ver1="20.0.0.4">

	 B-spline data points as floats, or as shorts for compressed B-splines.
	 
	 <add name="Num Float Control Points" type="uint">Number of Float Data Points</add>
	 <add name="Float Control Points" type="float" arr1="Num Float Control Points">Float values representing the control data.</add>
	 <add name="Num Short Control Points" type="uint">Number of Short Data Points</add>
	 <add name="Short Control Points" type="short" arr1="Num Short Control Points">
	 Signed shorts representing the data from 0 to 1 (scaled by SHRT_MAX).
	 </add>
	 </niobject>
	 */

	public int numFloatControlPoints;

	public float[] floatControlPoints;

	public int numShortControlPoints;

	public short[] shortControlPoints;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numFloatControlPoints = ByteConvert.readInt(stream);
		floatControlPoints = ByteConvert.readFloats(numFloatControlPoints, stream);
		numShortControlPoints = ByteConvert.readInt(stream);
		shortControlPoints = ByteConvert.readShorts(numShortControlPoints, stream);
		return success;
	}
}