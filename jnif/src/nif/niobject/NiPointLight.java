package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPointLight extends NiLight
{
	/**
	 <niobject name="NiPointLight" abstract="0" inherit="NiLight">

	 A point light.
	 
	 <add name="Constant Attenuation" type="float">Constant Attenuation</add>
	 <add name="Linear Attenuation" type="float">Linear Attenuation</add>
	 <add name="Quadratic Attenuation" type="float">Quadratic Attenuation (see glLight)</add>
	 </niobject>
	 */

	public float constantAttenuation;

	public float linearAttenuation;

	public float quadraticAttenuation;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		constantAttenuation = ByteConvert.readFloat(stream);
		linearAttenuation = ByteConvert.readFloat(stream);
		quadraticAttenuation = ByteConvert.readFloat(stream);
		return success;
	}
}