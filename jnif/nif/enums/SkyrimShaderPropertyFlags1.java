package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class SkyrimShaderPropertyFlags1
{
	/**
	 <bitflags name="SkyrimShaderPropertyFlags1" storage="uint">
	 
	        Skyrim Shader Property Flags 1
	        */
	public static final int SLSF1_Specular = 0;//">Enables Specularity</option>

	public static final int SLSF1_Skinned = 1;//">Required For Skinned Meshes.</option>

	public static final int SLSF1_Temp_Refraction = 2;//">Unknown</option>

	public static final int SLSF1_Vertex_Alpha = 3;//">Enables using alpha component of vertex colors.</option>

	public static final int SLSF1_Greyscale_To_PaletteColor = 4;//">in EffectShaderProperty</option>

	public static final int SLSF1_Greyscale_To_PaletteAlpha = 5;//">in EffectShaderProperty</option>

	public static final int SLSF1_Use_Falloff = 6;//">Use Falloff value in EffectShaderProperty</option>

	public static final int SLSF1_Environment_Mapping = 7;//">Environment mapping (uses Envmap Scale).</option>

	public static final int SLSF1_Recieve_Shadows = 8;//">Object can recieve shadows.</option>

	public static final int SLSF1_Cast_Shadows = 9;//">Can cast shadows</option>

	public static final int SLSF1_Facegen_Detail_Map = 10;//">Use a face detail map in the 4th texture slot.</option>

	public static final int SLSF1_Parallax = 11;//">Unused?</option>

	public static final int SLSF1_Model_Space_Normals = 12;//">Use Model space normals and an external Specular Map.</option>

	public static final int SLSF1_Non_Projective_Shadows = 13;//">Unknown.</option>

	public static final int SLSF1_Landscape = 14;//">Unknown.</option>

	public static final int SLSF1_Refraction = 15;//">Use normal map for refraction effect.</option>

	public static final int SLSF1_Fire_Refraction = 16;//"></option>

	public static final int SLSF1_Eye_Environment_Mapping = 17;//">Eye Environment Mapping (Must use the Eye shader and the model must be skinned)</option>

	public static final int SLSF1_Hair_Soft_Lighting = 18;//">Keeps from going too bright under lights (hair shader only)</option>

	public static final int SLSF1_Screendoor_Alpha_Fade = 19;//"></option>

	public static final int SLSF1_Localmap_Hide_Secret = 20;//">Object and anything it is positioned above will not render on local map view.</option>

	public static final int SLSF1_FaceGen_RGB_Tint = 21;//">Use tintmask for Face.</option>

	public static final int SLSF1_Own_Emit = 22;//">Provides its own emittance color. (will not absorb light/ambient color?)</option>

	public static final int SLSF1_Projected_UV = 23;//">Used for decalling?</option>

	public static final int SLSF1_Multiple_Textures = 24;//"></option>

	public static final int SLSF1_Remappable_Textures = 25;//"></option>

	public static final int SLSF1_Decal = 26;//"></option>

	public static final int SLSF1_Dynamic_Decal = 27;//"></option>

	public static final int SLSF1_Parallax_Occlusion = 28;//"></option>

	public static final int SLSF1_External_Emittance = 29;//"></option>

	public static final int SLSF1_Soft_Effect = 30;//"></option>

	public static final int SLSF1_ZBuffer_Test = 31;//">ZBuffer Test (1=on)</option>

	public int flags; // uint

	public SkyrimShaderPropertyFlags1(InputStream stream) throws IOException
	{
		flags = ByteConvert.readInt(stream);
	}

	public boolean isBitSet(int bitToMask)
	{
		return (flags & (0x1 << bitToMask)) != 0;
	}
}
