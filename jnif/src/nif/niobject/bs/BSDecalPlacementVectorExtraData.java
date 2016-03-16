package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifDecalVectorArray;
import nif.niobject.NiExtraData;

public class BSDecalPlacementVectorExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="BSDecalPlacementVectorExtraData" inherit="NiExtraData" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node. (for dynamic decal projection?)
	 
	 <add name="Unknown Float 1" type="float">Unknown</add>
	 <add name="Num Vector Blocks" type="short">Number of groups</add>
	 <add name="Vector Blocks" type="DecalVectorArray" arr1="Num Vector Blocks">Number of Blocks</add>
	 </niobject>
	 */

	public float unknownFloat1;

	public short numVectorBlocks;

	public NifDecalVectorArray[] vectorBlocks;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloat1 = ByteConvert.readFloat(stream);
		numVectorBlocks = ByteConvert.readShort(stream);
		vectorBlocks = new NifDecalVectorArray[numVectorBlocks];
		for (int i = 0; i < numVectorBlocks; i++)
		{
			vectorBlocks[i] = new NifDecalVectorArray(stream);
		}

		return success;
	}
}