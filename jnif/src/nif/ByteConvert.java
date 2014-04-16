package nif;

import java.io.IOException;
import java.io.InputStream;

import tools.io.LittleEndianPrimitiveBytes;

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

}