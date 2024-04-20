package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSSPLuminanceParams;
import nif.compound.NifColor3;
import nif.compound.NifColor4;
import nif.compound.NifTexCoord;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.TexClampMode;

public class BSEffectShaderProperty extends BSShaderProperty
{
	/**
	<niobject name="BSEffectShaderProperty" inherit="BSShaderProperty" stopcond="#BSVER# #GTE# 155 #AND# Name" module="BSMain" versions="#SKY_AND_LATER#">
        Bethesda effect shader property for Skyrim and later.
        <field name="Shader Flags 1" suffix="SK" type="SkyrimShaderPropertyFlags1" default="0x80000000" vercond="#NI_BS_LT_FO4#" />
        <field name="Shader Flags 2" suffix="SK" type="SkyrimShaderPropertyFlags2" default="0x20" vercond="#NI_BS_LT_FO4#" />
        <field name="Shader Flags 1" suffix="FO4" type="Fallout4ShaderPropertyFlags1" default="0x80000000" vercond="#BS_FO4#" />
        <field name="Shader Flags 2" suffix="FO4" type="Fallout4ShaderPropertyFlags2" default="0x20" vercond="#BS_FO4#" />
        <field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
        <field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
        <field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
        <field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
        <field name="UV Offset" type="TexCoord">Offset UVs</field>
        <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures</field>
        <field name="Source Texture"  type="SizedString">points to an external texture.</field>
        <field name="Unk Float" type="float" vercond="#BS_GTE_STF#" />
        <!-- TODO: Next 4 bytes requires bitfield, read as single int -->
        <field name="Texture Clamp Mode" type="byte" default="3">How to handle texture borders.</field>
        <field name="Lighting Influence" type="byte" default="255" />
        <field name="Env Map Min LOD" type="byte" range="0:16" />
        <field name="Unused Byte" type="byte" />
        <field name="Falloff Start Angle" type="float" default="1.0" range="#F_NRM#">At this cosine of angle falloff will be equal to Falloff Start Opacity</field>
        <field name="Falloff Stop Angle" type="float" default="1.0" range="#F_NRM#">At this cosine of angle falloff will be equal to Falloff Stop Opacity</field>
        <field name="Falloff Start Opacity" type="float" range="#F0_1#">Alpha falloff multiplier at start angle</field>
        <field name="Falloff Stop Opacity" type="float" range="#F0_1#">Alpha falloff multiplier at end angle</field>
        <field name="Refraction Power" type="float" vercond="#BS_F76#" />
        <field name="Base Color" type="Color4" default="#VEC4_ONE#">Base color</field>
        <field name="Base Color Scale" type="float" default="1.0" range="0.0:360.0">Multiplier for Base Color (RGB part)</field>
        <field name="Soft Falloff Depth" type="float" default="100.0" range="0.0:9999.0" />
        <field name="Greyscale Texture"  type="SizedString">Points to an external texture, used as palette for SLSF1_Greyscale_To_PaletteColor/SLSF1_Greyscale_To_PaletteAlpha.</field>
        <field name="Env Map Texture" type="SizedString" vercond="#BS_GTE_130#" />
        <field name="Normal Texture" type="SizedString" vercond="#BS_GTE_130#" />
        <field name="Env Mask Texture" type="SizedString" vercond="#BS_GTE_130#" />
        <field name="Environment Map Scale" type="float" default="1.0" range="0.01:20.0" vercond="#BS_GTE_130#" />
        <field name="Reflectance Texture" type="SizedString" vercond="#BS_GTE_F76#" />
        <field name="Lighting Texture" type="SizedString" vercond="#BS_GTE_F76#" />
        <field name="Emittance Color" type="Color3" vercond="#BS_GTE_F76#" />
        <field name="Emit Gradient Texture" type="SizedString" vercond="#BS_GTE_F76#" />
        <field name="Luminance" type="BSSPLuminanceParams" vercond="#BS_GTE_F76#" />
        <field name="Unknown Bytes" type="byte" length="7" vercond="#BS_GTE_STF#" />
        <field name="Unknown Floats" type="float" length="6" vercond="#BS_GTE_STF#" />
        <field name="Unknown Byte" type="byte" vercond="#BS_GTE_STF#" />
    </niobject>
	</niobject>

	 */
	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;

	public int NumSF1;

	public int NumSF2;

	public int[] SF1;

	public int[] SF2;	

	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public String SourceTexture;

	public TexClampMode TextureClampMode = TexClampMode.WRAP_S_WRAP_T;

	public float FalloffStartAngle;

	public float FalloffStopAngle;

	public float FalloffStartOpacity;

	public float FalloffStopOpacity;

	public NifColor4 BaseColor;

	public float BaseColorScale;

	public float SoftFalloffDepth;

	public String GreyscaleTexture;

	public String EnvMapTexture;

	public String NormalTexture;

	public String EnvMaskTexture;

	public float RefractionPower;

	public String ReflectanceTexture;

	public String LightingTexture;

