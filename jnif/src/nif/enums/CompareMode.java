package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class CompareMode
{
	/**
	 * This enum contains the options for doing z buffer or stecil buffer tests.
	 */
	public static final int TEST_NEVER = 0;// Test will allways return false. Nothing is drawn at all.

	public static final int TEST_LESS = 1;// The test will only succeed if the pixel is nearer than the previous pixel.

	public static final int TEST_EQUAL = 2;// Test will only succeed if the z value of the pixel to be drawn is equal to the value of the previous

	// drawn pixel.
	public static final int TEST_LESS_EQUAL = 3;// Test will succeed if the z value of the pixel to be drawn is smaller than or equal to the value in

	// the Z Buffer.

	public static final int TEST_GREATER = 4;// Opposite of TEST_LESS.</option>

	public static final int TEST_NOT_EQUAL = 5;// Test will succeed if the z value of the pixel to be drawn is NOT equal to the value of the

	// previously drawn pixel.

	public static final int TEST_GREATER_EQUAL = 6;// Opposite of TEST_LESS_EQUAL.</option>

	public static final int TEST_ALWAYS = 7;// Test will allways succeed. The Z Buffer value is ignored.

	public int mode;

	public CompareMode(ByteBuffer stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
