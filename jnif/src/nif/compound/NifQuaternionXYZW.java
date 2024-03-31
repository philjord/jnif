package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

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

	public NifQuaternionXYZW(ByteBuffer stream) throws IOException
	{
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
		w = ByteConvert.readFloat(stream);
	}

	public NifQuaternionXYZW(ByteBuffer stream, int offset) {
		x = stream.getFloat(offset + 0);
		y = stream.getFloat(offset + 4);
		z = stream.getFloat(offset + 8);
		w = stream.getFloat(offset + 12);
	}	
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof NifQuaternionXYZW)
		{
			NifQuaternionXYZW q2 = (NifQuaternionXYZW) o;
			return q2.w == w && q2.x == x && q2.y == y && q2.z == z;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "NPQuaternionXYZW " + x + " " + y + " " + z + " " + w;
	}
}
