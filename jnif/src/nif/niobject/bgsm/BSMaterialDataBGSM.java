package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class BSMaterialDataBGSM extends BSMaterial
{
	/**
    <struct name="BSMaterialDataBGSM" abstract="true" module="BSMain" versions="#FO4# #F76#">
        Bethesda material file (.bgsm) for Fallout 4 and 76.
        --these are in BSMaterial
        <field name="Version" type="uint" />
        <field name="Shader Flags 1" type="Fallout76MaterialPropertyFlags1" />
        <field name="Shader Flags 2" type="Fallout76MaterialPropertyFlags2" /> --NOT THE SAME!!
        <field name="UV Offset" type="TexCoord">Offset UVs</field>
        <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see above.</field>
        <field name="Alpha" type="float" default="1.0" range="0.0:128.0">The material opacity (1=opaque). Greater than 1.0 is used to affect alpha falloff i.e. staying opaque longer based on vertex alpha and alpha mask.</field>
        <field name="Alpha Source Blend Mode" type="AlphaFunction" />
        <field name="Alpha Destination Blend Mode" type="AlphaFunction" />
        <field name="Alpha Test Threshold" type="byte" />
        <field name="Refraction Power" type="float" />
        --end in BSMaterial
        
        <field name="Environment Map Scale" type="float" default="1.0" range="#F0_10#" cond="Version #LT# 10">Scales the intensity of the environment/cube map.</field>
        <field name="Write Mask" type="byte" cond="Version #GTE# 6" />
        <field name="Texture 0" type="SizedString">Diffuse Texture</field>
        <field name="Texture 1" type="SizedString">Normal Map Texture</field>
        <field name="Texture 2" type="SizedString">FO4 Specular Map Texture</field>
        <field name="Texture 3" type="SizedString">Grayscale To Palette Texture</field>
        <field name="Texture 4" type="SizedString">FO4 Cube Map or FO76 Glow Map Texture</field>
        <field name="Texture 5" type="SizedString">FO4 Glow Map or Environment Mask Texture</field>
        <field name="Texture 6" type="SizedString">FO76 Reflectance Texture</field>
        <field name="Texture 7" type="SizedString">FO76 Lighting Texture</field>
        <field name="Texture 8" type="SizedString" />
        <field name="Texture 9" type="SizedString" cond="Version #GTE# 17" />
        <field name="Rimlight Power" type="float" default="#FLT_MAX#" cond="Version #LT# 8" />
        <field name="Backlight Power" type="float" range="#F0_1000#" cond="Version #LT# 8" />
        <field name="Subsurface Rolloff" type="float" default="0.0" range="#F0_10#" cond="Version #LT# 8" />
        <field name="Translucency Subsurface Color" type="Color3" cond="Version #GTE# 8" />
        <field name="Translucency Transmissive Scale" type="float" cond="Version #GTE# 8" />
        <field name="Translucency Turbulence" type="float" cond="Version #GTE# 8" />
        <field name="Specular Color" type="Color3" default="#VEC3_ONE#">Adds a colored highlight.</field>
        <field name="Specular Strength" type="float" default="1.0" range="#F0_10#">Brightness of specular highlight. (0=not visible)</field>
        <field name="Smoothness" type="float" default="1.0" range="#F0_10#">The base roughness, multiplied by the smoothness map.</field>
        <field name="Fresnel Power" type="float" default="5.0" range="#F_PNZ#" />
        <field name="Wetness" type="BGSMWetnessParams" arg="Version" />
        <field name="Porosity Value" type="float" cond="Version #GTE# 9" />
        <field name="Root Material" type="SizedString" />
        <field name="Emissive Color" type="Color3" default="#VEC3_ZERO#" cond="Shader Flags 2 #BITAND# 0x0100">Glow color</field>
        <field name="Emissive Multiple" type="float" default="1.0" range="#F0_10#">Multiplied emissive colors</field>
        <field name="Luminance" type="BSSPLuminanceParams" cond="Version #GTE# 12" />
        <field name="Hair Tint Color" type="Color3" />
        <field name="Displacement Texture Bias" type="float" cond="Version #LT# 3" />
        <field name="Displacement Texture Scale" type="float" cond="Version #LT# 3" />
        <field name="Tessellation Pn Scale" type="float" cond="Version #LT# 3" />
        <field name="Tessellation Base Factor" type="float" cond="Version #LT# 3" />
        <field name="Tessellation Fade Distance" type="float" cond="Version #LT# 3" />
        <field name="Grayscale to Palette Scale" type="float" default="1.0" range="#F0_1#" />
        <field name="Terrain Threshold Falloff" type="float" cond="Shader Flags 2 #BITAND# 0x01000000" />
        <field name="Terrain Tiling Distance" type="float" cond="Shader Flags 2 #BITAND# 0x01000000" />
        <field name="Terrain Rotation Angle" type="float" cond="Shader Flags 2 #BITAND# 0x01000000" />
        <field name="Is Modified" type="bool" />
    </struct>
    
    
 <bitflags name="Fallout76MaterialPropertyFlags2" storage="uint" versions="#F76#">
        Fallout 76 Shader Material Property Flags
        <option bit="0" name="Enable_Editor_Alpha_Ref" />
        <option bit="1" name="Translucency" />
        <option bit="2" name="Translucency_Thick_Object" />
        <option bit="3" name="Translucency_Mix_Albedo_With_SS_Color" />
        <option bit="4" name="Specular" />
        <option bit="5" name="PBR" />
        <option bit="6" name="Custom_Porosity" />
        <option bit="7" name="Anisotropic_Lighting" />
        <option bit="8" name="Emit_Enabled" />
        <option bit="9" name="Model_Space_Normals" />
        <option bit="10" name="External_Emittance" />
        <option bit="11" name="Use_Adaptive_Emissive" />
        <option bit="12" name="Receive_Shadows" />
        <option bit="13" name="Hide_Secret" />
        <option bit="14" name="Cast_Shadows" />
        <option bit="15" name="Dissolve_Fade" />
        <option bit="16" name="Assume_Shadowmask" />
        <option bit="17" name="Glow_Map" />
        <option bit="18" name="Hair" />
        <option bit="19" name="Tree" />
        <option bit="20" name="FaceGen" />
        <option bit="21" name="Skin_Tint" />
        <option bit="22" name="Tessellate" />
        <option bit="23" name="Skew_Specular_Alpha" />
        <option bit="24" name="Terrain" />
        <option bit="25" name="Rim_Lighting" />
        <option bit="26" name="Subsurface_Lighting" />
        <option bit="27" name="Back_Lighting" />
        <option bit="28" name="Window_Environment_Mapping" />
        <option bit="29" name="Eye_Environment_Mapping" />
    </bitflags>
	 */
	public String DiffuseTexture;
	public String NormalTexture;
	public String SmoothSpecTexture;
	public String GreyscaleTexture;
	public String GlowTexture;
	public String WrinklesTexture;
	public String SpecularTexture;
	public String LightingTexture;
	public String FlowTexture;
	public String DistanceFieldAlphaTexture;
	public String EnvmapTexture;
	public String InnerLayerTexture;
	public String DisplacementTexture;
	
	public byte bEnableEditorAlphaRef;
	public byte bRimLighting;
	public float fRimPower;
	public float fBacklightPower;
	public byte bSubsurfaceLighting;
	public float fSubsurfaceLightingRolloff;
	public byte bSpecularEnabled;
	public NifColor3 cSpecularColor;
	public float fSpecularMult;
	public float fSmoothness;
	public float fFresnelPower;
	public float fWetnessControl_SpecScale;
	public float fWetnessControl_SpecPowerScale;
	public float fWetnessControl_SpecMinvar;
	public float fWetnessControl_EnvMapScale;
	public float fWetnessControl_FresnelPower;
	public float fWetnessControl_Metalness;
	public String sRootMaterialPath;
	public byte bAnisoLighting;
	public byte bEmitEnabled;
	public NifColor3 cEmittanceColor;
	public float fEmittanceMult;
	public byte bModelSpaceNormals;
	public byte bExternalEmittance;
	public byte bBackLighting;
	public byte bReceiveShadows;
	public byte bHideSecret;
	public byte bCastShadows;
	public byte bDissolveFade;
	public byte bAssumeShadowmask;
	public byte bGlowmap;
	public byte bEnvironmentMappingWindow;
	public byte bEnvironmentMappingEye;
	public byte bHair;
	public NifColor3 cHairTintColor;
	public byte bTree;
	public byte bFacegen;
	public byte bSkinTint;
	public byte bTessellate;
	public float fDisplacementTextureBias;
	public float fDisplacementTextureScale;
	public float fTessellationPnScale;
	public float fTessellationBaseFactor;
	public float fTessellationFadeDistance;
	public float fGrayscaleToPaletteScale;
	public byte bSkewSpecularAlpha;

	public byte Translucency;
	public byte TranslucencyThickObject;
	public byte TranslucencyMixAlbedoWithSubsurfaceColor;
	public NifColor3 TranslucencySubsurfaceColor;
	public float TranslucencyTransmissiveScale;
	public float TranslucencyTurbulence;
	public byte PBR;
	public byte CustomPorosity;
	public float PorosityValue;
	public float LumEmittance;
	public byte UseAdaptativeEmissive;
	public float AdaptativeEmissive_ExposureOffset;
	public float AdaptativeEmissive_FinalExposureMin;
	public float AdaptativeEmissive_FinalExposureMax;
	public byte Terrain;
	public int UnkInt1;
	public float TerrainThresholdFalloff;
	public float TerrainTilingDistance;
	public float TerrainRotationAngle;

	@Override
	public void readFile(ByteBuffer stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);

		DiffuseTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		NormalTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		SmoothSpecTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		GreyscaleTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

		if (Version > 2) {
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			WrinklesTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			SpecularTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			LightingTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			FlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

			if (Version >= 17) {
				DistanceFieldAlphaTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			}
		} else {
			EnvmapTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			InnerLayerTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			WrinklesTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			DisplacementTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		}

		bEnableEditorAlphaRef = ByteConvert.readByte(stream);
		
		if (Version >= 8) {
			Translucency = ByteConvert.readByte(stream);
			TranslucencyThickObject = ByteConvert.readByte(stream);
			TranslucencyMixAlbedoWithSubsurfaceColor = ByteConvert.readByte(stream);
			TranslucencySubsurfaceColor = new NifColor3(stream);
			TranslucencyTransmissiveScale = ByteConvert.readFloat(stream);
			TranslucencyTurbulence = ByteConvert.readFloat(stream);
		} else {
			bRimLighting = ByteConvert.readByte(stream);
			fRimPower = ByteConvert.readFloat(stream);
			fBacklightPower = ByteConvert.readFloat(stream);

			bSubsurfaceLighting = ByteConvert.readByte(stream);
			fSubsurfaceLightingRolloff = ByteConvert.readFloat(stream);
		}
		
		bSpecularEnabled = ByteConvert.readByte(stream);
		cSpecularColor = new NifColor3(stream);
		fSpecularMult = ByteConvert.readFloat(stream);
		fSmoothness = ByteConvert.readFloat(stream);
		
		fFresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_SpecScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecPowerScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecMinvar = ByteConvert.readFloat(stream);
		if (Version < 10) {
			fWetnessControl_EnvMapScale = ByteConvert.readFloat(stream);
		}
		fWetnessControl_FresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_Metalness = ByteConvert.readFloat(stream);

		if (Version > 2) {
			PBR = ByteConvert.readByte(stream);

			if (Version >= 9) {
				CustomPorosity = ByteConvert.readByte(stream);
				PorosityValue = ByteConvert.readFloat(stream);
			}
		}
		
		
		sRootMaterialPath = ByteConvert.readSizedString(stream);

		bAnisoLighting = ByteConvert.readByte(stream);
		bEmitEnabled = ByteConvert.readByte(stream);

		if (bEmitEnabled != 0)
			cEmittanceColor = new NifColor3(stream);

		fEmittanceMult = ByteConvert.readFloat(stream);
		bModelSpaceNormals = ByteConvert.readByte(stream);
		bExternalEmittance = ByteConvert.readByte(stream);
		if (Version >= 12) {
			LumEmittance = ByteConvert.readFloat(stream);
		}

		if (Version >= 13) {
			UseAdaptativeEmissive = ByteConvert.readByte(stream);
			AdaptativeEmissive_ExposureOffset = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMin = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMax = ByteConvert.readFloat(stream);
		}

		if (Version < 8) {
			bBackLighting = ByteConvert.readByte(stream);
		}
		
		bReceiveShadows = ByteConvert.readByte(stream);
		bHideSecret = ByteConvert.readByte(stream);
		bCastShadows = ByteConvert.readByte(stream);
		bDissolveFade = ByteConvert.readByte(stream);
		bAssumeShadowmask = ByteConvert.readByte(stream);
		
		bGlowmap = ByteConvert.readByte(stream);
		
		if (Version < 7) {
			bEnvironmentMappingWindow = ByteConvert.readByte(stream);
			bEnvironmentMappingEye = ByteConvert.readByte(stream);
		}
		
		bHair = ByteConvert.readByte(stream);
		cHairTintColor = new NifColor3(stream);

		bTree = ByteConvert.readByte(stream);
		bFacegen = ByteConvert.readByte(stream);
		bSkinTint = ByteConvert.readByte(stream);
		bTessellate = ByteConvert.readByte(stream);
		
		if (Version < 3) {
			fDisplacementTextureBias = ByteConvert.readFloat(stream);
			fDisplacementTextureScale = ByteConvert.readFloat(stream);
			fTessellationPnScale = ByteConvert.readFloat(stream);
			fTessellationBaseFactor = ByteConvert.readFloat(stream);
			fTessellationFadeDistance = ByteConvert.readFloat(stream);
		}
		
		fGrayscaleToPaletteScale = ByteConvert.readFloat(stream);
		
		if (Version >= 1) {
			bSkewSpecularAlpha = ByteConvert.readByte(stream);
		}
		
		if (Version >= 3) {
			Terrain = ByteConvert.readByte(stream);

			if (Terrain != 0) {
				if (Version == 3) {
					UnkInt1 = ByteConvert.readInt(stream);
				}

				TerrainThresholdFalloff = ByteConvert.readFloat(stream);
				TerrainTilingDistance = ByteConvert.readFloat(stream);
				TerrainRotationAngle = ByteConvert.readFloat(stream);
			}
		}
	}
}
