package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public enum BSShaderType implements BSShaderTypeI
{
	/**
	<enum name="BSShaderType" storage="uint" versions="#FO3#">
        FO3 Shader Type
        <option value="0" name="SHADER_TALL_GRASS">Tall Grass Shader</option>
        <option value="1" name="SHADER_DEFAULT">Standard Lighting Shader</option>
        <option value="10" name="SHADER_SKY">Sky Shader</option>
        <option value="14" name="SHADER_SKIN">Skin Shader</option>
        <option value="15" name="SHADER_UNKNOWN">scolbld06georgetown01</option>
        <option value="17" name="SHADER_WATER">Water Shader</option>
        <option value="29" name="SHADER_LIGHTING30">Lighting 3.0 Shader</option>
        <option value="32" name="SHADER_TILE">Tiled Shader</option>
        <option value="33" name="SHADER_NOLIGHTING">No Lighting Shader</option>
    </enum>
	 */

	SHADER_TALL_GRASS(0),// Tall Grass Shader
	SHADER_DEFAULT (1),//Standard Lighting Shader</option>
	SHADER_SKY (10),//Sky Shader</option>
	SHADER_SKIN (14), 
	SHADER_UNKNOWN (15), 
	SHADER_WATER (17),//Water Shader</option>
	SHADER_LIGHTING30 (29),//Lighting 3.0 Shader</option>
	SHADER_TILE (32),//Tiled Shader</option>
	SHADER_NOLIGHTING (33);//No Lighting Shader</option>
	
 

	public int type;
	private BSShaderType(int type) 
	{
		this.type = type;
	}
	@Override
	public int getType() {
		return type;
	}
	public static BSShaderType load(ByteBuffer stream) throws IOException
	{
		int t = ByteConvert.readInt(stream);
		for (BSShaderType st : BSShaderType.values())
			if (st.type == t)
				return st;
		
		System.out.println("Bad BSShaderType " + t);
		return null;
	}


}
