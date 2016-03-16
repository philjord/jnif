package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class TexType
{
	/**
	 * The type of texture.
	 */

	public static final int BASE_MAP = 0;// The basic texture used by most meshes.

	public static final int DARK_MAP = 1;// Used to darken the model with false lighting.

	public static final int DETAIL_MAP = 2;// Combined with base map for added detail. Usually tiled over the mesh many times for close-up view.

	public static final int GLOSS_MAP = 3;// Allows the specularity (glossyness) of an object to differ across its surface.

	public static final int GLOW_MAP = 4;// Creates a glowing effect. Basically an incandescence map.

	public static final int BUMP_MAP = 5;// Used to make the object appear to have more detail than it really does.

	public static final int DECAL_0_MAP = 6;// For placing images on the object like stickers.

	public static final int DECAL_1_MAP = 7;// For placing images on the object like stickers.

	public int texType;

	public TexType(ByteBuffer stream) throws IOException
	{
		texType = ByteConvert.readInt(stream);
	}

}
