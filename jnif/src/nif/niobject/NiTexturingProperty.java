package nif.niobject;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.compound.NifMatrix22;
import nif.compound.NifShaderTexDesc;
import nif.compound.NifTexDesc;
import nif.enums.ApplyMode;

public class NiTexturingProperty extends NiProperty
{
	/**
	 <niobject name="NiTexturingProperty" abstract="0" inherit="NiProperty">

	 Describes an object's textures.
	 
	 <add name="Flags" type="Flags" ver2="10.0.1.2">Property flags.</add>
	 <add name="Flags" type="Flags" ver1="20.1.0.3">Property flags.</add>
	 <add name="Apply Mode" type="ApplyMode" default="2" ver2="20.0.0.5">
	 Determines how the texture will be applied.  Seems to have special functions in Oblivion.
	 </add>
	 <add name="Texture Count" type="uint" default="7">
	 Number of textures. Always 7 in versions < 20.0.0.4. Can also be 8 in >= 20.0.0.4.
	 </add>
	 <add name="Has Base Texture" type="bool">Do we have a base texture?</add>
	 <add name="Base Texture" type="TexDesc" cond="Has Base Texture != 0">The base texture.</add>
	 <add name="Has Dark Texture" type="bool">Do we have a dark texture?</add>
	 <add name="Dark Texture" type="TexDesc" cond="Has Dark Texture != 0">The dark texture.</add>
	 <add name="Has Detail Texture" type="bool">Do we have a detail texture?</add>
	 <add name="Detail Texture" type="TexDesc" cond="Has Detail Texture != 0">The detail texture.</add>
	 <add name="Has Gloss Texture" type="bool">Do we have a gloss texture?</add>
	 <add name="Gloss Texture" type="TexDesc" cond="Has Gloss Texture != 0">The gloss texture.</add>
	 <add name="Has Glow Texture" type="bool">Do we have a glow texture?</add>
	 <add name="Glow Texture" type="TexDesc" cond="Has Glow Texture != 0">The glowing texture.</add>
	 <add name="Has Bump Map Texture" type="bool">Do we have a bump map texture?</add>
	 <add name="Bump Map Texture" type="TexDesc" cond="Has Bump Map Texture != 0">The bump map texture.</add>
	 <add name="Bump Map Luma Scale" type="float" cond="Has Bump Map Texture != 0">Unknown.</add>
	 <add name="Bump Map Luma Offset" type="float" cond="Has Bump Map Texture != 0">Unknown.</add>
	 <add name="Bump Map Matrix" type="Matrix22" cond="Has Bump Map Texture != 0">Unknown.</add>
	 <add name="Has Normal Texture" type="bool" ver1="20.2.0.7">
	 Do we have a normal texture?  (Noraml guess based on file suffix in sample files)
	 </add>
	 <add name="Normal Texture" type="TexDesc" cond="Has Normal Texture != 0" ver1="20.2.0.7">Normal texture.</add>
	 <add name="Has Unknown2 Texture" type="bool" ver1="20.2.0.7">Do we have a unknown texture 2?</add>
	 <add name="Unknown2 Texture" type="TexDesc" cond="Has Unknown2 Texture != 0" ver1="20.2.0.7">Unknown texture 2.</add>
	 <add name="Unknown2 Float" type="float" cond="Has Unknown2 Texture != 0">Unknown.</add>
	 <add name="Has Decal 0 Texture" type="bool">Do we have a decal 0 texture?</add>
	 <add name="Decal 0 Texture" type="TexDesc" cond="Has Decal 0 Texture != 0">The decal texture.</add>
	 <add name="Has Decal 1 Texture" type="bool" cond="Texture Count >= 8" ver2="20.1.0.3">Do we have a decal 1 texture?</add>
	 <add name="Has Decal 1 Texture" type="bool" cond="Texture Count >= 10" ver1="20.2.0.7">Do we have a decal 1 texture?</add>
	 <add name="Decal 1 Texture" type="TexDesc" cond="Has Decal 1 Texture != 0">Another decal texture.</add>
	 <add name="Has Decal 2 Texture" type="bool" cond="Texture Count >= 9" ver2="20.1.0.3">Do we have a decal 2 texture?</add>
	 <add name="Has Decal 2 Texture" type="bool" cond="Texture Count >= 11" ver1="20.2.0.7">Do we have a decal 2 texture?</add>
	 <add name="Decal 2 Texture" type="TexDesc" cond="Has Decal 2 Texture != 0">Another decal texture.</add>
	 <add name="Has Decal 3 Texture" type="bool" cond="Texture Count >= 10" ver2="20.1.0.3">Do we have a decal 3 texture?</add>
	 <add name="Has Decal 3 Texture" type="bool" cond="Texture Count >= 12" ver1="20.2.0.7">Do we have a decal 3 texture?</add>
	 <add name="Decal 3 Texture" type="TexDesc" cond="Has Decal 3 Texture != 0">Another decal texture. Who knows the limit.</add>
	 <add name="Num Shader Textures" type="uint" ver1="10.0.1.0">Number of Shader textures that follow.</add>
	 <add name="Shader Textures" type="ShaderTexDesc" arr1="Num Shader Textures" ver1="10.0.1.0">Shader textures.</add>
	 </niobject>
	
	 enum MapEnum
	{
	    BASE_INDEX,
	    DARK_INDEX,
	    DETAIL_INDEX,
	    GLOSS_INDEX,
	    GLOW_INDEX,
	    BUMP_INDEX,
	    NORMAL_INDEX,
	    PARALLAX_INDEX,
	    DECAL_BASE,
	    SHADER_BASE,
	    INDEX_MAX
	};

	 */

	public NifFlags flags;

	public ApplyMode applyMode;

	public int textureCount;

	public boolean hasBaseTexture;

