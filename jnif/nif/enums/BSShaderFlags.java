package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSShaderFlags
{

	public static final int SF_ZBUFFER_TEST = 0; //ZBuffer Test (1=on)</option>

	public static final int SF_SHADOW_MAP = 1; //Shadow Map</option>

	public static final int SF_EXTERNAL_EMITTANCE = 2; //External Emittance</option>

	public static final int SF_PARALLAX_OCCLUSION = 3; //Parallax Occlusion</option>

	public static final int SF_DYNAMIC_DECAL = 4; //Dynamic Decal</option>

	public static final int SF_DECAL = 5; //Decal</option>

	public static final int SF_UNKNOWN_6 = 6; //Unknown\Light fade? (if 0 and envmap is on, "envmap light fade" is not present)</option>

	public static final int SF_MULTIPLE_TEXTURES = 7; //Multiple Textures (base diff/norm become null)</option>

	public static final int SF_SHADOW_FRUSTUM = 8; //Shadow Frustum</option>

	public static final int SF_TREE_BILLBOARD = 9; //Tree Billboard</option>

	public static final int SF_WINDOW_ENVIRONMENT_MAPPING = 10; //Window Environment Mapping</option>

	public static final int SF_LOCALMAP_HIDE_SECRET = 11; //Localmap Hide Secret</option>

	public static final int SF_DYNAMIC_ALPHA = 12; //Dynamic Alpha</option>

	public static final int SF_HAIR = 13; //Hair</option>

	public static final int SF_EYE_ENVIRONMENT_MAPPING = 14; //Eye Environment Mapping (does not use envmap light fade or envmap scale)</option>

	public static final int SF_FIRE_REFRACTION = 15; //Fire Refraction (switches on refraction power/period)</option>

	public static final int SF_REFRACTION = 16; //Refraction (switches on refraction power)</option>

	public static final int SF_UNKNOWN_17 = 17; //Unknown/Crash</option>

	public static final int NON_PROJECTIVE_SHADOWS = 18; //Non-Projective Shadows</option>

	public static final int SF_UNKNOWN_19 = 19; //Unknown/Crash</option>

	public static final int SF_PARALLAX = 20; //Parallax</option>

	public static final int SF_FACEGEN_SKIN = 21; //Facegen\Skin</option>

	public static final int SF_UNKNOWN_22 = 22; //Unknown (Always 0?)</option>

	public static final int SF_UNKNOWN_23 = 23; //Unknown (usually 1)</option>

	public static final int SF_ENVIRONMENT_MAPPING = 24; //Environment mapping (uses Envmap Scale)</option>

	public static final int SF_EMPTY = 25; //EMPTY (usually seen w/texture animation)</option>

	public static final int SF_SINGLE_PASS = 26; //Single Pass (uses same default shader path as diff/norm/spec setup BSSM_ADTS10)</option>

	public static final int SF_UNKNOWN_27 = 27; //Unknown (Always 0?)</option>

	public static final int SF_VERTEX_ALPHA = 28; //Vertex Alpha</option>

	public static final int SF_LOWDDETAIL = 29; //Lowddetail (seems to use standard diff/norm/spec shader)</option>

	public static final int SF_SKINNED = 30; //Skinned.</option>

	public static final int SF_UNKNOWN_31 = 31; //Unknown</option>

	public int flags;

	public BSShaderFlags(InputStream stream) throws IOException
	{
		flags = ByteConvert.readInt(stream);
	}

	public boolean isBitSet(int bitToMask)
	{
		return (flags & (0x1 << bitToMask)) != 0;
	}
}
