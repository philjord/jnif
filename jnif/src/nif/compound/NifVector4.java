package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifVector4
{
	/**
	 <compound name="Vector4" niflibtype="Vector4" nifskopetype="vector4">

	 A 4-dimensional vector.
	 
	 <add name="x" type="float">First coordinate.</add>
	 <add name="y" type="float">Second coordinate.</add>
	 <add name="z" type="float">Third coordinate.</add>
	 <add name="w" type="float">Fourth coordinate.</add>
	 </compound>
	 */

	public float x;

	public float y;

	public float z;

	public float w;
	
	protected NifVector4() {
		
	}

	public NifVector4(ByteBuffer stream) throws IOException
	{
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
		w = ByteConvert.readFloat(stream);

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z) || Float.isNaN(w))
		{
			x = 0;
			y = 0;
			z = 0;
			w = 0;
		}
	}

	public NifVector4(ByteBuffer stream, int offset) {
		x = stream.getFloat(offset + 0);
		y = stream.getFloat(offset + 4);
		z = stream.getFloat(offset + 8);
		w = stream.getFloat(offset + 12);

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z) || Float.isNaN(w))
		{
			x = 0;
			y = 0;
			z = 0;
			w = 0;
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof NifVector4)
		{
			NifVector4 v2 = (NifVector4) o;
			return v2.x == x && v2.y == y && v2.z == z && v2.w == w;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "[NPVector4] x:" + x + " y:" + y + " z:" + z + " w:" + w;
	}
}
