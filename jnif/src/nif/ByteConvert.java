package nif;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import nif.tools.io.LittleEndianPrimitiveBytes;

public class ByteConvert extends LittleEndianPrimitiveBytes
{
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

	//BELOW ARE CONVIENCE

	public static int toInt(byte[] b)
	{
		return LittleEndianPrimitiveBytes.toInt(b);
	}

	public static float toFloat(byte[] b)
	{
		return LittleEndianPrimitiveBytes.toFloat(b);
	}

	public static short toShort(byte[] b)
	{
		return LittleEndianPrimitiveBytes.toShort(b);
	}

	public static byte readByte(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readByte(stream);
	}

	public static short readUnsignedByte(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readUnsignedByte(stream);
	}

	public static short byteToUnsigned(byte in)
	{
		return LittleEndianPrimitiveBytes.byteToUnsigned(in);
	}

	public static byte[] readBytes(int numBytes, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readBytes(numBytes, stream);
	}

	public static short[] readUnsignedBytes(int numBytes, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readUnsignedBytes(numBytes, stream);
	}

	public static int readInt(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readInt(stream);
	}

	public static int[] readInts(int numInts, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readInts(numInts, stream);
	}

	public static short readShort(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readShort(stream);
	}

	public static int readUnsignedShort(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readUnsignedShort(stream);
	}

	public static int shortToUnsigned(short in)
	{
		return LittleEndianPrimitiveBytes.shortToUnsigned(in);
	}

	public static int[] readUnsignedShorts(int numBytes, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readUnsignedShorts(numBytes, stream);
	}

	public static short[] readShorts(int numShorts, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readShorts(numShorts, stream);
	}

	public static float readFloat(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readFloat(stream);
	}

	public static float[] readFloats(int numFloats, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readFloats(numFloats, stream);
	}

	public static String readSizedString(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readSizedString(stream);
	}

	public static String[] readSizedStrings(int numStrings, InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readSizedStrings(numStrings, stream);
	}

	public static String readShortString(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readShortString(stream);
	}

	public static String readRealShortString(InputStream stream) throws IOException
	{
		return LittleEndianPrimitiveBytes.readRealShortString(stream);
	}

	public static boolean readBool(ByteBuffer stream, NifVer nifVer) throws IOException
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

	public static boolean[] readBooleans(int numBools, ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean[] bs = new boolean[numBools];
		for (int i = 0; i < numBools; i++)
		{
			bs[i] = readBool(stream, nifVer);
		}
		return bs;

	}

	/**
	 <compound name="string" niflibtype="IndexString" nifskopetype="string">
	
	 A string type.
	 
	 <add name="String" type="SizedString" ver2="20.0.0.5">The normal string.</add>
	 <add name="Index" type="StringIndex" ver1="20.1.0.3">The string index.</add>
	 </compound>
	 */

	public static String readIndexString(ByteBuffer stream, NifVer nifVer) throws IOException
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

	///////////////////////////////////// B Y T E B U F F E R ////////////////////////////////////////////////////////////
	public static byte readByte(ByteBuffer stream) throws IOException
	{
		return stream.get();
	}

	public static short readUnsignedByte(ByteBuffer stream) throws IOException
	{
		return byteToUnsigned(readByte(stream));
	}

	public static byte[] readBytes(int numBytes, ByteBuffer stream) throws IOException
	{
		byte[] b = new byte[numBytes];
		stream.get(b);
		return b;
	}

	public static short[] readUnsignedBytes(int numBytes, ByteBuffer stream) throws IOException
	{
		byte[] b = readBytes(numBytes, stream);
		short[] s = new short[numBytes];
		for (int i = 0; i < b.length; i++)
			s[i] = byteToUnsigned(b[i]);
		return s;
	}

	public static int readInt(ByteBuffer stream) throws IOException
	{
		return stream.getInt();
	}

	public static int[] readInts(int numInts, ByteBuffer stream) throws IOException
	{
		int[] is = new int[numInts];
		for (int i = 0; i < numInts; i++)
			is[i] = stream.getInt();
		return is;
	}

	public static short readShort(ByteBuffer stream) throws IOException
	{
		return stream.getShort();
	}

	public static int readUnsignedShort(ByteBuffer stream) throws IOException
	{
		return shortToUnsigned(readShort(stream));
	}

	public static int[] readUnsignedShorts(int numShorts, ByteBuffer stream) throws IOException
	{
		short[] s1 = readShorts(numShorts, stream);
		int[] s2 = new int[numShorts];
		for (int i = 0; i < numShorts; i++)
			s2[i] = shortToUnsigned(s1[i]);
		return s2;
	}

	public static short[] readShorts(int numShorts, ByteBuffer stream) throws IOException
	{
		short[] ss = new short[numShorts];
		for (int i = 0; i < numShorts; i++)
			ss[i] = stream.getShort();
		return ss;
	}
	
	public static long readLong(ByteBuffer stream) throws IOException
	{
		return stream.getLong();
	}

	public static float readFloat(ByteBuffer stream) throws IOException
	{
		return stream.getFloat();

	}
	
	public static double readDouble(ByteBuffer stream) throws IOException
	{
		return stream.getDouble();

	}

	public static float[] readFloats(int numFloats, ByteBuffer stream) throws IOException
	{
		float[] fs = new float[numFloats];
		for (int i = 0; i < numFloats; i++)
			fs[i] = stream.getFloat();
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
	public static String readSizedString(ByteBuffer stream) throws IOException
	{
		int len = readInt(stream);
		byte[] buffer = new byte[len];
		stream.get(buffer);
		return new String(buffer);
	}

	public static String[] readSizedStrings(int numStrings, ByteBuffer stream) throws IOException
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

	public static String readByteString(ByteBuffer stream) throws IOException
	{
		short len = readUnsignedByte(stream);
		byte[] buffer = new byte[len];
		stream.get(buffer);
		return new String(buffer);
	}

	public static String readShortString(ByteBuffer stream) throws IOException
	{
		int len = readUnsignedShort(stream);
		byte[] buffer = new byte[len];
		stream.get(buffer);
		return new String(buffer);
	}
}