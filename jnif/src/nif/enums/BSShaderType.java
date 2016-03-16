package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSShaderType
{
	/**
	 * The type of shader program   
4?  - in armour directory and head nifs
	 */

	public static final int SHADER_TALL_GRASS = 0;// Tall Grass Shader

	public static final int SHADER_DEFAULT = 1;//Standard Lighting Shader</option>

	public static final int SHADER_SKY = 10;//Sky Shader</option>

	public static final int SHADER_WATER = 17;//Water Shader</option>

	public static final int SHADER_LIGHTING30 = 29;//Lighting 3.0 Shader</option>

	public static final int SHADER_TILE = 32;//Tiled Shader</option>

	public static final int SHADER_NOLIGHTING = 33;//No Lighting Shader</option>
	
 

	public int type;

	public BSShaderType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
