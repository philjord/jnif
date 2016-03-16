package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSSkinBoneTrans
{
	/*<compound name="BSSkinBoneTrans">
	Fallout 4 Bone Transform
	<add name="Center" type="Vector3" />
	<add name="Radius" type="float" />
	<add name="Rotation" type="Matrix33" />
	<add name="Translation" type="Vector3" />
	<add name="Scale" type="float" />
	</compound>*/

	public NifVector3 Center;

	public float Radius;

	public NifMatrix33 Rotation;

	public NifVector3 Translation;

	public float Scale;

	public BSSkinBoneTrans(ByteBuffer stream) throws IOException
	{
		Center = new NifVector3(stream);
		Radius = ByteConvert.readFloat(stream);
		Rotation = new NifMatrix33(stream);
		Translation = new NifVector3(stream);
		Scale = ByteConvert.readFloat(stream);
	}
}
