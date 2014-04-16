package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifMatrix22
{
	/**
	 <compound name="Matrix22" niflibtype="Matrix22">

	 A 2x2 matrix of float values.  Stored in OpenGL column-major format.
	 
	 <add name="m11" type="float">Member 1,1 (top left)</add>
	 <add name="m21" type="float">Member 2,1 (bottom left)</add>
	 <add name="m12" type="float">Member 1,2 (top right)</add>
	 <add name="m22" type="float">Member 2,2 (bottom right)</add>
	 </compound>
	 */

	public float m11;

	public float m21;

	public float m12;

	public float m22;

	public NifMatrix22(InputStream stream) throws IOException
	{
		m11 = ByteConvert.readFloat(stream);
		m21 = ByteConvert.readFloat(stream);
		m12 = ByteConvert.readFloat(stream);
		m22 = ByteConvert.readFloat(stream);
	}
}
