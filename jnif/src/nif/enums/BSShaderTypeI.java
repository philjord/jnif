package nif.enums;

import java.nio.ByteBuffer;

public interface BSShaderTypeI
{
	/**
	subs  = BSShaderType and BSLightingShaderType
	 */
 
	public int getType();

	//MUST override
	public static BSShaderTypeI load(ByteBuffer stream) {
		return null;
	}
}