	public NifTexDesc baseTexture;

	public boolean hasDarkTexture;

	public NifTexDesc darkTexture;

	public boolean hasDetailTexture;

	public NifTexDesc detailTexture;

	public boolean hasGlossTexture;

	public NifTexDesc glossTexture;

	public boolean hasGlowTexture;

	public NifTexDesc glowTexture;

	public boolean hasBumpMapTexture;

	public NifTexDesc bumpMapTexture;

	public float bumpMapLumaScale;

	public float bumpMapLumaOffset;

	public NifMatrix22 bumpMapMatrix;

	public boolean hasNormalTexture;

	public NifTexDesc normalTexture;

	public boolean hasUnknown2Texture;

	public NifTexDesc unknown2Texture;

	public float unknown2Float;

	public boolean hasDecal0Texture;

	public NifTexDesc decal0Texture;

	public boolean hasDecal1Texture;

	public NifTexDesc decal1Texture;

	public boolean hasDecal2Texture;

	public NifTexDesc decal2Texture;

	public boolean hasDecal3Texture;

	public NifTexDesc decal3Texture;

	public int numShaderTextures;

	public NifShaderTexDesc[] shaderTextures;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		//note ||
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_2 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			flags = new NifFlags(stream);
		}

		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			applyMode = new ApplyMode(stream);
		}

		textureCount = ByteConvert.readInt(stream);
		hasBaseTexture = ByteConvert.readBool(stream, nifVer);
		if (hasBaseTexture)
			baseTexture = new NifTexDesc(stream, nifVer);
		hasDarkTexture = ByteConvert.readBool(stream, nifVer);
		if (hasDarkTexture)
			darkTexture = new NifTexDesc(stream, nifVer);
		hasDetailTexture = ByteConvert.readBool(stream, nifVer);
		if (hasDetailTexture)
			detailTexture = new NifTexDesc(stream, nifVer);
		hasGlossTexture = ByteConvert.readBool(stream, nifVer);
		if (hasGlossTexture)
			glossTexture = new NifTexDesc(stream, nifVer);
		hasGlowTexture = ByteConvert.readBool(stream, nifVer);
		if (hasGlowTexture)
			glowTexture = new NifTexDesc(stream, nifVer);
		hasBumpMapTexture = ByteConvert.readBool(stream, nifVer);
		if (hasBumpMapTexture)
		{
			bumpMapTexture = new NifTexDesc(stream, nifVer);
			bumpMapLumaScale = ByteConvert.readFloat(stream);
			bumpMapLumaOffset = ByteConvert.readFloat(stream);
			bumpMapMatrix = new NifMatrix22(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			hasNormalTexture = ByteConvert.readBool(stream, nifVer);
			if (hasNormalTexture)
			{
				normalTexture = new NifTexDesc(stream, nifVer);
			}

			hasUnknown2Texture = ByteConvert.readBool(stream, nifVer);
			if (hasUnknown2Texture)
			{
				unknown2Texture = new NifTexDesc(stream, nifVer);
				unknown2Float = ByteConvert.readFloat(stream);
			}
		}

		hasDecal0Texture = ByteConvert.readBool(stream, nifVer);
		if (hasDecal0Texture)
		{
			decal0Texture = new NifTexDesc(stream, nifVer);
		}
		if ((nifVer.LOAD_VER <= NifVer.VER_20_1_0_3 && textureCount >= 8) || (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && textureCount >= 10))
		{
			hasDecal1Texture = ByteConvert.readBool(stream, nifVer);
		}
		if (hasDecal1Texture)
		{
			decal1Texture = new NifTexDesc(stream, nifVer);
		}

		if ((nifVer.LOAD_VER <= NifVer.VER_20_1_0_3 && textureCount >= 9) || (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && textureCount >= 11))
		{
			hasDecal2Texture = ByteConvert.readBool(stream, nifVer);
		}
		if (hasDecal2Texture)
		{
			decal2Texture = new NifTexDesc(stream, nifVer);
		}

		if ((nifVer.LOAD_VER <= NifVer.VER_20_1_0_3 && textureCount >= 10)
				|| (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && textureCount >= 12))
		{
			hasDecal3Texture = ByteConvert.readBool(stream, nifVer);
		}
		if (hasDecal3Texture)
		{
			decal3Texture = new NifTexDesc(stream, nifVer);
		}

		if(nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			numShaderTextures = ByteConvert.readInt(stream);
	
			shaderTextures = new NifShaderTexDesc[numShaderTextures];
			for (int i = 0; i < numShaderTextures; i++)
			{
				shaderTextures[i] = new NifShaderTexDesc(stream, nifVer);
			}
		}

		return success;
	}

	public boolean isApplyReplace()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return applyMode.applyMode == ApplyMode.APPLY_REPLACE;
		}
		else
		{
			ret = (flags.flags & 0x0001) != 0;
		}
		return ret;
	}

	public boolean isApplyDecal()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return applyMode.applyMode == ApplyMode.APPLY_REPLACE;
		}
		else
		{
			ret = (flags.flags & 0x0002) != 0;
		}
		return ret;
	}

	public boolean isApplyModulate()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return applyMode.applyMode == ApplyMode.APPLY_MODULATE;
		}
		else
		{
			ret = (flags.flags & 0x0003) != 0;
		}
		return ret;
	}

	public boolean isApplyHighlight()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return applyMode.applyMode == ApplyMode.APPLY_HILIGHT;
		}
		else
		{
			ret = (flags.flags & 0x0004) != 0;
		}
		return ret;
	}

	public boolean isApplyHighlight2()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return applyMode.applyMode == ApplyMode.APPLY_HILIGHT2;
		}
		else
		{
			ret = (flags.flags & 0x0005) != 0;
		}
		return ret;
	}
}