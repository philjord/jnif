package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;

public class NiBSplineTransformInterpolator extends NiBSplineInterpolator
{
	/**
	 
	 <niobject name="NiBSplineTransformInterpolator" abstract="0" inherit="NiBSplineInterpolator" ver1="20.0.0.4">

	 An interpolator for storing transform keyframes via a B-spline.
	 
	 <add name="Translation" type="Vector3">Base translation when translate curve not defined.</add>
	 <add name="Rotation" type="Quaternion">Base rotation when rotation curve not defined.</add>
	 <add name="Scale" type="float">Base scale when scale curve not defined.</add>
	 <add name="Translation Offset" type="uint">
	 Starting offset for the translation data. (USHRT_MAX for no data.)
	 </add>
	 <add name="Rotation Offset" type="uint">
	 Starting offset for the rotation data. (USHRT_MAX for no data.)
	 </add>
	 <add name="Scale Offset" type="uint">
	 Starting offset for the scale data. (USHRT_MAX for no data.)
	 </add>
	 </niobject>
	 
	 */

	public NifVector3 translation;

	public NifQuaternion rotation;

	public float scale;

	public int translationOffset;

	public int rotationOffset;

	public int scaleOffset;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		translation = new NifVector3(stream);
		rotation = new NifQuaternion(stream);
		scale = ByteConvert.readFloat(stream);
		translationOffset = ByteConvert.readInt(stream);
		rotationOffset = ByteConvert.readInt(stream);
		scaleOffset = ByteConvert.readInt(stream);

		return success;
	}
}