package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;

public class bhkPrismaticConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkPrismaticConstraint" abstract="0" inherit="bhkConstraint" ver1="20.0.0.5">

	 A prismatic constraint.
	 
	 <add name="Pivot A" type="Vector4">Pivot A.</add>
	 <add name="Rotation" type="Vector4" arr1="4">4x4 rotation matrix, rotates the child entity.</add>
	 <add name="Pivot B" type="Vector4">Pivot B.</add>
	 <add name="Sliding Axis" type="Vector4">
	 Describes the axis the object is able to travel along. Unit vector.
	 </add>
	 <add name="Plane" type="Vector4">
	 Plane normal. Describes the plane the object is able to move on.
	 </add>
	 <add name="Min Distance" type="float">
	 Describe the min distance the object is able to travel.
	 </add>
	 <add name="Max Distance" type="float">
	 Describe the max distance the object is able to travel.
	 </add>
	 <add name="Friction" type="float">Friction.</add>
	 <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7">Unknown</add>
	 </niobject> 
	 
	 */

	public NifVector4 pivotA;

	public NifVector4[] rotation;

	public NifVector4 pivotB;

	public NifVector4 slidingAxis;

	public NifVector4 plane;

	public float minDistance;

	public float maxDistance;

	public float friction;

	public byte unknownByte1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		pivotA = new NifVector4(stream);
		rotation = new NifVector4[4];
		rotation[0] = new NifVector4(stream);
		rotation[1] = new NifVector4(stream);
		rotation[2] = new NifVector4(stream);
		rotation[3] = new NifVector4(stream);
		pivotB = new NifVector4(stream);
		slidingAxis = new NifVector4(stream);
		plane = new NifVector4(stream);

		minDistance = ByteConvert.readFloat(stream);
		maxDistance = ByteConvert.readFloat(stream);
		friction = ByteConvert.readFloat(stream);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownByte1 = ByteConvert.readByte(stream);
		}

		return success;
	}
}
