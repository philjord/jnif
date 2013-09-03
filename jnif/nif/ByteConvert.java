package nif;

import java.io.IOException;
import java.io.InputStream;

public class ByteConvert
{
	public static int toInt(byte[] b)
	{
		return (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | (b[0] & 0xff));
	}

	private static float toFloat(byte[] b)
	{

		return Float.intBitsToFloat(ByteConvert.toInt(b));
	}

	public static short toShort(byte[] b)
	{
		return (short) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
	}

	public static byte readByte(InputStream stream) throws IOException
	{
		byte[] b = new byte[1];
		stream.read(b, 0, 1);
		return b[0];
	}

	public static short readUnsignedByte(InputStream stream) throws IOException
	{
		return byteToUnsigned(readByte(stream));
	}

	public static short byteToUnsigned(byte in)
	{
		return (short) (in & 0xFF);
	}

	public static byte[] readBytes(int numBytes, InputStream stream) throws IOException
	{
		byte[] b = new byte[numBytes];
		stream.read(b, 0, numBytes);
		return b;
	}

	public static short[] readUnsignedBytes(int numBytes, InputStream stream) throws IOException
	{
		byte[] b = readBytes(numBytes, stream);
		short[] s = new short[numBytes];
		for (int i = 0; i < b.length; i++)
			s[i] = byteToUnsigned(b[i]);
		return s;
	}

	public static boolean readBool(InputStream stream, NifVer nifVer) throws IOException
	{
		if (nifVer.LOAD_VER >= NifVer.VER_4_1_0_1)
		{
			//  as bytes from 4.1.0.1 on 
			return readByte(stream) != 0;
		}
		else
		{
			//  as integers before version 4.1.0.1 
			return readInt(stream) != 0;
		}
	}

	public static boolean[] readBooleans(int numBools, InputStream stream, NifVer nifVer) throws IOException
	{
		boolean[] bs = new boolean[numBools];
		for (int i = 0; i < numBools; i++)
		{
			bs[i] = readBool(stream, nifVer);
		}
		return bs;

	}

	public static int readInt(InputStream stream) throws IOException
	{
		byte b[] = new byte[4];
		stream.read(b, 0, 4);
		return ByteConvert.toInt(b);
	}

	public static int[] readInts(int numInts, InputStream stream) throws IOException
	{
		int[] is = new int[numInts];
		for (int i = 0; i < numInts; i++)
		{
			is[i] = readInt(stream);
		}
		return is;
	}

	public static short readShort(InputStream stream) throws IOException
	{
		byte b[] = new byte[2];
		stream.read(b, 0, 2);
		return ByteConvert.toShort(b);
	}

	public static int readUnsignedShort(InputStream stream) throws IOException
	{
		return shortToUnsigned(readShort(stream));
	}

	public static int shortToUnsigned(short in)
	{
		return (in & 0xFFFF);
	}

	public static int[] readUnsignedShorts(int numBytes, InputStream stream) throws IOException
	{
		short[] s1 = readShorts(numBytes, stream);
		int[] s2 = new int[numBytes];
		for (int i = 0; i < s1.length; i++)
			s2[i] = shortToUnsigned(s1[i]);
		return s2;
	}

	public static short[] readShorts(int numShorts, InputStream stream) throws IOException
	{
		short[] ss = new short[numShorts];
		for (int i = 0; i < numShorts; i++)
		{
			ss[i] = readShort(stream);
		}
		return ss;
	}

	public static float readFloat(InputStream stream) throws IOException
	{
		byte b[] = new byte[4];
		stream.read(b, 0, 4);
		return ByteConvert.toFloat(b);
	}

	public static float[] readFloats(int numFloats, InputStream stream) throws IOException
	{
		float[] fs = new float[numFloats];
		for (int i = 0; i < numFloats; i++)
		{
			fs[i] = readFloat(stream);
		}
		return fs;
	}

	/**
	 * <compound name="SizedString" niflibtype="string" nifskopetype="sizedstring">
	 * 
	 * A string of given length.
	 * 
	 * <add name="Length" type="uint">The string length.</add> <add name="Value" type="char" arr1="Length">The string itself.</add> </compound>
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static String readSizedString(InputStream stream) throws IOException
	{
		int len = readInt(stream);
		byte[] buffer = new byte[len];
		stream.read(buffer, 0, len);
		return new String(buffer);
	}

	public static String[] readSizedStrings(int numStrings, InputStream stream) throws IOException
	{
		String[] ss = new String[numStrings];
		for (int i = 0; i < numStrings; i++)
		{
			ss[i] = readSizedString(stream);
		}
		return ss;
	}

	/**
	 <compound name="ShortString" ver1="10.1.0.0" niflibtype="ShortString" nifskopetype="shortstring">

	 Another string format, for short strings.  Specific to Bethesda-specific header tags.
	 
	 <add name="Length" type="byte">The string length.</add>
	 <add name="Value" type="char" arr1="Length">
	 The string itself, null terminated (the null terminator is taken into account in the length byte).
	 </add>
	 </compound>
	 */

	public static String readShortString(InputStream stream) throws IOException
	{
		short len = readUnsignedByte(stream);
		byte[] buffer = new byte[len];
		stream.read(buffer, 0, len);
		return new String(buffer);
	}

	/**
	 <compound name="string" niflibtype="IndexString" nifskopetype="string">

	 A string type.
	 
	 <add name="String" type="SizedString" ver2="20.0.0.5">The normal string.</add>
	 <add name="Index" type="StringIndex" ver1="20.1.0.3">The string index.</add>
	 </compound>
	 */

	public static String readIndexString(InputStream stream, NifVer nifVer) throws IOException
	{
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			return readSizedString(stream);
		}
		if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			//NOTE index string must have been set from the strings in the header before calls to this method
			int index = readInt(stream);
			if (index >= 0 && index < nifVer.indexStrings.length)
			{
				return nifVer.indexStrings[index];
			}
			else
			{
				return "";
			}
		}

		new Exception("Bad Nif Ver!!!!" + nifVer.LOAD_VER).printStackTrace();
		return null;

	}

	/*
		private static float toFloat2(byte[] b)
		{
			// See Float.intBitsToFloat
			// But all native calls removed to allow hotspot to charge in

			int bits = ByteConvert.toInt(b);
			float f2 = 0;
			if (bits == 0x7f800000)
			{
				f2 = Float.POSITIVE_INFINITY;
			}
			else if (bits == 0xff800000)
			{
				f2 = Float.NEGATIVE_INFINITY;
			}
			else if ((0x7f800001 <= bits && bits <= 0x7fffffff) || (0xff800001 <= bits && bits <= 0xffffffff))
			{
				f2 = Float.NaN;
			}
			else
			{
				int s = ((bits >> 31) == 0) ? 1 : -1;
				int e = ((bits >> 23) & 0xff);
				int m = (e == 0) ? (bits & 0x7fffff) << 1 : (bits & 0x7fffff) | 0x800000;
				//f2 = (float) ((s * m) * Math.pow(2, e - 150));
				f2 = (float) ((s * m) * pow(e - 150));
			}

			return f2;

		}

		//fixed at 2 raised to exp
		public static double pow(int exp)
		{
			if (exp > 0)
			{
				return (2 * pow(exp - 1));
			}
			else if (exp < 0)
			{
				return (1 / pow(-exp));
			}
			else
			{
				return 1;
			}
		}
	*/
}