package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifTexCoord; 
/**
 * nifxml now has this covered
 */
public abstract class BSMaterial
{
	/** BGSM & BGEM shared variables
		 <field name="Version" type="uint" />
        <field name="Shader Flags 1" type="Fallout76MaterialPropertyFlags1" />
        <field name="Shader Flags 2" type="Fallout76MaterialPropertyFlags2" />
        <field name="UV Offset" type="TexCoord">Offset UVs</field>
        <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see above.</field>
        <field name="Alpha" type="float" default="1.0" range="0.0:128.0">The material opacity (1=opaque). Greater than 1.0 is used to affect alpha falloff i.e. staying opaque longer based on vertex alpha and alpha mask.</field>
        <field name="Alpha Source Blend Mode" type="AlphaFunction" />
        <field name="Alpha Destination Blend Mode" type="AlphaFunction" />
        <field name="Alpha Test Threshold" type="byte" />
        <field name="Refraction Power" type="float" />
        
        
    <bitflags name="Fallout76MaterialPropertyFlags1" storage="ushort" versions="#F76#">
        Fallout 76 Common Material Property Flags
        <option bit="0" name="Tile_U" />
        <option bit="1" name="Tile_V" />
        <option bit="2" name="Alpha_Blend" />
        <option bit="3" name="Alpha_Test" />
        <option bit="4" name="ZBuffer_Write" />
        <option bit="5" name="ZBuffer_Test" />
        <option bit="6" name="Screen_Space_Reflections" />
        <option bit="7" name="WetnessControl_SSR" />
        <option bit="8" name="Decal" />
        <option bit="9" name="Double_Sided" />
        <option bit="10" name="Decal_No_Fade" />
        <option bit="11" name="Non_Occluder" />
        <option bit="12" name="Refraction" />
        <option bit="13" name="Refraction_Falloff" />
        <option bit="14" name="Environment_Mapping" />
        <option bit="15" name="Grayscale_To_Palette_Color" />
    </bitflags>

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




  <enum name="AlphaFunction" storage="ushort" prefix="ALPHA">
        Describes alpha blend modes for NiAlphaProperty.
        <option value="0" name="ONE" />
        <option value="1" name="ZERO" />
        <option value="2" name="SRC_COLOR" />
        <option value="3" name="INV_SRC_COLOR" />
        <option value="4" name="DEST_COLOR" />
        <option value="5" name="INV_DEST_COLOR" />
        <option value="6" name="SRC_ALPHA" />
        <option value="7" name="INV_SRC_ALPHA" />
        <option value="8" name="DEST_ALPHA" />
        <option value="9" name="INV_DEST_ALPHA" />
        <option value="10" name="SRC_ALPHA_SATURATE" />
    </enum>


		 */
	public int Version = 0;
	public int tileFlags = 0;
	public boolean bTileU = false;
	public boolean bTileV = false;
	public float fUOffset;
	public float fVOffset;
	public float fUScale;
	public float fVScale;
	public float fAlpha;
	public byte bAlphaBlend;
	public int iAlphaSrc;
	public int iAlphaDst;
	public int iAlphaTestRef;
	public byte bAlphaTest;
	public short bZBufferWrite;
	public byte bZBufferTest;
	public byte bScreenSpaceReflections;
	public byte bWetnessControl_ScreenSpaceReflections;
	public byte bDecal;
	public byte bTwoSided;
	public byte bDecalNoFade;
	public byte bNonOccluder;
	public byte bRefraction;
	public byte bRefractionFalloff;
	public float fRefractionPower;
	public byte bEnvironmentMapping;
	public float fEnvironmentMappingMaskScale;
	public byte bDepthBias;
	public byte bGrayscaleToPaletteColor;

	public byte MaskWrites;
	
	//FIXME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// started making correct to nifxml, but the current has 10+10+1 bytes were the nifxml has only 2+2+1!
	
	
	/*
	public int Version = 0;
	public short ShaderFlags1 = 0;//use unsigned
	public short ShaderFlags2 = 0;//use unsigned
	public NifTexCoord UVOffset;
	public NifTexCoord UVScale;
	
	public float Alpha;
	public short AlphaSourceBlendMode;//use unsigned
	public short AlphaDestinationBlendMode;//use unsigned
	public byte AlphaTestThreshold;
	public float RefractionPower;*/

