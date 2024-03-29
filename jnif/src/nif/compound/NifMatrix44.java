package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

//NOT sub classed from Matrix3, the maths is NOT the same!!
public class NifMatrix44
{
	/**
	 * <compound name="Matrix44" niflibtype="Matrix44" nifskopetype="matrix44">
	
	 A 4x4 transformation matrix.
	 
	 <add name="m11" type="float">The (1,1) element.</add>
	 <add name="m21" type="float">The (2,1) element.</add>
	 <add name="m31" type="float">The (3,1) element.</add>
	 <add name="m41" type="float">The (4,1) element.</add>
	 <add name="m12" type="float">The (1,2) element.</add>
	 <add name="m22" type="float">The (2,2) element.</add>
	 <add name="m32" type="float">The (3,2) element.</add>
	 <add name="m42" type="float">The (4,2) element.</add>
	 <add name="m13" type="float">The (1,3) element.</add>
	 <add name="m23" type="float">The (2,3) element.</add>
	 <add name="m33" type="float">The (3,3) element.</add>
	 <add name="m43" type="float">The (4,3) element.</add>
	 <add name="m14" type="float">The (1,4) element.</add>
	 <add name="m24" type="float">The (2,4) element.</add>
	 <add name="m34" type="float">The (3,4) element.</add>
	 <add name="m44" type="float">The (4,4) element.</add>
	 </compound>
	 */

	public float m11;

	public float m21;

	public float m31;

	public float m12;

	public float m22;

	public float m32;

	public float m13;

	public float m23;

	public float m33;

	public float m41;

	public float m42;

	public float m43;

	public float m14;

	public float m24;

	public float m34;

	public float m44;

	public NifMatrix44(ByteBuffer stream) throws IOException
	{
		m11 = ByteConvert.readFloat(stream);
		m21 = ByteConvert.readFloat(stream);
		m31 = ByteConvert.readFloat(stream);
		m41 = ByteConvert.readFloat(stream);
		m12 = ByteConvert.readFloat(stream);
		m22 = ByteConvert.readFloat(stream);
		m32 = ByteConvert.readFloat(stream);
		m42 = ByteConvert.readFloat(stream);
		m13 = ByteConvert.readFloat(stream);
		m23 = ByteConvert.readFloat(stream);
		m33 = ByteConvert.readFloat(stream);
		m43 = ByteConvert.readFloat(stream);
		m14 = ByteConvert.readFloat(stream);
		m24 = ByteConvert.readFloat(stream);
		m34 = ByteConvert.readFloat(stream);
		m44 = ByteConvert.readFloat(stream);
	}
	public NifMatrix44(ByteBuffer stream, int offset) throws IOException
	{
		m11 = stream.getFloat(offset + 0);
		m21 = stream.getFloat(offset + 4);
		m31 = stream.getFloat(offset + 8);
		m41 = stream.getFloat(offset + 12);
		m12 = stream.getFloat(offset + 16);
		m22 = stream.getFloat(offset + 20);
		m32 = stream.getFloat(offset + 24);
		m42 = stream.getFloat(offset + 28);
		m13 = stream.getFloat(offset + 32);
		m23 = stream.getFloat(offset + 36);
		m33 = stream.getFloat(offset + 40);
		m43 = stream.getFloat(offset + 44);
		m14 = stream.getFloat(offset + 48);
		m24 = stream.getFloat(offset + 52);
		m34 = stream.getFloat(offset + 56);
		m44 = stream.getFloat(offset + 60);
	}
	public float[] data()
	{
		return new float[] { m11, m12, m13, m14, m21, m22, m23, m24, m31, m32, m33, m34, m41, m42, m43, m44 };
	}

}
