package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiBSplineBasisData;
import nif.niobject.NiBSplineData;

public abstract class NiBSplineInterpolator extends NiInterpolator
{
	/**
	 
	 <niobject name="NiBSplineInterpolator" abstract="1" inherit="NiInterpolator">

	 For interpolators storing data via a B-spline.
	 
	 <add name="Start Time" type="float">Animation start time.</add>
	 <add name="Stop Time" type="float">Animation stop time.</add>
	 <add name="Spline Data" type="Ref" template="NiBSplineData">Refers to NiBSplineData.</add>
	 <add name="Basis Data" type="Ref" template="NiBSplineBasisData">Refers to NiBSPlineBasisData.</add>
	 </niobject>
	 */

	public float startTime;

	public float stopTime;

	public NifRef splineData;

	public NifRef basisData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		startTime = ByteConvert.readFloat(stream);
		stopTime = ByteConvert.readFloat(stream);
		splineData = new NifRef(NiBSplineData.class, stream);
		basisData = new NifRef(NiBSplineBasisData.class, stream);

		return success;
	}
}