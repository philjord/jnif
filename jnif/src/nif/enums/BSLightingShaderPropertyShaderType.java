package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSLightingShaderPropertyShaderType
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

	public int type;

	public BSLightingShaderPropertyShaderType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
