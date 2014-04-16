package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifSkinPartition;

public class NiSkinPartition extends NiObject
{
	/**
	 
	 <niobject name="NiSkinPartition" abstract="0" inherit="NiObject" ver1="4.2.1.0">

	 Skinning data, optimized for hardware skinning. The mesh is partitioned in submeshes such that each vertex of a 
	 submesh is influenced only by a limited and fixed number of bones.
	 
	 <add name="Num Skin Partition Blocks" type="uint">Unknown.</add>
	 <add name="Skin Partition Blocks" type="SkinPartition" arr1="Num Skin Partition Blocks">Skin partition objects.</add>
	 </niobject>
	 
	 */

	public int numSkinPartitionBlocks;

	public NifSkinPartition[] skinPartitionBlocks;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numSkinPartitionBlocks = ByteConvert.readInt(stream);
		skinPartitionBlocks = new NifSkinPartition[numSkinPartitionBlocks];
		for (int i = 0; i < numSkinPartitionBlocks; i++)
		{
			skinPartitionBlocks[i] = new NifSkinPartition(stream, nifVer);
		}

		return success;
	}
}