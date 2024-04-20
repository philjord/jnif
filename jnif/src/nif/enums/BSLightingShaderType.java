package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public enum BSLightingShaderType implements BSShaderTypeI
{
	/**
	 <enum name="BSLightingShaderPropertyShaderType" storage="uint">
	        Values for configuring the shader type in a BSLightingShaderProperty
	        <option value="0" name="Default"></option>
	        <option value="1" name="Environment Map">Enables EnvMap Mask(TS6), EnvMap Scale</option>
	        <option value="2" name="Glow Shader">Enables Glow(TS3)</option>
	        <option value="3" name="Heightmap">Enables Height(TS4)</option>
	        <option value="4" name="Face Tint">Enables SubSurface(TS3), Detail(TS4), Tint(TS7)</option>
	        <option value="5" name="Skin Tint">Enables Skin Tint Color</option>
	        <option value="6" name="Hair Tint">Enables Hair Tint Color</option>
	        <option value="7" name="Parallax Occ Material">Enables Height(TS4), Max Passes, Scale.  Unused?</option>
	        <option value="8" name="World Multitexture"></option>
	        <option value="9" name="WorldMap1"></option>
	        <option value="10" name="Unknown/Unused"></option>
	        <option value="11" name="MultiLayer Parallax">Enables EnvMap Mask(TS6), Layer(TS7), Parallax Layer Thickness, Parallax Refraction Scale, Parallax Inner Layer U Scale, Parallax Inner Layer V Scale, EnvMap Scale</option>
	        <option value="12" name="Unknown/Unused"></option>
	        <option value="13" name="WorldMap2"></option>
	        <option value="14" name="Sparkle/Snow">Enables SparkleParams</option>
	        <option value="15" name="WorldMap3"></option>
	        <option value="16" name="Eye Envmap">Enables EnvMap Mask(TS6), Eye EnvMap Scale</option>
	        <option value="17" name="Unknown/Unused"></option>
	        <option value="18" name="WorldMap4"></option>
	        <option value="19" name="World LOD Multitexture"></option>
	    </enum>

	 */
	
	ST_Default (0), //"></option>
	ST_EnvironmentMap (1), //">Enables EnvMap Mask(TS6), EnvMap Scale</option>
	ST_GlowShader (2), //">Enables Glow(TS3)</option>
	ST_Heightmap (3), //">Enables Height(TS4)</option>
	ST_FaceTint (4), //">Enables SubSurface(TS3), Detail(TS4), Tint(TS7)</option>
	ST_SkinTint (5), //">Enables Skin Tint Color</option>
	ST_HairTint (6), //">Enables Hair Tint Color</option>
	ST_ParallaxOccMaterial (7), //">Enables Height(TS4), Max Passes, Scale.  Unused?</option>
	ST_WorldMultitexture (8), //"></option>
	ST_WorldMap1 (9), //"></option>
	ST_UnknownUnused1 (10), //"></option>
	ST_MultiLayerParallax (11), //">Enables EnvMap Mask(TS6), Layer(TS7), Parallax Layer Thickness, Parallax Refraction Scale, Parallax Inner Layer U Scale, Parallax Inner Layer V Scale, EnvMap Scale</option>
	ST_UnknownUnused2 (12), //"></option>
	ST_WorldMap2 (13), //"></option>
	ST_SparkleSnow (14), //">Enables SparkleParams</option>
	ST_WorldMap3 (15), //"></option>
	ST_EyeEnvmap (16), //">Enables EnvMap Mask(TS6), Eye EnvMap Scale</option>
	ST_UnknownUnused3 (17), //"></option>
	ST_WorldMap4 (18), //"></option>
	ST_WorldLODMultitexture (19); //"></option>

	public int type;
	private BSLightingShaderType(int type) 
	{
		this.type = type;
	}
	@Override
	public int getType() {
		return type;
	}
	public static BSLightingShaderType load(ByteBuffer stream) throws IOException
	{
		int t = ByteConvert.readInt(stream);
		if(t < 20)
			return BSLightingShaderType.values()[t];
		
		for (BSLightingShaderType st : BSLightingShaderType.values())
			if (st.type == t)
				return st;
		
		System.out.println("Bad BSLightingShaderType " + t);
		return null;
	}

	 
}
