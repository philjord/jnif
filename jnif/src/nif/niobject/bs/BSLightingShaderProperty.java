package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifColor3;
import nif.compound.NifTexCoord;
import nif.compound.NifVector3;
import nif.compound.NifVector4;
import nif.enums.BSLightingShaderPropertyShaderType;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.TexClampMode;
import nif.niobject.NiExtraData;
import nif.niobject.NiObject;
import nif.niobject.controller.NiTimeController;

public class BSLightingShaderProperty extends NiObject
{
	/**
	 <niobject name="BSLightingShaderProperty"  abstract="0" inherit="NiObject">
	    Skyrim PP shader for assigning material/shader/texture.
	    <add name="Skyrim Shader Type" type="BSLightingShaderPropertyShaderType" vercond="User Version >= 12">Configures the main shader path</add>
	    <add name="Name" type="string">Object Name</add>
	    <add name="Num Extra Data List" type="uint" ver1="10.0.1.0">The number of Extra Data objects referenced through the list.</add>
	    <add name="Extra Data List" type="Ref" template="NiExtraData" arr1="Num Extra Data List" ver1="10.0.1.0">List of extra data indices.</add>
	    <add name="Controller" type="Ref" template="NiTimeController" ver1="3.0">Controller object index. (The first in a chain)</add>
	    <add name="Shader Flags 1" type="SkyrimShaderPropertyFlags1" vercond="User Version == 12" default="2185233153">Skyrim Shader Flags for setting render/shader options.</add>
	    <add name="Shader Flags 2" type="SkyrimShaderPropertyFlags2" vercond="User Version == 12" default="32801">Skyrim Shader Flags for setting render/shader options.</add>
	    <add name="UV Offset" type="TexCoord">UV Offset</add>
	    <add name="UV Scale" type="TexCoord">UV Scale</add>
	    <add name="Texture Set" type="Ref" template="BSShaderTextureSet">Texture Set, can have override in an esm/esp</add>
	    <add name="Emissive Color" type="Color3">Glow color and alpha</add>
	    <add name="Emissive Multiple" type="float">Multiplied emissive colors</add>
	    <add name="Texture Clamp Mode" type="TexClampMode">How to handle texture borders.</add>
	    <add name="Alpha" type="float" default="1.0">The materials opacity (1=non-transparent).</add>
	    <add name="Unknown Float 2" type="float">Unknown</add>
	    <add name="Specular Power - Glossiness" type="float">The material&#039;s glossiness. (0-999)</add>
	    <add name="Specular Color" type="Color3">Adds a colored highlight.</add>
	    <add name="Specular Strength" type="float" default="1.0">Brightness of specular highlight. (0=not visible) (0-999)</add>
	    <add name="Lighting Effect 1" type="float">Controls strength for envmap/backlight/rim/softlight lighting effect?</add>
	    <add name="Lighting Effect 2" type="float">Controls strength for envmap/backlight/rim/softlight lighting effect?</add>
	    <add name="Environment Map Scale" type="float" cond="Skyrim Shader Type == 1">Scales the environment/cube map. (0-??)</add>
	    <add name="Skin Tint Color" type="Color3" cond="Skyrim Shader Type == 5">Tints the base texture. Overridden by game settings.</add>
	    <add name="Hair Tint Color" type="Color3" cond="Skyrim Shader Type == 6">Tints the base texture. Overridden by game settings.</add>
	    <add name="Max Passes" type="float" cond="Skyrim Shader Type == 7">Max Passes</add>
	    <add name="Scale" type="float" cond="Skyrim Shader Type == 7">Scale</add>
	    <add name="Parallax Inner Layer Thickness" type="float" cond="Skyrim Shader Type == 11">How far from the surface the inner layer appears to be.</add>
	    <add name="Parallax Refraction Scale" type="float" cond="Skyrim Shader Type == 11">Depth of inner parallax layer effect.</add>
	    <add name="Parallax Inner Layer Texture Scale" type="TexCoord" cond="Skyrim Shader Type == 11">Scales the inner parallax layer texture.</add>
	    <add name="Parallax Envmap Strength" type="float" cond="Skyrim Shader Type == 11">How strong the environment/cube map is. (0-??)</add>
	    <add name="Sparkle Paramaters" type="Vector4" cond="Skyrim Shader Type == 14">Unknown/unused?  CK lists "snow material" when used.</add>
	    <add name="Eye Cubemap Scale" type="float" cond="Skyrim Shader Type == 16">Eye cubemap scale</add>
	    <add name="Left Eye Reflection Center" type="Vector3" cond="Skyrim Shader Type == 16">Offset to set center for left eye cubemap</add>
	    <add name="Right Eye Reflection Center" type="Vector3" cond="Skyrim Shader Type == 16">Offset to set center for right eye cubemap</add>
	</niobject>
	
	
	 */

	public BSLightingShaderPropertyShaderType SkyrimShaderType;

	public String Name;

	public int NumExtraDataList;

	public NifRef[] ExtraDataList;

	public NifRef controller;

	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;

	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public NifRef TextureSet;

	public NifColor3 EmissiveColor;

	public float EmissiveMultiple;

	public TexClampMode TextureClampMode;

	public float Alpha;

	public float UnknownFloat2;

	public float Glossiness;//LOAD_USER_VER2 == 130 0.5?? not out of 1000 but just 0-1??

	public NifColor3 SpecularColor;

	public float SpecularStrength;

	public float LightingEffect1;

	public float LightingEffect2;

	public float EnvironmentMapScale;

	public NifColor3 SkinTintColor;

	public NifColor3 HairTintColor;

	public float MaxPasses;

	public float Scale;

	public float ParallaxInnerLayerThickness;

