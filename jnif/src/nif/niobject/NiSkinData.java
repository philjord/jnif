package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifSkinData;
import nif.compound.NifSkinTransform;

public class NiSkinData extends NiObject
{
	/**
	 
	 <niobject name="NiSkinData" abstract="0" inherit="NiObject">

	 Skinning data.
	 
	 
	 <add name="SkinTransform" type="SkinTransform">	
	 </add>
	 <add name="Num Bones" type="uint">Number of bones.</add>
	 <add name="Skin Partition" type="Ref" template="NiSkinPartition" ver1="4.0.0.2" ver2="10.1.0.0">
	 This optionally links a NiSkinPartition for hardware-acceleration information.
	 </add>
	 <add name="Has Vertex Weights" type="byte" ver1="4.2.1.0" default="1">Enables Vertex Weights for this NiSkinData.</add>
	 <add name="Bone List" type="SkinData" arr1="Num Bones" arg="Has Vertex Weights">
	 Contains offset data for each node that this skin is influenced by.
	 </add>
	 </niobject>
	 
	 */

	public NifSkinTransform nifSkinTransform;

	public int numBones;

	public NifRef skinPartition;

	public boolean hasVertexWeights;

	public NifSkinData[] boneList;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		nifSkinTransform = new NifSkinTransform(stream);
		numBones = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			skinPartition = new NifRef(NiSkinPartition.class, stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_4_2_1_0)
		{
			hasVertexWeights = ByteConvert.readByte(stream) != 0;
		}
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_1_0)
		{
			hasVertexWeights = true;// yes the version numbers above are the same 4210 read it and ignored it
		}
		
		boneList = new NifSkinData[numBones];
		for (int i = 0; i < numBones; i++)
		{
			boneList[i] = new NifSkinData(hasVertexWeights, stream);
		}

		return success;
	}
}