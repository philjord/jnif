package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public enum BSShaderType155 implements BSShaderTypeI
{
	/**
	 <enum name="BSShaderType155" storage="uint" prefix="ST155" versions="#F76#">
        Values for configuring the shader type in a BSLightingShaderProperty
        <option value="0" name="Default" />
        <option value="2" name="Glow" />
        <option value="3" name="Face Tint" />
        <option value="4" name="Skin Tint" />
        <option value="5" name="Hair Tint" />
        <option value="12" name="Eye Envmap">Enables EnvMap Mask, Eye EnvMap Scale</option>
        <option value="17" name="Terrain" />
    </enum>
	 */

	Default (0),
	Glow (2),
	FaceTint (3), 
	SkinTint (4), 
	HairTint (5), 
	EyeEnvmap (12), 
	Terrain (17);

	public int type;
	private BSShaderType155(int type) 
	{
		this.type = type;
	}
	@Override
	public int getType() {
		return type;
	}
	public static BSShaderType155 load(ByteBuffer stream) throws IOException
	{
		int t = ByteConvert.readInt(stream);
		for (BSShaderType155 st : BSShaderType155.values())
			if (st.type == t)
				return st;
		
		System.out.println("Bad BSShaderType155 " + t);
		return null;
	}


}
