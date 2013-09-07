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

}