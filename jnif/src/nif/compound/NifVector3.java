package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifVector3
{
	/**
	 <compound name="Vector3" niflibtype="Vector3" nifskopetype="vector3">

	 A vector in 3D space (x,y,z).
	 
	 <add name="x" type="float">First coordinate.</add>
	 <add name="y" type="float">Second coordinate.</add>
	 <add name="z" type="float">Third coordinate.</add>
	 </compound> 	
	 */

	public float x;

	public float y;

	public float z;

	public NifVector3(InputStream stream) throws IOException
	{
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}

	public boolean equals(Object o)
	{
		if (o instanceof NifVector3)
		{
			NifVector3 v2 = (NifVector3) o;
			return v2.x == x && v2.y == y && v2.z == z;
		}
		return false;
	}

	public String toString()
	{
		return "[NPVector3] " + x + " " + y + " " + z;
	}
}
