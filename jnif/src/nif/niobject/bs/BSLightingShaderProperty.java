package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.BSSPLuminanceParams;
import nif.compound.BSSPTranslucencyParams;
import nif.compound.BSSPWetnessParams;
import nif.compound.NifColor3;
import nif.compound.NifColor4;
import nif.compound.NifTexCoord;
import nif.compound.NifVector3;
import nif.compound.NifVector4;
import nif.enums.BSShaderType155;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.TexClampMode;

public class BSLightingShaderProperty extends BSShaderProperty {
	/**
	 * 
	 * <niobject name="BSLightingShaderProperty" inherit="BSShaderProperty" stopcond="#BSVER# #GTE# 155 #AND# Name"
	 * module="BSMain" versions="#SKY_AND_LATER#"> Bethesda shader property for Skyrim and later.
	 * <field name="Shader Flags 1" suffix="SK" type="SkyrimShaderPropertyFlags1" vercond="#NI_BS_LT_FO4#" default=
	 * "0x82400301">Skyrim Shader Flags for setting render/shader options.</field>
	 * <field name="Shader Flags 2" suffix="SK" type="SkyrimShaderPropertyFlags2" vercond="#NI_BS_LT_FO4#" default=
	 * "0x8021">Skyrim Shader Flags for setting render/shader options.</field>
	 * <field name="Shader Flags 1" suffix="FO4" type="Fallout4ShaderPropertyFlags1" vercond="#BS_FO4#" default=
	 * "0x80400201">Fallout 4 Shader Flags. Mostly overridden if "Name" is a path to a BGSM/BGEM file.</field>
	 * <field name="Shader Flags 2" suffix="FO4" type="Fallout4ShaderPropertyFlags2" vercond="#BS_FO4#" default=
	 * "1">Fallout 4 Shader Flags. Mostly overridden if "Name" is a path to a BGSM/BGEM file.</field>
	 * <field name="Shader Type" type="BSShaderType155" vercond="#BS_GTE_F76#" />
	 * <field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
	 * <field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
	 * <field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
	 * <field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
	 * <field name="UV Offset" type="TexCoord">Offset UVs</field>
	 * <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see
	 * above.</field> <field name="Texture Set" type="Ref" template="BSShaderTextureSet">Texture Set, can have override
	 * in an esm/esp</field> <field name="Emissive Color" type="Color3" default="#VEC3_ZERO#">Glow color and
	 * alpha</field> <field name="Emissive Multiple" type="float" default="1.0" range="#F0_10#">Multiplied emissive
	 * colors</field> <field name="Root Material" type="NiFixedString" vercond="#BS_GTE_130#" />
	 * <field name="Unk Float" type="float" vercond="#BS_GTE_STF#" />
	 * <field name="Texture Clamp Mode" type="TexClampMode" default="WRAP_S_WRAP_T">How to handle texture
	 * borders.</field> <field name="Alpha" type="float" default="1.0" range="0.0:128.0">The material opacity
	 * (1=opaque). Greater than 1.0 is used to affect alpha falloff i.e. staying opaque longer based on vertex alpha and
	 * alpha mask.</field> <field name="Refraction Strength" type="float" range="#F0_1#">The amount of distortion. **Not
	 * based on physically accurate refractive index** (0=none)</field>
	 * <field name="Glossiness" type="float" default="80.0" range="#F0_999#" vercond="#NI_BS_LT_FO4#">The material
	 * specular power, or glossiness.</field>
	 * <field name="Smoothness" type="float" default="1.0" range="#F0_1#" vercond="#BS_GTE_130#">The base roughness,
	 * multiplied by the smoothness map.</field> <field name="Specular Color" type="Color3" default="#VEC3_ONE#">Adds a
	 * colored highlight.</field> <field name="Specular Strength" type="float" default="1.0" range="#F0_10#">Brightness
	 * of specular highlight. (0=not visible)</field>
	 * <field name="Lighting Effect 1" type="float" default="0.3" range="#F0_10#" vercond="#NI_BS_LT_FO4#">Controls
	 * strength for envmap/backlight/rim/softlight lighting effect?</field>
	 * <field name="Lighting Effect 2" type="float" default="2.0" range="#F0_1000#" vercond="#NI_BS_LT_FO4#">Controls
	 * strength for envmap/backlight/rim/softlight lighting effect?</field>
	 * <field name="Subsurface Rolloff" type="float" default="0.0" range="#F0_10#" vercond="#BS_FO4_2#" />
	 * <field name="Rimlight Power" type="float" default="#FLT_MAX#" vercond="#BS_FO4_2#" />
	 * <field name="Backlight Power" type="float" range="#F0_1000#" cond="(Rimlight Power #GTE# #FLT_MAX#) #AND#
	 * (Rimlight Power #LT# #FLT_INF#)" vercond="#BS_FO4_2#" />
	 * <field name="Grayscale to Palette Scale" type="float" default="1.0" range="#F0_1#" vercond="#BS_GTE_130#" />
	 * <field name="Fresnel Power" type="float" default="5.0" range="#F_PNZ#" vercond="#BS_GTE_130#" />
	 * <field name="Wetness" type="BSSPWetnessParams" vercond="#BS_GTE_130#" />
	 * 
	 * //this should be BS_GTE_130 and so in FO76 and STF, issue if I correct it
	 * <field name="Luminance" type="BSSPLuminanceParams" vercond="#BS_GTE_STF#" /> // #BS_GTE_130#
	 * <field name="Do Translucency" type="bool" vercond="#BS_F76#" /> // #BS_GTE_130#
	 * <field name="Translucency" type="BSSPTranslucencyParams" vercond="#BS_F76#" cond="Do Translucency" /> //
	 * #BS_GTE_130# <field name="Has Texture Arrays" type="byte" vercond="#BS_F76#" /> // #BS_GTE_130#
	 * <field name="Num Texture Arrays" type="uint" vercond="#BS_F76#" cond="Has Texture Arrays" /> // #BS_GTE_130#
	 * <field name="Texture Arrays" type="BSTextureArray" length="Num Texture Arrays" vercond="#BS_F76#" cond="Has
	 * Texture Arrays" />
	 * 
	 * <field name="Unk Float 1" type="float" vercond="#BS_GTE_STF#"/>
	 * <field name="Unk Float 2" type="float" vercond="#BS_GTE_STF#"/>
	 * <field name="Unk Short 1" type="ushort" vercond="#BS_GTE_STF#"/>
	 * 
	 * <field name="Environment Map Scale" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 1" vercond=
	 * "#NI_BS_LTE_FO4#">Scales the intensity of the environment/cube map.</field>
	 * <field name="Use Screen Space Reflections" type="bool" cond="Shader Type == 1" vercond="#BS_FO4_2#" />
	 * <field name="Wetness Control: Use SSR" type="bool" cond="Shader Type == 1" vercond="#BS_FO4_2#" />
	 * 
	 * <field name="Skin Tint Color" type="Color4" vercond="#BS_GTE_F76#" cond="Shader Type == 4" />
	 * <field name="Hair Tint Color" type="Color3" vercond="#BS_GTE_F76#" cond="Shader Type == 5" />
	 * 
	 * <field name="Skin Tint Color" type="Color3" default="#VEC3_ONE#" vercond="#NI_BS_LTE_FO4#" cond="Shader Type ==
	 * 5">Tints the base texture. Overridden by game settings.</field>
	 * <field name="Skin Tint Alpha" type="float" default="1.0" vercond="#BS_FO4_2#" cond="Shader Type == 5" />
	 * <field name="Hair Tint Color" type="Color3" default="#VEC3_ONE#" vercond="#NI_BS_LTE_FO4#" cond="Shader Type ==
	 * 6">Tints the base texture. Overridden by game settings.</field>
	 * 
	 * <field name="Max Passes" type="float" default="4.0" range="1.0:320.0" cond="Shader Type == 7" />
	 * <field name="Scale" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 7" />
	 * <field name="Parallax Inner Layer Thickness" type="float" default="5.0" range="5.0:500.0" cond="Shader Type ==
	 * 11">How far from the surface the inner layer appears to be.</field>
	 * <field name="Parallax Refraction Scale" type="float" default="0.25" range="#F0_1#" cond="Shader Type == 11">Depth
	 * of inner parallax layer effect.</field>
	 * <field name="Parallax Inner Layer Texture Scale" type="TexCoord" cond="Shader Type == 11">Scales the inner
	 * parallax layer texture.</field>
	 * <field name="Parallax Envmap Strength" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 11">How
	 * strong the environment/cube map is.</field>
	 * <field name="Sparkle Parameters" type="Vector4" cond="Shader Type == 14">CK lists "snow material" when
	 * used.</field>
	 * <field name="Eye Cubemap Scale" type="float" default="1.3" range="#F0_10#" cond="Shader Type == 16">Eye cubemap
	 * scale</field> <field name="Left Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set
	 * center for left eye cubemap</field>
	 * <field name="Right Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set center for right
	 * eye cubemap</field> </niobject>
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public SkyrimShaderPropertyFlags1	ShaderFlags1;
	public SkyrimShaderPropertyFlags2	ShaderFlags2;
	public NifTexCoord					UVOffSet;
	public NifTexCoord					UVScale;
	public NifRef						TextureSet;
	public NifColor3					EmissiveColor;
	public float						EmissiveMultiple		= 1.0f;
	public TexClampMode					TextureClampMode		= TexClampMode.WRAP_S_WRAP_T;
	public float						Alpha					= 1.0f;
	public float						RefractionStrength;
	public float						Glossiness				= 80.0f;						//LOAD_USER_VER2 == 130 0.5?? not out of 1000 but just 0-1??
	public float						Smoothness				= 1.0f;							//glossiness renamed
	public NifColor3					SpecularColor;
	public float						SpecularStrength		= 1.0f;
	public float						LightingEffect1			= 0.3f;
	public float						LightingEffect2			= 2.0f;
	public NifColor3					SkinTintColor;
	public NifColor4					SkinTintColor2;
	public float						SkinTintAlpha;
	public NifColor3					HairTintColor;
	public float						MaxPasses;
	public float						Scale;
	public float						ParallaxInnerLayerThickness;
	public float						ParallaxRefractionScale;
	public NifTexCoord					ParallaxInnerLayerTextureScale;
	public float						ParallaxEnvmapStrength;
	public NifVector4					SparkleParamaters;
	public float						EyeCubemapScale;
	public NifVector3					LeftEyeReflectionCenter;
	public NifVector3					RightEyeReflectionCenter;

	//FO4 onwards
	public String						RootMaterial;
	public float						SubsurfaceRolloff		= 0.0f;
	public float						RimlightPower			= Float.MAX_VALUE;
	public float						BacklightPower;
	public float						GrayscaletoPaletteScale	= 1.0f;
	public float						FresnelPower			= 5.0f;
	public BSSPWetnessParams			Wetness;

	public int							ShaderType155;											//type="BSShaderType155"
	public int							NumSF1;
	public int							NumSF2;
	public int[]						SF1;
	public int[]						SF2;
	public int							ShaderType2;
	public BSSPLuminanceParams			Luminance;
	public boolean						DoTranslucency;
	public BSSPTranslucencyParams		Translucency;
	public byte							HasTextureArrays;
	public int							NumTextureArrays;
	public String[]						TextureArrays;
	public boolean						UseScreenSpaceReflections;
	public boolean						WetnessControlUseSSR;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException {
		boolean success = super.readFromStream(stream, nifVer);

		// FO76 or somewhere this concept got introduced, so that object don't bother loading! crikey!
		//stopcond="#BSVER# #GTE# 155 #AND# Name"
		if (nifVer.BS_Version >= 155 && name.length() > 0)
			return success;

		//<field name="Material" type="BSLayeredMaterial" abstract="true" vercond="#BS_GTE_STF#" cond="$Name">Starfield material (.mat).</field>
		//Ok so possibly the abstract marker means go find a find using the name field, so just a wee name chekc instead
		if (nifVer.BS_GTE_STF() && name.length() > 0) {
			if (!name.toLowerCase().endsWith(".mat"))
				System.out.println("bad material name " + name);
		}
		//<field name="Material" type="BSMaterialDataBGSM" abstract="true" vercond="#BS_FO4_F76#" cond="$Name">Fallout 4 or 76 material (.bgsm).</field>
		if (nifVer.BS_FO4_F76() && name.length() > 0) {
			if (!name.toLowerCase().endsWith(".bgsm"))
				System.out.println("bad material name " + name);
		}

		//<field name="Shader Property Data" type="BSLightingShaderPropertyDataGTEFO76" vercond="#BS_GTE_F76#" cond="!$Name">Shader property data when not using a material file, deprecated after Fallout 76.</field>
		if (nifVer.BS_GTE_F76() && name.length() == 0) {
			//Ok so the definition of the FO76 data is just a erroneously pulled out cut of this class
			// and it needs putting back together if there are any updates

			// so I am here, this file seems wrong for STF so I'm going to make a mash up
			//original and BSLightingShaderPropertyDataGTEFO76 definitions are are the bottom, my chimera is above! 
		}
		//<field name="Shader Flags 1" suffix="SK" type="SkyrimShaderPropertyFlags1" vercond="#NI_BS_LT_FO4#" default="0x82400301">Skyrim Shader Flags for setting render/shader options.</field>
		//<field name="Shader Flags 2" suffix="SK" type="SkyrimShaderPropertyFlags2" vercond="#NI_BS_LT_FO4#" default="0x8021">Skyrim Shader Flags for setting render/shader options.</field>
		if (nifVer.NI_BS_LT_FO4()) {
			ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);
			ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);
		}
		//<field name="Shader Flags 1" suffix="FO4" type="Fallout4ShaderPropertyFlags1" vercond="#BS_FO4#" default="0x80400201">Fallout 4 Shader Flags. Mostly overridden if "Name" is a path to a BGSM/BGEM file.</field>
		//<field name="Shader Flags 2" suffix="FO4" type="Fallout4ShaderPropertyFlags2" vercond="#BS_FO4#" default="1">Fallout 4 Shader Flags. Mostly overridden if "Name" is a path to a BGSM/BGEM file.</field>
		if (nifVer.BS_FO4()) {
			//TODO: need to create FO4 flags
			ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);
			ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);
		}

		//<field name="Shader Type" type="BSShaderType155" vercond="#BS_GTE_F76#" />
		if (nifVer.BS_GTE_F76())
			ShaderType = BSShaderType155.load(stream);
		//<field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
		if (nifVer.BS_GTE_132())
			NumSF1 = ByteConvert.readInt(stream);
		//<field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
		if (nifVer.BS_GTE_152())
			NumSF2 = ByteConvert.readInt(stream);
		//<field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
		if (nifVer.BS_GTE_132())
			SF1 = ByteConvert.readInts(NumSF1, stream);
		//<field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
		if (nifVer.BS_GTE_152())
			SF2 = ByteConvert.readInts(NumSF2, stream);

		UVOffSet = new NifTexCoord(stream);
		UVScale = new NifTexCoord(stream);
		TextureSet = new NifRef(BSShaderTextureSet.class, stream);
		EmissiveColor = new NifColor3(stream);
		EmissiveMultiple = ByteConvert.readFloat(stream);
		if (nifVer.BS_GTE_130())
			RootMaterial = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.BS_GTE_STF())
			ByteConvert.readFloat(stream);//unknownfloat

		TextureClampMode = TexClampMode.load(stream);
		Alpha = ByteConvert.readFloat(stream);
		RefractionStrength = ByteConvert.readFloat(stream);
		if (nifVer.NI_BS_LT_FO4())
			Glossiness = ByteConvert.readFloat(stream);
		if (nifVer.BS_GTE_130())
			Smoothness = ByteConvert.readFloat(stream);

		SpecularColor = new NifColor3(stream);
		SpecularStrength = ByteConvert.readFloat(stream);

		if (nifVer.NI_BS_LT_FO4()) {
			LightingEffect1 = ByteConvert.readFloat(stream);
			LightingEffect2 = ByteConvert.readFloat(stream);
		}

		if (nifVer.BS_FO4_2()) {
			SubsurfaceRolloff = ByteConvert.readFloat(stream);
			//<field name="Rimlight Power" type="float" default="#FLT_MAX#" vercond="#BS_FO4_2#" />
			RimlightPower = ByteConvert.readFloat(stream);
			//TODO: these conditions are super tricky
			//<field name="Backlight Power" type="float" range="#F0_1000#" cond="(Rimlight Power #GTE# #FLT_MAX#) #AND# (Rimlight Power #LT# #FLT_INF#)" vercond="#BS_FO4_2#" />
			if (RimlightPower >= Float.MAX_VALUE && RimlightPower < Float.POSITIVE_INFINITY)
				BacklightPower = ByteConvert.readFloat(stream);
		}

		if (nifVer.BS_GTE_130()) {
			GrayscaletoPaletteScale = ByteConvert.readFloat(stream);
			FresnelPower = ByteConvert.readFloat(stream);
			Wetness = new BSSPWetnessParams(stream, nifVer);
		}

		// <field name="Luminance" type="BSSPLuminanceParams" vercond="#BS_GTE_STF#" />  		
		if (nifVer.BS_GTE_STF())  
			Luminance = new BSSPLuminanceParams(stream);
		
		// <field name="Do Translucency" type="bool" vercond="#BS_F76#" />  
		// <field name="Translucency" type="BSSPTranslucencyParams" vercond="#BS_F76#" cond="Do Translucency" /> 
		// <field name="Has Texture Arrays" type="byte" vercond="#BS_F76#" />  
		// <field name="Num Texture Arrays" type="uint" vercond="#BS_F76#" cond="Has Texture Arrays" /> 
		// <field name="Texture Arrays" type="BSTextureArray" length="Num Texture Arrays" vercond="#BS_F76#" cond="Has/ Texture Arrays" />
		if (nifVer.BS_F76()) {
			DoTranslucency = ByteConvert.readByte(stream) != 0;
			if (DoTranslucency)
				Translucency = new BSSPTranslucencyParams(stream);

			HasTextureArrays = ByteConvert.readByte(stream);
			if (HasTextureArrays != 0) {
				NumTextureArrays = ByteConvert.readInt(stream);
				TextureArrays = new String[NumTextureArrays];
				for (int i = 0; i < NumTextureArrays; i++) {
					TextureArrays[i] = ByteConvert.readSizedString(stream);
				}
			}
		}

		if (nifVer.BS_GTE_STF()) {
			ByteConvert.readFloat(stream);//unknownfloat
			ByteConvert.readFloat(stream);//unknownfloat
			ByteConvert.readShort(stream);//unknownshort 
		}

		if (ShaderType.getType() == 1) {
			if (nifVer.NI_BS_LTE_FO4()) {
				//<field name="Environment Map Scale" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 1" vercond="#NI_BS_LTE_FO4#">Scales the intensity of the environment/cube map.</field>
				EnvironmentMapScale = ByteConvert.readFloat(stream);
			}
			if (nifVer.BS_FO4_2()) {
				//<field name="Use Screen Space Reflections" type="bool" cond="Shader Type == 1" vercond="#BS_FO4_2#" />
				UseScreenSpaceReflections = ByteConvert.readByte(stream) != 0;
				//<field name="Wetness Control: Use SSR" type="bool" cond="Shader Type == 1" vercond="#BS_FO4_2#" />
				WetnessControlUseSSR = ByteConvert.readByte(stream) != 0;
			}
		}

		else if (ShaderType.getType() == 4) {
			if (nifVer.BS_GTE_F76()) {
				//<field name="Skin Tint Color" type="Color4" vercond="#BS_GTE_F76#" cond="Shader Type == 4" />
				SkinTintColor2 = new NifColor4(stream);
			}
		} else if (ShaderType.getType() == 5) {
			if (nifVer.BS_GTE_F76()) {
				//<field name="Hair Tint Color" type="Color3" vercond="#BS_GTE_F76#" cond="Shader Type == 5" />
				HairTintColor = new NifColor3(stream);
			}
			if (nifVer.NI_BS_LTE_FO4()) {
				//<field name="Skin Tint Color" type="Color3" default="#VEC3_ONE#" vercond="#NI_BS_LTE_FO4#" cond="Shader Type == 5">Tints the base texture. Overridden by game settings.</field>
				SkinTintColor = new NifColor3(stream);
			}
			if (nifVer.BS_FO4_2()) {
				//<field name="Skin Tint Alpha" type="float" default="1.0" vercond="#BS_FO4_2#" cond="Shader Type == 5" />
				SkinTintAlpha = ByteConvert.readFloat(stream);
			}
		} else if (ShaderType.getType() == 6) {
			if (nifVer.NI_BS_LTE_FO4()) {
				//<field name="Hair Tint Color" type="Color3" default="#VEC3_ONE#" vercond="#NI_BS_LTE_FO4#" cond="Shader Type == 6">Tints the base texture. Overridden by game settings.</field>
				HairTintColor = new NifColor3(stream);
			}
		} else if (ShaderType.getType() == 7) {
			//<field name="Max Passes" type="float" default="4.0" range="1.0:320.0" cond="Shader Type == 7" />
			MaxPasses = ByteConvert.readFloat(stream);
			//<field name="Scale" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 7" />
			Scale = ByteConvert.readFloat(stream);
		} else if (ShaderType.getType() == 11) {
			//<field name="Parallax Inner Layer Thickness" type="float" default="5.0" range="5.0:500.0" cond="Shader Type == 11">How far from the surface the inner layer appears to be.</field>
			ParallaxInnerLayerThickness = ByteConvert.readFloat(stream);
			//<field name="Parallax Refraction Scale" type="float" default="0.25" range="#F0_1#" cond="Shader Type == 11">Depth of inner parallax layer effect.</field>
			ParallaxRefractionScale = ByteConvert.readFloat(stream);
			//<field name="Parallax Inner Layer Texture Scale" type="TexCoord" cond="Shader Type == 11">Scales the inner parallax layer texture.</field>
			ParallaxInnerLayerTextureScale = new NifTexCoord(stream);
			//<field name="Parallax Envmap Strength" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 11">How strong the environment/cube map is.</field>
			ParallaxEnvmapStrength = ByteConvert.readFloat(stream);
		} else if (ShaderType.getType() == 14) {
			//<field name="Sparkle Parameters" type="Vector4" cond="Shader Type == 14">CK lists "snow material" when used.</field>
			SparkleParamaters = new NifVector4(stream);
		} else if (ShaderType.getType() == 16) {
			//<field name="Eye Cubemap Scale" type="float" default="1.3" range="#F0_10#" cond="Shader Type == 16">Eye cubemap scale</field>
			EyeCubemapScale = ByteConvert.readFloat(stream);
			//<field name="Left Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set center for left eye cubemap</field>
			LeftEyeReflectionCenter = new NifVector3(stream);
			//<field name="Right Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set center for right eye cubemap</field>
			RightEyeReflectionCenter = new NifVector3(stream);
		}

		return success;
	}

	/**
	 * 
	 * dev11 version <struct name="BSLightingShaderPropertyDataGTEFO76" module="BSMain" versions="#BS_GTE_F76#">
	 * Bethesda shader property for Fallout 76 and later. <field name="Shader Type" type="BSShaderType155" />
	 * <field name="Num SF1" type="uint" /> <field name="Num SF2" type="uint" />
	 * <field name="SF1" type="BSShaderCRC32" length="Num SF1" />
	 * <field name="SF2" type="BSShaderCRC32" length="Num SF2" /> <field name="UV Offset" type="TexCoord">Offset
	 * UVs</field> <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling
	 * textures, see above.</field> <field name="Texture Set" type="Ref" template="BSShaderTextureSet">Texture Set, can
	 * have override in an esm/esp</field> <field name="Emissive Color" type="Color3" default="#VEC3_ZERO#">Glow color
	 * and alpha</field> <field name="Emissive Multiple" type="float" default="1.0" range="#F0_10#">Multiplied emissive
	 * colors</field> <field name="Root Material" type="NiFixedString" />
	 * <field name="Unk Float" type="float" vercond="#BS_GTE_STF#" />
	 * <field name="Texture Clamp Mode" type="TexClampMode" default="WRAP_S_WRAP_T">How to handle texture
	 * borders.</field> <field name="Alpha" type="float" default="1.0" range="0.0:128.0">The material opacity
	 * (1=opaque). Greater than 1.0 is used to affect alpha falloff i.e. staying opaque longer based on vertex alpha and
	 * alpha mask.</field> <field name="Refraction Strength" type="float" range="#F0_1#">The amount of distortion. **Not
	 * based on physically accurate refractive index** (0=none)</field>
	 * <field name="Smoothness" type="float" default="1.0" range="#F0_1#">The base roughness, multiplied by the
	 * smoothness map.</field> <field name="Specular Color" type="Color3" default="#VEC3_ONE#">Adds a colored
	 * highlight.</field> <field name="Specular Strength" type="float" default="1.0" range="#F0_10#">Brightness of
	 * specular highlight. (0=not visible)</field>
	 * <field name="Grayscale to Palette Scale" type="float" default="1.0" range="#F0_1#" />
	 * <field name="Fresnel Power" type="float" default="5.0" range="#F_PNZ#" />
	 * <field name="Wetness" type="BSSPWetnessParams" /> <field name="Luminance" type="BSSPLuminanceParams" />
	 * <field name="Do Translucency" type="bool" vercond="#BS_F76#" />
	 * <field name="Translucency" type="BSSPTranslucencyParams" vercond="#BS_F76#" cond="Do Translucency" />
	 * <field name="Has Texture Arrays" type="byte" vercond="#BS_F76#" />
	 * <field name="Num Texture Arrays" type="uint" vercond="#BS_F76#" cond="Has Texture Arrays" />
	 * <field name="Texture Arrays" type="BSTextureArray" length="Num Texture Arrays" vercond="#BS_F76#" cond="Has
	 * Texture Arrays" /> <field name="Unk Float 1" type="float" vercond="#BS_GTE_STF#" />
	 * <field name="Unk Float 2" type="float" vercond="#BS_GTE_STF#" />
	 * <field name="Unk Short 1" type="ushort" vercond="#BS_GTE_STF#" />
	 * <field name="Skin Tint Color" type="Color4" cond="Shader Type == 4" />
	 * <field name="Hair Tint Color" type="Color3" cond="Shader Type == 5" />
	 * <field name="Max Passes" type="float" default="4.0" range="1.0:320.0" cond="Shader Type == 7" />
	 * <field name="Scale" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 7" />
	 * <field name="Parallax Inner Layer Thickness" type="float" default="5.0" range="5.0:500.0" cond="Shader Type ==
	 * 11">How far from the surface the inner layer appears to be.</field>
	 * <field name="Parallax Refraction Scale" type="float" default="0.25" range="#F0_1#" cond="Shader Type == 11">Depth
	 * of inner parallax layer effect.</field>
	 * <field name="Parallax Inner Layer Texture Scale" type="TexCoord" cond="Shader Type == 11">Scales the inner
	 * parallax layer texture.</field>
	 * <field name="Parallax Envmap Strength" type="float" default="1.0" range="#F0_10#" cond="Shader Type == 11">How
	 * strong the environment/cube map is.</field>
	 * <field name="Sparkle Parameters" type="Vector4" cond="Shader Type == 14">CK lists "snow material" when
	 * used.</field>
	 * <field name="Eye Cubemap Scale" type="float" default="1.3" range="#F0_10#" cond="Shader Type == 16">Eye cubemap
	 * scale</field> <field name="Left Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set
	 * center for left eye cubemap</field>
	 * <field name="Right Eye Reflection Center" type="Vector3" cond="Shader Type == 16">Offset to set center for right
	 * eye cubemap</field> </struct>
	 * 
	 * 
	 */
}
