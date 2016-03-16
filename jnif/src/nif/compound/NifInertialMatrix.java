package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifInertialMatrix
{
	/**
	 <compound name="InertiaMatrix" niflibtype="InertiaMatrix">

	 An inertia matrix.
	 
	 <add name="m11" type="float"/>
	 <add name="m12" type="float"/>
	 <add name="m13" type="float"/>
	 <add name="m14" type="float">Zero</add>
	 <add name="m21" type="float"/>
	 <add name="m22" type="float"/>
	 <add name="m23" type="float"/>
	 <add name="m24" type="float">Zero</add>
	 <add name="m31" type="float"/>
	 <add name="m32" type="float"/>
	 <add name="m33" type="float"/>
	 <add name="m34" type="float">Zero</add>
	 </compound>
	 */

	public float m11;

	public float m21;

	public float m31;

	public float m41;

	public float m12;

	public float m22;

	public float m32;

	public float m42;

	public float m13;

	public float m23;

	public float m33;

	public float m43;

	public NifInertialMatrix(ByteBuffer stream) throws IOException
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

	}
}
