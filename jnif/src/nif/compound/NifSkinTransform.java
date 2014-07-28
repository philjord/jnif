package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifSkinTransform
{
	/**
	 <compound name="SkinTransform">
	 <add name="Rotation" type="Matrix33">
	 Rotation offset of the skin from this bone in bind position.
	 </add>
	 <add name="Translation" type="Vector3">
	 Translation offset of the skin from this bone in bind position.
	 </add>
	 <add name="Scale" type="float">
	 Scale offset of the skin from this bone in bind position. (Assumption - this is always 1.0 so far)
	 </add>
	 <add name="Bounding Sphere Offset" type="Vector3">
	 Translation offset of a bounding sphere holding all vertices. (Note that its a Sphere Containing Axis Aligned Box not a minimum volume Sphere)
	 </compound>
	 */
	public NifMatrix33 rotation;

	public NifVector3 translation;

	public float scale;

	public NifSkinTransform(InputStream stream) throws IOException
	{
		rotation = new NifMatrix33(stream);
		translation = new NifVector3(stream);
		scale = ByteConvert.readFloat(stream);
	}
}
