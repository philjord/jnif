package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;

public class NiAlphaProperty extends NiProperty
{
	/**
	 <niobject name="NiAlphaProperty" abstract="0" inherit="NiProperty">
	
	 Transparency. Flags 0x00ED.
	 
	 <add name="Flags" type="Flags" default="237">
	
	 Bit 0 : alpha blending enable
	 Bits 1-4 : source blend mode
	 Bits 5-8 : destination blend mode
	 Bit 9 : alpha test enable
	 Bit 10-12 : alpha test mode
	 Bit 13 : no sorter flag ( disables triangle sorting )
	
	 blend modes (glBlendFunc):
	 0000 GL_ONE
	 0001 GL_ZERO
	 0010 GL_SRC_COLOR
	 0011 GL_ONE_MINUS_SRC_COLOR
	 0100 GL_DST_COLOR
	 0101 GL_ONE_MINUS_DST_COLOR
	 0110 GL_SRC_ALPHA
	 0111 GL_ONE_MINUS_SRC_ALPHA
	 1000 GL_DST_ALPHA
	 1001 GL_ONE_MINUS_DST_ALPHA
	 1010 GL_SRC_ALPHA_SATURATE
	 
	
	 test modes (glAlphaFunc):
	 000 GL_ALWAYS
	 001 GL_LESS
	 010 GL_EQUAL
	 011 GL_LEQUAL
	 100 GL_GREATER
	 101 GL_NOTEQUAL
	 110 GL_GEQUAL
	 111 GL_NEVER
	 
	 </add>
	 <add name="Threshold" type="byte">Threshold for alpha testing (see: glAlphaFunc)</add>
	 </niobject>
	 */
	// 1001 0010 1110 1100 = 37612
	// 0001 0010 1110 1100 = 4844

	// blend modes (glBlendFunc):
	public static int GL_ONE = 0;

	public static int GL_ZERO = 1;

	public static int GL_SRC_COLOR = 2;

	public static int GL_ONE_MINUS_SRC_COLOR = 3;

	public static int GL_DST_COLOR = 4;

	public static int GL_ONE_MINUS_DST_COLOR = 5;

	public static int GL_SRC_ALPHA = 6;

	public static int GL_ONE_MINUS_SRC_ALPHA = 7;

	public static int GL_DST_ALPHA = 8;

	public static int GL_ONE_MINUS_DST_ALPHA = 9;

	public static int GL_SRC_ALPHA_SATURATE = 10;

	//test modes (glAlphaFunc):
	public static int GL_ALWAYS = 0;

	public static int GL_LESS = 1;

	public static int GL_EQUAL = 2;

	public static int GL_LEQUAL = 3;

	public static int GL_GREATER = 4;

	public static int GL_NOTEQUAL = 5;

	public static int GL_GEQUAL = 6;

	public static int GL_NEVER = 7;

	public NifFlags flags;

	public short threshold;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		threshold = ByteConvert.readUnsignedByte(stream);

		//morrowind doesn't appear to have the concept of alpha testing in the flags
		if (nifVer.LOAD_VER < NifVer.VER_10_0_1_0)
		{
			if (flags.flags == 237)
			{
				flags.flags = 4608 + 237; 
				threshold = 64;// must pick a goody here?
			}
			else if (flags.flags == 13 || flags.flags == 4097)
			{
				// 4097 looks like a bug to me
				flags.flags = 4608 + 13;
				threshold = 64;
			}
		}
		return success;
	}

	public boolean alphaBlendingEnable()
	{
		return (flags.flags & 0x0001) != 0;
	}

	public int sourceBlendMode()
	{
		return ((flags.flags >> 1) & 0x00f);
	}

	public int destinationBlendMode()
	{
		return ((flags.flags >> 5) & 0x00f);
	}

	public boolean alphaTestEnabled()
	{
		boolean alphaTest = ((flags.flags >> 9) & 0x0001) != 0;

		// Temporary Weapon Blood fix for FO4
		if (this.nVer.BS_Version == 130)
			alphaTest |= (flags.flags == 20547);

		return alphaTest;
	}

	public int alphaTestMode()
	{
		return ((flags.flags >> 10) & 0x007);
	}

	public boolean noSorterFlag()
	{
		return ((flags.flags >> 13) & 0x0001) != 0;
	}
}