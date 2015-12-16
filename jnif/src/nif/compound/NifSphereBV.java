package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifSphereBV
{
	/**
	 <compound name="SphereBV">
	
	 A sphere.
	 
	 <add name="Center" type="Vector3">The sphere's center.</add>
	 <add name="Radius" type="float">The sphere's radius.</add>
	 </compound> 				
	 */

	public NifVector3 center;

	public float radius;

	public NifSphereBV(InputStream stream) throws IOException
	{
		center = new NifVector3(stream);
		radius = ByteConvert.readFloat(stream);
	}

	public String toString()
	{
		return "[NifSphereBV] " + center + " : " + radius;
	}
}
