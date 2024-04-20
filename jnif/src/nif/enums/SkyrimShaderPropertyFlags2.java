package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SkyrimShaderPropertyFlags2
{
	/**
	 <bitflags name="SkyrimShaderPropertyFlags2" storage="uint">
	        Skyrim Shader Property Flags 2*/
	public static final int SLSF2_ZBuffer_Write = 0;//">Enables writing to the Z-Buffer</option>

	public static final int SLSF2_LOD_Landscape = 1;//"></option>

	public static final int SLSF2_LOD_Objects = 2;//"></option>

	public static final int SLSF2_No_Fade = 3;//"></option>

	public static final int SLSF2_Double_Sided = 4;//">Double-sided rendering.</option>

	public static final int SLSF2_Vertex_Colors = 5;//">Has Vertex Colors.</option>

	public static final int SLSF2_Glow_Map = 6;//">Use Glow Map in the third texture slot.</option>

	public static final int SLSF2_Assume_Shadowmask = 7;//"></option>

	public static final int SLSF2_Packed_Tangent = 8;//"></option>

	public static final int SLSF2_Multi_Index_Snow = 9;//"></option>

	public static final int SLSF2_Vertex_Lighting = 10;//"></option>

	public static final int SLSF2_Uniform_Scale = 11;//"></option>

	public static final int SLSF2_Fit_Slope = 12;//"></option>

	public static final int SLSF2_Billboard = 13;//"></option>

	public static final int SLSF2_No_LOD_Land_Blend = 14;//"></option>

	public static final int SLSF2_EnvMap_Light_Fade = 15;//"></option>

	public static final int SLSF2_Wireframe = 16;//">Wireframe (Seems to only work on particles)</option>

	public static final int SLSF2_Weapon_Blood = 17;//">Used for blood decals on weapons.</option>

	public static final int SLSF2_Hide_On_Local_Map = 18;//">Similar to hide secret, but only for self?</option>

	public static final int SLSF2_Premult_Alpha = 19;//">Has Premultiplied Alpha</option>

	public static final int SLSF2_Cloud_LOD = 20;//"></option>

	public static final int SLSF2_Anisotropic_Lighting = 21;//">Hair only?</option>

	public static final int SLSF2_No_Transparency_Multisampling = 22;//"></option>

	public static final int SLSF2_Unused01 = 23;//">Unused?</option>

	public static final int SLSF2_Multi_Layer_Parallax = 24;//">Use Multilayer (inner-layer) Map</option>

	public static final int SLSF2_Soft_Lighting = 25;//">Use Soft Lighting Map</option>

	public static final int SLSF2_Rim_Lighting = 26;//">Use Rim Lighting Map</option>

	public static final int SLSF2_Back_Lighting = 27;//">Use Back Lighting Map</option>

	public static final int SLSF2_Unused02 = 28;//">Unused?</option>

	public static final int SLSF2_Tree_Anim = 29;//">Enables Vertex Animation, Flutter Animation</option>

	public static final int SLSF2_Effect_Lighting = 30;//"></option>

	public static final int SLSF2_HD_LOD_Objects = 31;//"></option>

	public int flags; // uint

	public SkyrimShaderPropertyFlags2(ByteBuffer stream) throws IOException
	{
		flags = ByteConvert.readInt(stream);
	}

	public static boolean isBitSet(SkyrimShaderPropertyFlags2 flags, int bitToMask)
	{
		return flags != null && (flags.flags & (0x1 << bitToMask)) != 0;
	}
}