	public float ParallaxRefractionScale;

	public NifTexCoord ParallaxInnerLayerTextureScale;

	public float ParallaxEnvmapStrength;

	public NifVector4 SparkleParamaters;

	public float EyeCubemapScale;

	public NifVector3 LeftEyeReflectionCenter;

	public NifVector3 RightEyeReflectionCenter;

	//FO4 only
	public String wetMaterialTemplate;
	public float SubsurfaceRolloff;
	public float UnknownFloat1;
	public float BacklightPower;
	public float GrayscaletoPaletteScale;
	public float FresnelPower;
	public float WetnessSpecScale;
	public float WetnessSpecPower;
	public float WetnessMinVar;
	public float WetnessEnvMapScale;
	public float WetnessFresnelPower;
	public float WetnessMetalness;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_USER_VER >= 12)
		{
			SkyrimShaderType = new BSLightingShaderPropertyShaderType(stream);
		}

		Name = ByteConvert.readIndexString(stream, nifVer);// name of a material file in USER_VER2==130
		
		NumExtraDataList = ByteConvert.readInt(stream);

		ExtraDataList = new NifRef[NumExtraDataList];
		for (int i = 0; i < NumExtraDataList; i++)
		{
			ExtraDataList[i] = new NifRef(NiExtraData.class, stream);
		}

		controller = new NifRef(NiTimeController.class, stream);

		if (nifVer.LOAD_USER_VER >= 12)
		{
			ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);
			ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);		
		}

		UVOffSet = new NifTexCoord(stream);
		UVScale = new NifTexCoord(stream);
		TextureSet = new NifRef(BSShaderTextureSet.class, stream);
		EmissiveColor = new NifColor3(stream);
		EmissiveMultiple = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.LOAD_USER_VER2 == 130)
		{
			wetMaterialTemplate = ByteConvert.readIndexString(stream, nifVer);
		}
		TextureClampMode = new TexClampMode(stream);
		Alpha = ByteConvert.readFloat(stream);
		UnknownFloat2 = ByteConvert.readFloat(stream);
		Glossiness = ByteConvert.readFloat(stream);
		SpecularColor = new NifColor3(stream);
		SpecularStrength = ByteConvert.readFloat(stream);

		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.LOAD_USER_VER2 == 130))
		{
			LightingEffect1 = ByteConvert.readFloat(stream);
			LightingEffect2 = ByteConvert.readFloat(stream);

			if (SkyrimShaderType.type == 1)
			{
				EnvironmentMapScale = ByteConvert.readFloat(stream);
			}
			else if (SkyrimShaderType.type == 5)
			{
				SkinTintColor = new NifColor3(stream);
			}
			else if (SkyrimShaderType.type == 6)
			{
				HairTintColor = new NifColor3(stream);
			}
			else if (SkyrimShaderType.type == 7)
			{
				MaxPasses = ByteConvert.readFloat(stream);
				Scale = ByteConvert.readFloat(stream);
			}
			else if (SkyrimShaderType.type == 11)
			{
				ParallaxInnerLayerThickness = ByteConvert.readFloat(stream);
				ParallaxRefractionScale = ByteConvert.readFloat(stream);
				ParallaxInnerLayerTextureScale = new NifTexCoord(stream);
				ParallaxEnvmapStrength = ByteConvert.readFloat(stream);
			}
			else if (SkyrimShaderType.type == 14)
			{
				SparkleParamaters = new NifVector4(stream);
			}
			else if (SkyrimShaderType.type == 16)
			{
				EyeCubemapScale = ByteConvert.readFloat(stream);
				LeftEyeReflectionCenter = new NifVector3(stream);
				RightEyeReflectionCenter = new NifVector3(stream);
			}
		}
		else
		{
			SubsurfaceRolloff = ByteConvert.readFloat(stream);
			UnknownFloat1 = ByteConvert.readFloat(stream);
			BacklightPower = ByteConvert.readFloat(stream);
			GrayscaletoPaletteScale = ByteConvert.readFloat(stream);
			FresnelPower = ByteConvert.readFloat(stream);
			WetnessSpecScale = ByteConvert.readFloat(stream);
			WetnessSpecPower = ByteConvert.readFloat(stream);
			WetnessMinVar = ByteConvert.readFloat(stream);
			WetnessEnvMapScale = ByteConvert.readFloat(stream);
			WetnessFresnelPower = ByteConvert.readFloat(stream);
			WetnessMetalness = ByteConvert.readFloat(stream);

			if (SkyrimShaderType.type == 2 || SkyrimShaderType.type == 4 || SkyrimShaderType.type == 18)
			{
				//appears to need no extra data
			}
			else if (SkyrimShaderType.type == 1)
			{
				//6 more required
				ByteConvert.readUnsignedShort(stream);// not examined
				EnvironmentMapScale = ByteConvert.readFloat(stream);// not examined
			}
			else if (SkyrimShaderType.type == 5)
			{
				//16 more required
				SkinTintColor = new NifColor3(stream);// not examined
				ByteConvert.readInt(stream);// not examined
			}
			else if (SkyrimShaderType.type == 6)
			{
				//12 more required
				HairTintColor = new NifColor3(stream);// not examined				
			}
			else if (SkyrimShaderType.type == 16)
			{
				//28 more required
				EyeCubemapScale = ByteConvert.readFloat(stream);// not examined
				LeftEyeReflectionCenter = new NifVector3(stream);// not examined
				RightEyeReflectionCenter = new NifVector3(stream);// not examined				
			}
			else if (SkyrimShaderType.type != 0)
			{
				System.out.println("SkyrimShaderType type of " + SkyrimShaderType.type + " Not yet looked at closely");
			}
		}

		return success;
	}
}
