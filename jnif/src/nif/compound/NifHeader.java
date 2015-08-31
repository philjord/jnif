package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifHeader
{
	/**
	 <compound name="Header">

	 The NIF file header.

	 <add name="Header String" type="HeaderString">
	 'NetImmerse File Format x.x.x.x' (versions <= 10.0.1.2) or 'Gamebryo File Format x.x.x.x' (versions >= 10.1.0.0), with x.x.x.x the version written out. Ends with a newline character (0x0A).
	 </add>
	 <add name="Copyright" type="LineString" arr1="3" ver2="3.1"/>
	 <add name="Version" type="FileVersion" default="0x04000002" ver1="3.3.0.13">
	 The NIF version, in hexadecimal notation: 0x04000002, 0x0401000C, 0x04020002, 0x04020100, 0x04020200, 0x0A000100, 0x0A010000, 0x0A020000, 0x14000004, ...
	 </add>
	 <add name="Endian Type" type="EndianType" default="1" ver1="20.0.0.4">Determines the endianness of the data in the file.</add>
	 <add name="User Version" type="uint" ver1="10.1.0.0">
	 An extra version number, for companies that decide to modify the file format.
	 </add>
	 <add name="Num Blocks" type="uint" ver1="3.3.0.13">Number of file objects.</add>
	 <add name="User Version 2" type="uint" default="0" cond="(User Version == 10) || (User Version == 11)" ver1="10.1.0.0">
	 This also appears to be the extra user version number and must be set in some circumstances.
	 </add>
	 <add name="Export Info" type="ExportInfo" ver1="10.0.1.2" ver2="10.0.1.2"/>
	 <add name="Export Info" type="ExportInfo" ver1="10.1.0.0" cond="(User Version == 10) || (User Version == 11)"/>
	 <add name="Num Block Types" type="ushort" ver1="10.0.1.0">Number of object types in this NIF file.</add>
	 <add name="Block Types" type="SizedString" arr1="Num Block Types" ver1="10.0.1.0">List of all object types used in this NIF file.</add>
	 <add name="Block Type Index" type="BlockTypeIndex" arr1="Num Blocks" ver1="10.0.1.0">
	 Maps file objects on their corresponding type: first file object is of type object_types[object_type_index[0]], the second of object_types[object_type_index[1]], etc.
	 </add>
	 <add name="Block Size" type="uint" arr1="Num Blocks" ver1="20.2.0.7">Array of block sizes?</add>
	 <add name="Num Strings" type="uint" ver1="20.1.0.3">Number of strings.</add>
	 <add name="Max String Length" type="uint" ver1="20.1.0.3">Maximum string length.</add>
	 <add name="Strings" type="SizedString" arr1="Num Strings" ver1="20.1.0.3">Strings.</add>
	 <add name="Unknown Int 2" type="uint" default="0" ver1="10.0.1.0">Unknown.</add>
	 </compound>
	 */

	public String headerString = "";

	public NifVer nifVer;

	public byte endianType = 1;

	public int numBlocks = 0;

	public NifExportInfo exportInfo;

	public short numBlockTypes = 0;

	public String[] blockTypes;

	public short[] blockTypeIndex;

	public int[] blockSizes;

	public int numStrings;

	public int maxStringLength;

	public String[] strings;

	public int unknownInt2 = 0;

	public NifHeader(String fileName)
	{
		nifVer = new NifVer(fileName, 0, 0, 0);
	}

	public boolean readFromStream(InputStream stream) throws IOException
	{
		// header loads till first linefeed (ascii 10) or 64 max (not checked)
		int len = 0;
		char c = (char) stream.read();
		len++;
		while (c != 10 && len < 65)
		{
			headerString += c;
			c = (char) stream.read();
			len++;
		}

		nifVer.LOAD_VER = ByteConvert.readInt(stream);
		// if version bad return
		if (!checkVersion(headerString))
			return false;

		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4)
		{
			endianType = ByteConvert.readByte(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			nifVer.LOAD_USER_VER = ByteConvert.readInt(stream);
		}

		numBlocks = ByteConvert.readInt(stream);

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			if ((nifVer.LOAD_USER_VER >= 10 || (nifVer.LOAD_USER_VER == 1 && nifVer.LOAD_VER != NifVer.VER_10_2_0_0)) && !nifVer.isBP())
			{
				nifVer.LOAD_USER_VER2 = ByteConvert.readInt(stream);
			}
		}

		if (nifVer.LOAD_VER == NifVer.VER_10_0_1_2
				|| (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0
						&& (nifVer.LOAD_USER_VER >= 10 || (nifVer.LOAD_USER_VER == 1 && nifVer.LOAD_VER != NifVer.VER_10_2_0_0)) && !nifVer
							.isBP()))
		{
			exportInfo = new NifExportInfo(nifVer.LOAD_USER_VER, stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			numBlockTypes = ByteConvert.readShort(stream);
			blockTypes = new String[numBlockTypes];
			for (int i = 0; i < numBlockTypes; i++)
			{
				blockTypes[i] = ByteConvert.readSizedString(stream);
			}
			blockTypeIndex = new short[numBlocks];
			for (int i = 0; i < numBlocks; i++)
			{
				blockTypeIndex[i] = ByteConvert.readShort(stream);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			blockSizes = new int[numBlocks];
			for (int i = 0; i < numBlocks; i++)
			{
				blockSizes[i] = ByteConvert.readInt(stream);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			numStrings = ByteConvert.readInt(stream);
			maxStringLength = ByteConvert.readInt(stream);
			strings = new String[numStrings];
			for (int i = 0; i < numStrings; i++)
			{
				strings[i] = ByteConvert.readSizedString(stream);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			unknownInt2 = ByteConvert.readInt(stream);
		}

		return true;
	}

	public String toString()
	{
		return toString(false);
	}

	public String toString(boolean verbose)
	{
		String out = "  Header String:  " + headerString + "\n";
		out += "  Version:  " + nifVer.LOAD_VER + " ( 0x" + Integer.toHexString(nifVer.LOAD_VER) + " )\n";
		out += "  Endian Type:  " + endianType + "\n";
		out += "  User Version:  " + nifVer.LOAD_USER_VER + "\n";
		out += "  Num Blocks:  " + numBlocks + "\n";
		if (nifVer.LOAD_VER == NifVer.VER_10_0_1_2
				|| (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0
						&& (nifVer.LOAD_USER_VER >= 10 || (nifVer.LOAD_USER_VER == 1 && nifVer.LOAD_VER != NifVer.VER_10_2_0_0)) && !nifVer
							.isBP()))
		{
			out += "    Creator:  " + exportInfo.creator + "\n";
			out += "    Export Type:  " + exportInfo.exportInfo1 + "\n";
			out += "    Export Script:  " + exportInfo.exportInfo2 + "\n";
			out += "  	userVersion2:  " + nifVer.LOAD_USER_VER2 + "\n";
		}
		out += "  Num Block Types:  " + numBlockTypes + "\n";
		if (verbose)
		{
			for (int i = 0; i < numBlockTypes; i++)
			{
				out += "    Block Types[" + i + "]:  " + blockTypes[i] + "\n";
			}

			for (int i = 0; i < numBlocks; i++)
			{
				out += "    Block Type Index[" + i + "]:  " + blockTypeIndex[i] + " " + blockTypes[blockTypeIndex[i]] + "\n";
			}
		}
		out += "  Unknown Int 2:  " + unknownInt2 + "\n";
		return out;
	}

	private boolean checkVersion(String ver)
	{

		// make sure this is a NIF file
		if (ver.startsWith("NetImmerse File Format") || ver.startsWith("Gamebryo File Format"))
		{
			// supported versions
			/*
			 * if (version.equals("NetImmerse File Format, Version 4.0.0.2") || version.equals("NetImmerse File Format,
			 * Version 4.1.0.12") || version.equals("NetImmerse File Format, Version 4.2.0.2") ||
			 * version.equals("NetImmerse File Format, Version 4.2.1.0") || version.equals("NetImmerse File Format,
			 * Version 4.2.2.0") || version.equals("NetImmerse File Format, Version 10.0.1.0") // below are oblivion ||
			 * version.equals("NetImmerse File Format, Version 10.0.1.106") || version.equals("Gamebryo File Format,
			 * Version 10.1.0.0") || version.equals("Gamebryo File Format, Version 10.2.0.0")
			 */

			//if (ver.equals("Gamebryo File Format, Version 20.0.0.4") || ver.equals("Gamebryo File Format, Version 20.0.0.5"))
			return true;
		}

		// anything else: unsupported
		System.out.println("Unsupported header::" + nifVer.LOAD_VER);
		return false;

	}
}
