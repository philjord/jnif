package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifBodyPartList;
import nif.niobject.NiSkinInstance;

public class BSDismemberSkinInstance extends NiSkinInstance
{
	/**
	 
	 <niobject name="BSDismemberSkinInstance" abstract="0" inherit="NiSkinInstance" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node.
	 
	 <add name="Num Partitions" type="int">Unknown</add>
	 <add name="Partitions" type="BodyPartList" arr1="Num Partitions">Unknown</add>
	 </niobject>
	 */

	public int numPartitions;

	public NifBodyPartList[] partitions;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numPartitions = ByteConvert.readInt(stream);
		partitions = new NifBodyPartList[numPartitions];
		for (int i = 0; i < numPartitions; i++)
		{
			partitions[i] = new NifBodyPartList(stream);
		}

		return success;
	}
}