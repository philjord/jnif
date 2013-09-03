package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class TargetColor
{
	/**
	 * Used by NiPoint3InterpControllers to select which type of color in the controlled object that will be animated.
	 */
	public static final int TC_AMBIENT = 0;// Control the ambient color.</option>

	public static final int TC_DIFFUSE = 1;// Control the diffuse color.</option>

	public static final int TC_SPECULAR = 2;// Control the specular color.</option>

	public static final int TC_SELF_ILLUM = 3;// Control the self illumination color.</option>

	public short color;

	public TargetColor(InputStream stream) throws IOException
	{
		color = ByteConvert.readShort(stream);
	}

}
