package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.BSShaderFlags;
import nif.enums.BSShaderType;
import nif.enums.BSShaderTypeI;
import nif.niobject.NiShadeProperty;

public class BSShaderProperty extends NiShadeProperty
{
	/**
	 
	<niobject name="BSShaderProperty" inherit="NiShadeProperty" module="BSMain" versions="#FO3_AND_LATER#">
        Bethesda-specific property.
        <field name="Shader Type" type="BSShaderType" default="" vercond="#NI_BS_LTE_FO3#" />
        <field name="Shader Flags" type="BSShaderFlags" default="0x82000000" vercond="#NI_BS_LTE_FO3#" />
        <field name="Shader Flags 2" type="BSShaderFlags2" default="1" vercond="#NI_BS_LTE_FO3#" />
        <field name="Environment Map Scale" type="float" default="1.0" range="#F0_10#" vercond="#NI_BS_LTE_FO3#">Scales the intensity of the environment/cube map.</field>
    </niobject>
	 */


	public BSShaderTypeI ShaderType = null;

	public BSShaderFlags ShaderFlags;

	public BSShaderFlags ShaderFlags2;

	public float EnvironmentMapScale;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		if(nifVer.NI_BS_LTE_FO3()) {			
			ShaderType = BSShaderType.load(stream);//<field name="Shader Type" type="BSShaderType" default="SHADER_DEFAULT" vercond="#NI_BS_LTE_FO3#" />
			ShaderFlags = new BSShaderFlags(stream);//<field name="Shader Flags" type="BSShaderFlags" default="0x82000000" vercond="#NI_BS_LTE_FO3#" />
			ShaderFlags2 = new BSShaderFlags(stream); //<field name="Shader Flags 2" type="BSShaderFlags2" default="1" vercond="#NI_BS_LTE_FO3#" />
			EnvironmentMapScale = ByteConvert.readFloat(stream); //<field name="Environment Map Scale" type="float" default="1.0" range="#F0_10#" vercond="#NI_BS_LTE_FO3#">Scales the intensity of the environment/cube map.</field>
		}

		return success;
	}
}