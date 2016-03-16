package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifAdditionalDataBlock;
import nif.compound.NifAdditionalDataInfo;

public class NiAdditionalGeometryData extends NiObject
{
	/**
	 <niobject name="NiAdditionalGeometryData" inherit="NiObject">
	 <add name="Num Vertices" type="ushort">Number of vertices</add>
	 <add name="Num Block Infos" type="uint">Information about additional data blocks</add>
	 <add name="Block Infos" type="AdditionalDataInfo" arr1="Num Block Infos">Number of additional data blocks</add>
	 <add name="Num Blocks" type="int">Number of additional data blocks</add>
	 <add name="Blocks" type="AdditionalDataBlock" arr1="Num Blocks">Number of additional data blocks</add>
	 </niobject>
	 */

	public short numVertices;

	public int numBlockInfos;

	public NifAdditionalDataInfo[] blockInfos;

	public int numBlocks;

	public NifAdditionalDataBlock[] blocks;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numVertices = ByteConvert.readShort(stream);

		numBlockInfos = ByteConvert.readInt(stream);

		blockInfos = new NifAdditionalDataInfo[numBlockInfos];
		for (int i = 0; i < numBlockInfos; i++)
		{
			blockInfos[i] = new NifAdditionalDataInfo(stream);
		}
		numBlocks = ByteConvert.readInt(stream);
		blocks = new NifAdditionalDataBlock[numBlocks];
		for (int i = 0; i < numBlocks; i++)
		{
			blocks[i] = new NifAdditionalDataBlock(stream, nifVer);
		}

		return success;
	}
}