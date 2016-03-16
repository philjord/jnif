package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifAdditionalDataBlock
{
	/**
	 <compound name="AdditionalDataBlock">
	 <add name="Has Data" type="bool">Has data</add>
	 <add name="Block Size" type="int" cond="Has Data">Size of Block</add>
	 <add name="Num Blocks" type="int" cond="Has Data">Unknown</add>
	 <add name="Block Offsets" type="int" arr1="Num Blocks" cond="Has Data">Unknown</add>
	 <add name="Num Data" type="int" cond="Has Data">Unknown</add>
	 <add name="Data Sizes" type="int" arr1="Num Data" cond="Has Data">Unknown</add>
	 <add name="Data" type="byte" arr1="Num Data" arr2="Block Size" cond="Has Data">Unknown</add>
	 </compound>
	 */

	public boolean hasData;

	public int blockSize;

	public int numBlocks;

	public int blockOffsets;

	public int numData;

	public int[] dataSizes;

	public byte[][] data;

	public NifAdditionalDataBlock(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		hasData = ByteConvert.readBool(stream, nifVer);
		if (hasData)
		{
			blockSize = ByteConvert.readInt(stream);
			numBlocks = ByteConvert.readInt(stream);
			blockOffsets = ByteConvert.readInt(stream);
			numData = ByteConvert.readInt(stream);
			dataSizes = ByteConvert.readInts(numData, stream);
			data = new byte[numData][];
			for (int i = 0; i < numData; i++)
			{
				data[i] = ByteConvert.readBytes(blockSize, stream);
			}

		}
	}

}
