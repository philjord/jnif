package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class EffectType
{
	/**
	 * The type of information that's store in a texture used by a NiTextureEffect.
	 */
	public static final int EFFECT_PROJECTED_LIGHT = 0;// Apply a projected light texture.</option>

	public static final int EFFECT_PROJECTED_SHADOW = 1;// Apply a projected shaddow texture.</option>

	public static final int EFFECT_ENVIRONMENT_MAP = 2;// Apply an environment map texture.</option>

	public static final int EFFECT_FOG_MAP = 3;// Apply a fog map texture.</option>

	public int type;

	public EffectType(ByteBuffer stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}

}
