package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSSPLuminanceParams
{
	/**
	   <struct name="BSSPLuminanceParams" module="BSMain" versions="#F76#">
        <field name="Lum Emittance" type="float" default="100.0" range="#F_POS#" />
        <field name="Exposure Offset" type="float" default="13.5" range="#F_POS#" />
        <field name="Final Exposure Min" type="float" default="2.0" range="#F_POS#" />
        <field name="Final Exposure Max" type="float" default="3.0" range="#F_POS#" />
    </struct>
	 */

	public float LumEmittance;
	public float ExposureOffset;
	public float FinalExposureMin;
	public float FinalExposureMax;

	public BSSPLuminanceParams(ByteBuffer stream) throws IOException
	{
		LumEmittance = ByteConvert.readFloat(stream);
		ExposureOffset = ByteConvert.readFloat(stream);
		FinalExposureMin = ByteConvert.readFloat(stream);
		FinalExposureMax = ByteConvert.readFloat(stream);
	}
 
}
