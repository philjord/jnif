package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class BSSPWetnessParams
{
	/**
	   <struct name="BSSPWetnessParams" module="BSMain" versions="#FO4# #F76#">
        <field name="Spec Scale" type="float" default="-1.0" />
        <field name="Spec Power" type="float" default="-1.0" />
        <field name="Min Var" type="float" default="-1.0" />
        <field name="Env Map Scale" type="float" default="-1.0" vercond="#BS_FO4#" />
        <field name="Fresnel Power" type="float" default="-1.0" />
        <field name="Metalness" type="float" default="-1.0" />
        <field name="Unknown 1" type="float" default="-1.0" vercond="#BS_GT_130#" />
        <field name="Unknown 2" type="float" default="-1.0" vercond="#BS_GTE_F76#" />
    </struct>
	 */

	public float SpecScale = -1;
	public float SpecPower = -1;
	public float MinVar = -1;
	public float EnvMapScale = -1;
	public float FresnelPower = -1;
	public float Metalness = -1;
	public float Unknown1 = -1;
	public float Unknown2 = -1;

	public BSSPWetnessParams(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		SpecScale = ByteConvert.readFloat(stream);
		SpecPower = ByteConvert.readFloat(stream);
		MinVar = ByteConvert.readFloat(stream);
     
		if (nifVer.BS_FO4())
			EnvMapScale = ByteConvert.readFloat(stream);
		FresnelPower = ByteConvert.readFloat(stream);
		Metalness = ByteConvert.readFloat(stream);
		
		if (nifVer.BS_GT_130())
			Unknown1 = ByteConvert.readFloat(stream);
		if (nifVer.BS_GTE_F76())
			Unknown2 = ByteConvert.readFloat(stream);
	}
 
}
