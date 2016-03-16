package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBSplineCompTransformInterpolator extends NiBSplineTransformInterpolator
{
	/**
	 
	 <niobject name="NiBSplineCompTransformInterpolator" abstract="0" inherit="NiBSplineTransformInterpolator" ver1="20.0.0.4">

	 An interpolator for storing transform keyframes via a compressed
	 B-spline (that is, using shorts rather than floats in the B-spline
	 data).
	 
	 <add name="Translation Bias" type="float">Translation Bias</add>
	 <add name="Translation Multiplier" type="float">Translation Multiplier</add>
	 <add name="Rotation Bias" type="float">Rotation Bias</add>
	 <add name="Rotation Multiplier" type="float">Rotation Multiplier</add>
	 <add name="Scale Bias" type="float">Scale Bias</add>
	 <add name="Scale Multiplier" type="float">Scale Multiplier</add>
	 </niobject>
	 */

	public float translationBias;

	public float translationMultiplier;

	public float rotationBias;

	public float rotationMultiplier;

	public float scaleBias;

	public float scaleMultiplier;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		translationBias = ByteConvert.readFloat(stream);
		translationMultiplier = ByteConvert.readFloat(stream);
		rotationBias = ByteConvert.readFloat(stream);
		rotationMultiplier = ByteConvert.readFloat(stream);
		scaleBias = ByteConvert.readFloat(stream);
		scaleMultiplier = ByteConvert.readFloat(stream);

		return success;
	}
}