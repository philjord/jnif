package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;

public class NiSkinInstance extends NiObject
{
	/**
	 
	 <niobject name="NiSkinInstance" abstract="0" inherit="NiObject">

	 Skinning instance.
	 
	 <add name="Data" type="Ref" template="NiSkinData">Skinning data reference.</add>
	 <add name="Skin Partition" type="Ref" template="NiSkinPartition" ver1="10.2.0.0">
	 Refers to a NiSkinPartition objects, which partitions the mesh such that every vertex is only influenced by a limited number of bones.
	 </add>
	 <add name="Skeleton Root" type="Ptr" template="NiNode">Armature root node.</add>
	 <add name="Num Bones" type="uint">The number of node bones referenced as influences.</add>
	 <add name="Bones" type="Ptr" template="NiNode" arr1="Num Bones">List of all armature bones.</add>
	 </niobject>
	 
	 */

	public NifRef data;

	public NifRef skinPartition;

	public NifPtr skeletonRoot;

	public int numBones;

	public NifPtr[] bones;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifRef(NiSkinData.class, stream);
		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0)
		{
			skinPartition = new NifRef(NiSkinPartition.class, stream);
		}

		skeletonRoot = new NifPtr(NiAVObject.class, stream);
		numBones = ByteConvert.readInt(stream);
		bones = new NifPtr[numBones];
		for (int i = 0; i < numBones; i++)
		{
			bones[i] = new NifPtr(NiNode.class, stream);
		}
		return success;
	}
}