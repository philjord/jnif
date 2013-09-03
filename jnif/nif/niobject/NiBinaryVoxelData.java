package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;

public class NiBinaryVoxelData extends NiObject
{
	/**
	 <niobject name="NiBinaryVoxelData" abstract="0" inherit="NiObject" ver1="3.1">

	 Voxel data object.
	 
	 <add name="Unknown Short 1" type="ushort">Unknown.</add>
	 <add name="Unknown Short 2" type="ushort">Unknown.</add>
	 <add name="Unknown Short 3" type="ushort">Unknown. Is this^3 the Unknown Bytes 1 size?</add>
	 <add name="Unknown 7 Floats" type="float" arr1="7">Unknown.</add>
	 <add name="Unknown Bytes 1" type="byte" arr1="7" arr2="12">Unknown. Always a multiple of 7.</add>
	 <add name="Num Unknown Vectors" type="uint">Unknown.</add>
	 <add name="Unknown Vectors" type="Vector4" arr1="Num Unknown Vectors">Vectors on the unit sphere.</add>
	 <add name="Num Unknown Bytes 2" type="uint">Unknown.</add>
	 <add name="Unknown Bytes 2" type="byte" arr1="Num Unknown Bytes 2">Unknown.</add>
	 <add name="Unknown 5 Ints" type="uint" arr1="5">Unknown.</add>
	 </niobject>
	 */

	public short unknownShort1;

	public short unknownShort2;

	public short unknownShort3;

	public float[] unknown7Floats;

	public byte[][] unknownBytes1;

	public int numUnknownVectors;

	public NifVector4[] unknownVectors;

	public int numUnknownBytes2;

	public byte[] unknownBytes2;

	public int[] unknown5Ints;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort1 = ByteConvert.readShort(stream);
		unknownShort2 = ByteConvert.readShort(stream);
		unknownShort3 = ByteConvert.readShort(stream);
		unknown7Floats = new float[7];
		for (int i = 0; i < 7; i++)
		{
			unknown7Floats[i] = ByteConvert.readFloat(stream);
		}

		unknownBytes1 = new byte[7][12];
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				unknownBytes1[i][j] = ByteConvert.readByte(stream);
			}
		}

		numUnknownVectors = ByteConvert.readInt(stream);
		unknownVectors = new NifVector4[numUnknownVectors];
		for (int i = 0; i < numUnknownVectors; i++)
		{
			unknownVectors[i] = new NifVector4(stream);
		}

		numUnknownBytes2 = ByteConvert.readInt(stream);
		unknownBytes2 = new byte[numUnknownBytes2];
		for (int i = 0; i < numUnknownBytes2; i++)
		{
			unknownBytes2[i] = ByteConvert.readByte(stream);
		}

		unknown5Ints = new int[5];
		for (int i = 0; i < 5; i++)
		{
			unknown5Ints[i] = ByteConvert.readInt(stream);
		}

		return success;
	}
}