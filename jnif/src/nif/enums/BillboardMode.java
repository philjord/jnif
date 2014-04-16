package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BillboardMode
{
	/**
	 * Determines the way the billboard will react to the camera.
	 */
	public static final int ALWAYS_FACE_CAMERA = 0;// The billboard will always face the camera.</option>

	public static final int ROTATE_ABOUT_UP = 1;// The billboard will only rotate around the up axis.</option>

	public static final int RIGID_FACE_CAMERA = 2;// Rigid Face Camera.</option>

	public static final int ALWAYS_FACE_CENTER = 3;// Always Face Center.</option>

	public static final int RIGID_FACE_CENTER = 4;// Rigid Face Center.</option>

	public static final int ROTATE_ABOUT_UP2 = 9;// The billboard will only rotate around the up axis (same as ROTATE_ABOUT_UP?).

	public short mode;

	public BillboardMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readShort(stream);
	}

}
