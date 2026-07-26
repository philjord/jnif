package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifQuaternion
{
	public static final NifQuaternion Identity = new NifQuaternion(1f,0f,0f,0f);
 
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

	public NifQuaternion(ByteBuffer stream) throws IOException
	{
		w = ByteConvert.readFloat(stream);
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
	}

	public NifQuaternion(ByteBuffer stream, int offset) {
		w = stream.getFloat(offset + 0);
		x = stream.getFloat(offset + 4);
		y = stream.getFloat(offset + 8);
		z = stream.getFloat(offset + 12);
	}

	public NifQuaternion(float w, float x, float y, float z)  
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof NifQuaternion)
		{
			NifQuaternion q2 = (NifQuaternion) o;
			return q2.w == w && q2.x == x && q2.y == y && q2.z == z;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "NPQuaternion " + x + " " + y + " " + z + " " + w;
	}

	// for interpolation
	//https://stackoverflow.com/questions/9027201/how-to-apply-a-scalar-multiplication-to-a-quaternion
	public NifQuaternion mul(float f) {
		this.w *= f;
		this.x *= f;
		this.y *= f;
		this.z *= f;
		return this;
	}
	public NifQuaternion add(NifQuaternion q) {
		this.w += q.w;
		this.x += q.x;
		this.y += q.y;
		this.z += q.z;
		return this;
	}
}
