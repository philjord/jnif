package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifBoxBV
{
	/**
	 <compound name="BoxBV">

	 Box Bounding Volume
	 
	 <add name="Center" type="Vector3">Center</add>
	 <add name="Axis" type="Vector3" arr1="3">Axis</add>
	 <add name="Extent" type="float" arr1="3">Extent</add>
	 </compound>				
	 */

	public NifVector3 center;

	public NifVector3 axis;

	public float extent;

	public NifBoxBV(InputStream stream) throws IOException
	{
		center = new NifVector3(stream);
		axis = new NifVector3(stream);
		extent = ByteConvert.readFloat(stream);
	}
}
