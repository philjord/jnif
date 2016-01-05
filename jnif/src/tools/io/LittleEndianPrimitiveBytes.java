package tools.io;

import java.io.IOException;
import java.io.InputStream;

public class LittleEndianPrimitiveBytes
{

	public static int toInt(byte[] b)
	{
		return (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | (b[0] & 0xff));
	}

	public static short toShort(byte[] b)
	{
		return (short) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
	}

	public static byte readByte(InputStream stream) throws IOException
	{
		byte[] b = new byte[1];
		if(stream.read(b, 0, 1)==-1)
			throw new IOException("unexpected end of stream");
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
		if(stream.read(b, 0, numBytes)==-1)
			throw new IOException("unexpected end of stream");
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

	public static int readInt(InputStream stream) throws IOException
	{
		byte b[] = new byte[4];
		if(stream.read(b, 0, 4)==-1)
			throw new IOException("unexpected end of stream");
		return toInt(b);
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
		if(stream.read(b, 0, 2)==-1)
			throw new IOException("unexpected end of stream");
		return toShort(b);
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
		if(stream.read(b, 0, 4)==-1)
			throw new IOException("unexpected end of stream");
		return toFloat(b);
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
		if(stream.read(buffer, 0, len)==-1)
			throw new IOException("unexpected end of stream");
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
		if(stream.read(buffer, 0, len)==-1)
			throw new IOException("unexpected end of stream");
		return new String(buffer);
	}
	
	public static String readRealShortString(InputStream stream) throws IOException
	{
		int len = readUnsignedShort(stream);
		byte[] buffer = new byte[len];
		if(stream.read(buffer, 0, len)==-1)
			throw new IOException("unexpected end of stream");
		return new String(buffer);
	}

	//private static HashMap<Integer, Integer> floatHitCount = new HashMap<Integer, Integer>();

	//private static int c = 0;

	public static float toFloat(byte[] b)
	{

		//System.out.println("1.0 : "+ Integer.toBinaryString(Float.floatToRawIntBits(1.0f)) + " " + Float.floatToRawIntBits(1.0f));

		int i = toInt(b);
		// output the top hits, NOTE! no discernable saving from this crap!
	/*	c++;
		
		if (!floatHitCount.containsKey(i))
			floatHitCount.put(i, 0);

		floatHitCount.put(i, floatHitCount.get(i) + 1);

		if (c % 10000 == 0)
		{
			for (Integer key : floatHitCount.keySet())
			{
				if (floatHitCount.get(key) > 10000)
				{
					System.out.println("key= " + key + " count= " + floatHitCount.get(key) + " f=" + Float.intBitsToFloat(key));
				}
			}
			System.out.println("" + c + "----------------------");
		}*/

	/*	switch (i)
		{
			case (0)://count= 287699
				return 0f;
			case (1065353216)://count= 1085445
				return 1.0f;
			case (1053345994)://count= 123019
				return 0.3921569f;
			case (1056964608)://count= 43501
				return 0.5f;
			case (1061734602)://count= 11479 
				return 0.7843138f;
			case (-1006632960)://count= 27376 
				return -512.0f;
			case (1061158912)://count= 16092 
				return 0.75f;
			case (1050253722)://count= 11991 
				return 0.3f;
			case (1207959552)://count= 14100 
				return 131072.0f;
			case (1048576000)://count= 18146 
				return 0.25f;
			case (1058444951)://count= 11001 
				return 0.5882353f;
			default:*/
				return Float.intBitsToFloat(i);
	//	}

	}

}
