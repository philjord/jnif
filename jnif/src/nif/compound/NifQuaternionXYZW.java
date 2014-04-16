package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifQuaternionXYZW
{
	/**
	 <compound name="QuaternionXYZW" nifskopetype="quaternion_xyzw">

	 A quaternion as it appears in the havok objects.
	 
	 <add name="x" type="float">The x-coordinate.</add>
	 <add name="y" type="float">The y-coordinate.</add>
	 <add name="z" type="float">The z-coordinate.</add>
	 <add name="w" type="float">The w-coordinate.</add>
	 </compound>
	 */

	public float x;

	public float y;

	public float z;

	public float w;

	public NifQuaternionXYZW(InputStream stream) throws IOException
	{
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
		w = ByteConvert.readFloat(stream);
	}

	public String toString()
	{
		return "NPQuaternionXYZW " + x + " " + y + " " + z + " " + w;
	}
}
