package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifMatrix33
{
	/**
	 <compound name="Matrix33" niflibtype="Matrix33" nifskopetype="matrix33">
	
	 A 3x3 rotation matrix; M^T M=identity, det(M)=1.    Stored in OpenGL column-major format.
	 
	 <add name="m11" type="float">Member 1,1 (top left)</add>
	 <add name="m21" type="float">Member 2,1</add>
	 <add name="m31" type="float">Member 3,1 (bottom left)</add>
	 <add name="m12" type="float">Member 1,2</add>
	 <add name="m22" type="float">Member 2,2</add>
	 <add name="m32" type="float">Member 3,2</add>
	 <add name="m13" type="float">Member 1,3 (top right)</add>
	 <add name="m23" type="float">Member 2,3</add>
	 <add name="m33" type="float">Member 3,3 (bottom left)</add>
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

	public NifMatrix33(InputStream stream) throws IOException
	{
		m11 = ByteConvert.readFloat(stream);
		m21 = ByteConvert.readFloat(stream);
		m31 = ByteConvert.readFloat(stream);
		m12 = ByteConvert.readFloat(stream);
		m22 = ByteConvert.readFloat(stream);
		m32 = ByteConvert.readFloat(stream);
		m13 = ByteConvert.readFloat(stream);
		m23 = ByteConvert.readFloat(stream);
		m33 = ByteConvert.readFloat(stream);
	}

	public float[] data()
	{
		return new float[] { m11, m12, m13, m21, m22, m23, m31, m32, m33 };
	}
}