	public NifColor3 EmittanceColor;

	public String EmitGradientTexture;

	public BSSPLuminanceParams Luminance;

	public byte LightingInfluence;

	public byte EnvMapMinLOD;

	public byte UnusedByte;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		// FO76 or somewhere this concept got introduced, so that object don't bother loading! crikey!
		//stopcond="#BSVER# #GTE# 155 #AND# Name"
		if(nifVer.BS_Version >= 155 && name.length() > 0)
			return success;// TODO: everything needs to be set to the defaults!

		if (nifVer.NI_BS_LTE_FO4() || nifVer.BS_FO4()) {
			// note these are FO4 flags as well if ver=fO4
			ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);
			ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);
		}
		
        //<field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
		if(nifVer.BS_GTE_132())
			NumSF1 = ByteConvert.readInt(stream);
        //<field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
		if(nifVer.BS_GTE_152())
			NumSF2 = ByteConvert.readInt(stream);
        //<field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
		if(nifVer.BS_GTE_132())
			SF1 = ByteConvert.readInts(NumSF1, stream);
        //<field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
		if(nifVer.BS_GTE_152())
			SF2 = ByteConvert.readInts(NumSF2, stream);

		UVOffSet = new NifTexCoord(stream);

		UVScale = new NifTexCoord(stream);

		SourceTexture = ByteConvert.readSizedString(stream);
		
		if(nifVer.BS_GTE_STF())
			ByteConvert.readFloat(stream);//unknownfloat

		//<!-- TODO: Next 4 bytes requires bitfield, read as single int -->
		//<field name="Texture Clamp Mode" type="byte" default="3">How to handle texture borders.</field>
        //<field name="Lighting Influence" type="byte" default="255" />
        //<field name="Env Map Min LOD" type="byte" range="0:16" />
        //<field name="Unused Byte" type="byte" />
		TextureClampMode = TexClampMode.values()[ByteConvert.readUnsignedByte(stream)]; 
		LightingInfluence = ByteConvert.readByte(stream);
		EnvMapMinLOD = ByteConvert.readByte(stream);
		UnusedByte = ByteConvert.readByte(stream);

		FalloffStartAngle = ByteConvert.readFloat(stream);
		FalloffStopAngle = ByteConvert.readFloat(stream);
		FalloffStartOpacity = ByteConvert.readFloat(stream);
		FalloffStopOpacity = ByteConvert.readFloat(stream);
		
		//<field name="Refraction Power" type="float" vercond="#BS_F76#" />
		if(nifVer.BS_GTE_F76())
			RefractionPower = ByteConvert.readFloat(stream);
		
	   
		BaseColor = new NifColor4(stream);//<field name="Base Color" type="Color4" default="#VEC4_ONE#">Base color</field>
		BaseColorScale = ByteConvert.readFloat(stream); //<field name="Base Color Scale" type="float" default="1.0" range="0.0:360.0">Multiplier for Base Color (RGB part)</field>
		SoftFalloffDepth = ByteConvert.readFloat(stream);
		GreyscaleTexture = ByteConvert.readSizedString(stream);		  
	  
		if (nifVer.BS_GTE_130()){
			EnvMapTexture = ByteConvert.readSizedString(stream);//e.g. Shared/Cubemaps/mipblur_DefaultOutside1.dds
			NormalTexture = ByteConvert.readSizedString(stream);//e.g. actors/bloatfly/bloatfly_n.dds
			EnvMaskTexture = ByteConvert.readSizedString(stream);
			EnvironmentMapScale = ByteConvert.readFloat(stream);
		}
		
		if(nifVer.BS_GTE_F76()) {			
			ReflectanceTexture = ByteConvert.readSizedString(stream);//<field name="Reflectance Texture" type="SizedString" vercond="#BS_GTE_F76#" />	       
			LightingTexture = ByteConvert.readSizedString(stream);//<field name="Lighting Texture" type="SizedString" vercond="#BS_GTE_F76#" />
			EmittanceColor = new NifColor3(stream);//<field name="Emittance Color" type="Color3" vercond="#BS_GTE_F76#" />			
			EmitGradientTexture = ByteConvert.readSizedString(stream);//<field name="Emit Gradient Texture" type="SizedString" vercond="#BS_GTE_F76#" />
			Luminance = new BSSPLuminanceParams(stream);//<field name="Luminance" type="BSSPLuminanceParams" vercond="#BS_GTE_F76#" />
		}
		
		if(nifVer.BS_GTE_STF()) {
			byte[] UnknownBytes = ByteConvert.readBytes(7, stream);//<field name="Unknown Bytes" type="byte" length="7" vercond="#BS_GTE_STF#" />
			float[] UnknownFloats = ByteConvert.readFloats(6, stream);//<field name="Unknown Floats" type="float" length="6" vercond="#BS_GTE_STF#" />
			byte UnknownByte = ByteConvert.readByte(stream);//<field name="Unknown Byte" type="byte" vercond="#BS_GTE_STF#" />
		}

		
		return success;
	}
}
