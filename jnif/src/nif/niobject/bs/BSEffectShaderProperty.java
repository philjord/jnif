package nif.niobject.bs;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifColor4;
import nif.compound.NifTexCoord;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.TexClampMode;
import nif.niobject.NiProperty;

public class BSEffectShaderProperty extends NiProperty
{
	/**
	<niobject name="BSEffectShaderProperty" abstract="0" inherit="NiProperty">
	    Skyrim non-PP shader model, used primarily for transparency effects.
	    <add name="Shader Flags 1" type="SkyrimShaderPropertyFlags1"></add>
	    <add name="Shader Flags 2" type="SkyrimShaderPropertyFlags2"></add>
	    <add name="UV OffSet" type="TexCoord">UV OffSet</add>
	    <add name="UV Scale" type="TexCoord" default="1.0, 1.0">UV Scale</add>
	    <add name="Source Texture"  type="SizedString">points to an external texture.</add>
	    <add name="Texture Clamp Mode" type="uint">How to handle texture borders.</add>
	    <!-- Seems to behave the same as in LightingShader, but needs flags instead? -->
	    <add name="Falloff Start Angle" type="float" default="1.0"></add>
	    <add name="Falloff Stop Angle" type="float" default="1.0"></add>
	    <add name="Falloff Start Opacity" type="float">Texture will fade in within this proximity.</add>
	    <add name="Falloff Stop Opacity" type="float"></add>
	    <add name="Emissive Color" type="Color4">Emissive color</add>
	    <add name="Emissive Multiple" type="float">Multipled Emissive Colors</add>
	    <add name="Soft Falloff Depth" type="float"></add>
	    <add name="Greyscale Texture"  type="SizedString">points to an external texture.</add>
	</niobject>

	 */
	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;

	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public String SourceTexture;

	public TexClampMode TextureClampMode;

	public float FalloffStartAngle;

	public float FalloffStopAngle;

	public float FalloffStartOpacity;

	public float FalloffStopOpacity;

	public NifColor4 EmissiveColor;

	public float EmissiveMultiple;

	public float SoftFalloffDepth;

	public String GreyscaleTexture;

	public String EnvMapTexture;

	public String NormalTexture;

	public String EnvMaskTexture;

	public float EnvironmentMapScale;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);

		ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);

		UVOffSet = new NifTexCoord(stream);

		UVScale = new NifTexCoord(stream);

		SourceTexture = ByteConvert.readSizedString(stream);

		TextureClampMode = new TexClampMode(stream);

		FalloffStartAngle = ByteConvert.readFloat(stream);

		FalloffStopAngle = ByteConvert.readFloat(stream);

		FalloffStartOpacity = ByteConvert.readFloat(stream);

		FalloffStopOpacity = ByteConvert.readFloat(stream);

		EmissiveColor = new NifColor4(stream);

		EmissiveMultiple = ByteConvert.readFloat(stream);

		SoftFalloffDepth = ByteConvert.readFloat(stream);

		GreyscaleTexture = ByteConvert.readSizedString(stream);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.LOAD_USER_VER2 == 130)
		{
			EnvMapTexture = ByteConvert.readSizedString(stream);//e.g. Shared/Cubemaps/mipblur_DefaultOutside1.dds
			NormalTexture = ByteConvert.readSizedString(stream);//e.g. actors/bloatfly/bloatfly_n.dds
			EnvMaskTexture = ByteConvert.readSizedString(stream);
			EnvironmentMapScale = ByteConvert.readFloat(stream);
		}

		return success;
	}
}
