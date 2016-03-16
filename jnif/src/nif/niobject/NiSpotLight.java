package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiSpotLight extends NiPointLight
{
	/**
	 
	 <niobject name="NiSpotLight" abstract="0" inherit="NiPointLight" ver1="20.0.0.4">

	 A spot.
	 
	 <add name="Cutoff Angle" type="float">The opening angle of the spot.</add>
	 <add name="Unknown Float" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Exponent" type="float">
	 Describes the distribution of light. (see: glLight)
	 </add>
	 </niobject>
	 
	 */

	public float cutoffAngle;

	public float unknownFloat;

	public float exponent;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		cutoffAngle = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownFloat = ByteConvert.readFloat(stream);
		}
		exponent = ByteConvert.readFloat(stream);

		return success;
	}
}