package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifBoundingBox
{
	/**
	 <compound name="BoundingBox">

	 Bounding box.
	 
	 <add name="Unknown Int" type="uint" default="1">Usually 1.</add>
	 <add name="Translation" type="Vector3">Translation vector.</add>
	 <add name="Rotation" type="Matrix33">Rotation matrix.</add>
	 <add name="Radius" type="Vector3">Radius, per direction.</add>
	 </compound>
	 */

	public int unknownInt;

	public NifVector3 translation;

	public NifMatrix33 rotation;

	public NifVector3 radius;

	public NifBoundingBox(InputStream stream) throws IOException
	{
		unknownInt = ByteConvert.readInt(stream);

		translation = new NifVector3(stream);

		rotation = new NifMatrix33(stream);

		radius = new NifVector3(stream);

	}
}