	public void readFile(ByteBuffer stream) throws IOException
	{
		
		Version = ByteConvert.readInt(stream);
		tileFlags = ByteConvert.readInt(stream);

		bTileU = (tileFlags & 0x2) != 0;
		bTileV = (tileFlags & 0x1) != 0;

		fUOffset = ByteConvert.readFloat(stream);
		fVOffset = ByteConvert.readFloat(stream);
		fUScale = ByteConvert.readFloat(stream);
		fVScale = ByteConvert.readFloat(stream);

		fAlpha = ByteConvert.readFloat(stream);
		bAlphaBlend = ByteConvert.readByte(stream);
		iAlphaSrc = ByteConvert.readInt(stream);
		iAlphaDst = ByteConvert.readInt(stream);
		iAlphaTestRef = ByteConvert.readUnsignedByte(stream);
		bAlphaTest = ByteConvert.readByte(stream);
		bZBufferWrite = ByteConvert.readByte(stream);
		bZBufferTest = ByteConvert.readByte(stream);
		bScreenSpaceReflections = ByteConvert.readByte(stream);
		bWetnessControl_ScreenSpaceReflections = ByteConvert.readByte(stream);
		bDecal = ByteConvert.readByte(stream);
		bTwoSided = ByteConvert.readByte(stream);
		bDecalNoFade = ByteConvert.readByte(stream);
		bNonOccluder = ByteConvert.readByte(stream);
		bRefraction = ByteConvert.readByte(stream);
		bRefractionFalloff = ByteConvert.readByte(stream);
		fRefractionPower = ByteConvert.readFloat(stream);
		
		if (Version < 10) {
			bEnvironmentMapping = ByteConvert.readByte(stream);
			fEnvironmentMappingMaskScale = ByteConvert.readFloat(stream);             
        } else {
            bDepthBias = ByteConvert.readByte(stream);
        }
		
		bGrayscaleToPaletteColor = ByteConvert.readByte(stream);
		
		if (Version >= 6) {
			MaskWrites = ByteConvert.readByte(stream);
		}
		
		
		/*
		Version = ByteConvert.readInt(stream);
		ShaderFlags1 = ByteConvert.readShort(stream);
		ShaderFlags2 = ByteConvert.readShort(stream);

		UVOffset = new NifTexCoord(stream);
		UVScale = new NifTexCoord(stream);
		Alpha = ByteConvert.readFloat(stream);
		AlphaSourceBlendMode = ByteConvert.readShort(stream);
		AlphaDestinationBlendMode = ByteConvert.readShort(stream);		
		AlphaTestThreshold = ByteConvert.readByte(stream);		
		RefractionPower = ByteConvert.readFloat(stream);
		*/		
	}
	
	/**
	  
    <bitflags name="Fallout76MaterialPropertyFlags1" storage="ushort" versions="#F76#">
        Fallout 76 Common Material Property Flags
        <option bit="0" name="Tile_U" />
        <option bit="1" name="Tile_V" />
        <option bit="2" name="Alpha_Blend" />
        <option bit="3" name="Alpha_Test" />
        <option bit="4" name="ZBuffer_Write" />
        <option bit="5" name="ZBuffer_Test" />
        <option bit="6" name="Screen_Space_Reflections" />
        <option bit="7" name="WetnessControl_SSR" />
        <option bit="8" name="Decal" />
        <option bit="9" name="Double_Sided" />
        <option bit="10" name="Decal_No_Fade" />
        <option bit="11" name="Non_Occluder" />
        <option bit="12" name="Refraction" />
        <option bit="13" name="Refraction_Falloff" />
        <option bit="14" name="Environment_Mapping" />
        <option bit="15" name="Grayscale_To_Palette_Color" />
    </bitflags>
    

*/

/**

  <enum name="AlphaFunction" storage="ushort" prefix="ALPHA">
        Describes alpha blend modes for NiAlphaProperty.
        <option value="0" name="ONE" />
        <option value="1" name="ZERO" />
        <option value="2" name="SRC_COLOR" />
        <option value="3" name="INV_SRC_COLOR" />
        <option value="4" name="DEST_COLOR" />
        <option value="5" name="INV_DEST_COLOR" />
        <option value="6" name="SRC_ALPHA" />
        <option value="7" name="INV_SRC_ALPHA" />
        <option value="8" name="DEST_ALPHA" />
        <option value="9" name="INV_DEST_ALPHA" />
        <option value="10" name="SRC_ALPHA_SATURATE" />
    </enum>
    
    
		fAlpha = ByteConvert.readFloat(stream);
		bAlphaBlend = ByteConvert.readByte(stream);
		iAlphaSrc = ByteConvert.readInt(stream);
		iAlphaDst = ByteConvert.readInt(stream);
		iAlphaTestRef = ByteConvert.readUnsignedByte(stream);
		bAlphaTest = ByteConvert.readByte(stream);
		bZBufferWrite = ByteConvert.readByte(stream);
		bZBufferTest = ByteConvert.readByte(stream);
		bScreenSpaceReflections = ByteConvert.readByte(stream);
		bWetnessControl_ScreenSpaceReflections = ByteConvert.readByte(stream);
		bDecal = ByteConvert.readByte(stream);
		bTwoSided = ByteConvert.readByte(stream);
		bDecalNoFade = ByteConvert.readByte(stream);
		bNonOccluder = ByteConvert.readByte(stream);
		bRefraction = ByteConvert.readByte(stream);
		bRefractionFalloff = ByteConvert.readByte(stream);
		fRefractionPower = ByteConvert.readFloat(stream);
		
	 */

}
