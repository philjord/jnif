package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifAdditionalDataInfo
{
	/**
	 <compound name="AdditionalDataInfo">
	 <add name="Data Type" type="int">Type of data</add>
	 <add name="Block Size" type="int">Size of Block</add>
	 <add name="Num Blocks" type="int">Unknown</add>
	 <add name="Block Size 2" type="int">Unknown</add>
	 <add name="Block Index" type="int">Unknown</add>
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 </compound>
	 */

	public int dataType;

	public int blockSize;

	public int numBlocks;

	public int blockSize2;

	public int blockIndex;

	public int unknownInt1;

	public byte unknownByte1;

	public NifAdditionalDataInfo(ByteBuffer stream) throws IOException
	{
		dataType = ByteConvert.readInt(stream);
		blockSize = ByteConvert.readInt(stream);
		numBlocks = ByteConvert.readInt(stream);
		blockSize2 = ByteConvert.readInt(stream);
		blockIndex = ByteConvert.readInt(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownByte1 = ByteConvert.readByte(stream);
	}

}
