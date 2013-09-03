package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifQuaternion
{
	/**
	 <compound name="Quaternion" niflibtype="Quaternion" nifskopetype="quaternion">

	 A quaternion.
	 
	 <add name="w" type="float">The w-coordinate.</add>
	 <add name="x" type="float">The x-coordinate.</add>
	 <add name="y" type="float">The y-coordinate.</add>
	 <add name="z" type="float">The z-coordinate.</add>
	 </compound>
	 */

	public float w;

	public float x;

	public float y;

	public float z;

	public NifQuaternion(InputStream stream) throws IOException
	{
		w = ByteConvert.readFloat(stream);
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
	}

	public boolean equals(Object o)
	{
		if (o instanceof NifQuaternion)
		{
			NifQuaternion q2 = (NifQuaternion) o;
			return q2.w == w && q2.x == x && q2.y == y && q2.z == z;
		}
		return false;
	}
}
