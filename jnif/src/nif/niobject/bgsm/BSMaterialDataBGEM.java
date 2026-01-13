package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class BSMaterialDataBGEM extends BSMaterial
{
	/**
	<struct name="BSMaterialDataBGEM" abstract="true" module="BSMain" versions="#FO4# #F76#">
        Bethesda material file (.bgem) for Fallout 4 and 76.
        --these are in BSMaterial
        <field name="Version" type="uint" />
        <field name="Shader Flags 1" type="Fallout76MaterialPropertyFlags1" />
        <field name="Shader Flags 2" type="Fallout76MaterialPropertyFlags3" /> --NOT THE SAME
        <field name="UV Offset" type="TexCoord">Offset UVs</field>
        <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see above.</field>
        <field name="Alpha" type="float" default="1.0" range="0.0:128.0">The material opacity (1=opaque). Greater than 1.0 is used to affect alpha falloff i.e. staying opaque longer based on vertex alpha and alpha mask.</field>
        <field name="Alpha Source Blend Mode" type="AlphaFunction" />
        <field name="Alpha Destination Blend Mode" type="AlphaFunction" />
        <field name="Alpha Test Threshold" type="byte" />
        <field name="Refraction Power" type="float" />
        --end in BSMaterial
        
        <field name="Write Mask" type="byte" cond="Version #GTE# 6" />
        <field name="Texture 0" type="SizedString">Source Texture</field>
        <field name="Texture 1" type="SizedString">Grayscale To Palette Texture</field>
        <field name="Texture 2" type="SizedString">Cube Map Texture</field>
        <field name="Texture 3" type="SizedString">Normal Map Texture</field>
        <field name="Texture 4" type="SizedString">Environment Mask Texture</field>
        <field name="Texture 5" type="SizedString" cond="Version #GTE# 10">Reflectance Texture</field>
        <field name="Texture 6" type="SizedString" cond="Version #GTE# 10">Lighting Texture</field>
        <field name="Texture 7" type="SizedString" cond="Version #GTE# 10">Emit Gradient Texture</field>
        <field name="Texture 8" type="SizedString" cond="Version #GTE# 21">Glass Roughness and Scratch Texture</field>
        <field name="Texture 9" type="SizedString" cond="Version #GTE# 21">Glass Dirt Overlay Texture</field>
        <field name="Glass Fresnel Color" type="Color3" cond="Shader Flags 2 #BITAND# 0x0200" />
        <field name="Glass Refraction Scale" type="float" cond="Shader Flags 2 #BITAND# 0x0200" />
        <field name="Glass Blur Scale Base" type="float" cond="Shader Flags 2 #BITAND# 0x0200" />
        <field name="Glass Blur Scale Factor" type="float" cond="(Shader Flags 2 #BITAND# 0x0200) #AND# (Version #GTE# 22)" />
        <field name="Environment Map Scale" type="float" default="1.0" range="0.01:20.0" />
        <field name="Base Color" type="Color3" default="#VEC3_ONE#">Base color</field>
        <field name="Base Color Scale" type="float" default="1.0" range="0.0:360.0">Multiplier for Base Color (RGB part)</field>
        <field name="Falloff Start Angle" type="float" default="1.0" range="#F_NRM#">At this cosine of angle falloff will be equal to Falloff Start Opacity</field>
        <field name="Falloff Stop Angle" type="float" default="1.0" range="#F_NRM#">At this cosine of angle falloff will be equal to Falloff Stop Opacity</field>
        <field name="Falloff Start Opacity" type="float" range="#F0_1#">Alpha falloff multiplier at start angle</field>
        <field name="Falloff Stop Opacity" type="float" range="#F0_1#">Alpha falloff multiplier at end angle</field>
        <field name="Lighting Influence" type="float" />
        <field name="Env Map Min LOD" type="byte" range="0:16" />
        <field name="Soft Falloff Depth" type="float" default="100.0" range="0.0:9999.0" />
        <field name="Emittance Color" type="Color3" cond="Version #GTE# 11" />
        <field name="Adaptive Emissive Exposure Offset" type="float" cond="Version #GTE# 15" />
        <field name="Adaptive Emissive Exposure Min" type="float" cond="Version #GTE# 15" />
        <field name="Adaptive Emissive Exposure Max" type="float" cond="Version #GTE# 15" />
        <field name="Is Modified" type="bool" />
    </struct>

    <bitflags name="Fallout76MaterialPropertyFlags3" storage="ushort" versions="#F76#">
        Fallout 76 Effect Material Property Flags
        <option bit="0" name="Environment_Mapping" />
        <option bit="1" name="Blood_Enabled" />
        <option bit="2" name="Effect_Lighting" />
        <option bit="3" name="Use_Falloff" />
        <option bit="4" name="RGB_Falloff" />
        <option bit="5" name="Grayscale_To_Palette_Alpha" />
        <option bit="6" name="Soft_Enabled" />
        <option bit="7" name="Glow_Map" />
        <option bit="8" name="Effect_PBR_Specular" />
        <option bit="9" name="Glass_Enabled" />
    </bitflags>

	 */

	public String BaseTexture;
	public String GrayscaleToPaletteTexture;
	public String CubeMapTexture;
	public String NormalTexture;
	public String EnvironmentMaskTexture;
	public String SpecularTexture;
	public String LightingTexture;
	public String GlowTexture;
	public byte EnvironmentMapping;
	public float EnvironmentMappingMaskScale;
	public byte bBloodEnabled;
	public byte bEffectLightingEnabled;
	public byte bFalloffEnabled;
	public byte bFalloffColorEnabled;
	public byte bGrayscaleToPaletteAlpha;
	public byte bSoftEnabled;
	public NifColor3 cBaseColor;
	public float fBaseColorScale;
	public float fFalloffStartAngle;
	public float fFalloffStopAngle;
	public float fFalloffStartOpacity;
	public float fFalloffStopOpacity;
	public float fLightingInfluence;
	public int iEnvmapMinLOD;
	public float fSoftDepth;

	public NifColor3 EmittanceColor;
	public float AdaptativeEmissive_ExposureOffset;
	public float AdaptativeEmissive_FinalExposureMin;
	public float AdaptativeEmissive_FinalExposureMax;
	public byte Glowmap;
	public byte EffectPbrSpecular;


	@Override
	public void readFile(ByteBuffer stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);
		
		BaseTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		GrayscaleToPaletteTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		CubeMapTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		NormalTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		EnvironmentMaskTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

		if (Version >= 10) {
			SpecularTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			LightingTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		}

		if (Version >= 10) {
			EnvironmentMapping = ByteConvert.readByte(stream);
			EnvironmentMappingMaskScale = ByteConvert.readFloat(stream);
		}
		

		bBloodEnabled = ByteConvert.readByte(stream);
		bEffectLightingEnabled = ByteConvert.readByte(stream);
		bFalloffEnabled = ByteConvert.readByte(stream);
		bFalloffColorEnabled = ByteConvert.readByte(stream);
		bGrayscaleToPaletteAlpha = ByteConvert.readByte(stream);
		bSoftEnabled = ByteConvert.readByte(stream);
		cBaseColor = new NifColor3(stream);
		fBaseColorScale = ByteConvert.readFloat(stream);
		fFalloffStartAngle = ByteConvert.readFloat(stream);
		fFalloffStopAngle = ByteConvert.readFloat(stream);
		fFalloffStartOpacity = ByteConvert.readFloat(stream);
		fFalloffStopOpacity = ByteConvert.readFloat(stream);
		fLightingInfluence = ByteConvert.readFloat(stream);
		iEnvmapMinLOD = ByteConvert.readUnsignedByte(stream);
		fSoftDepth = ByteConvert.readFloat(stream);
		
		
		if (Version >= 11) {
			EmittanceColor = new NifColor3(stream);
		}

		if (Version >= 15) {
			AdaptativeEmissive_ExposureOffset = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMin = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMax = ByteConvert.readFloat(stream);
		}

		if (Version >= 16) {
			Glowmap = ByteConvert.readByte(stream);
		}

		if (Version >= 20) {
			EffectPbrSpecular = ByteConvert.readByte(stream);
		}
	}
}
