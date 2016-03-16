package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifOldSkinData;
import nif.niobject.NiBone;

public class NiTriShapeSkinController extends NiTimeController
{
	/**
	 <niobject name="NiTriShapeSkinController" abstract="0" inherit="NiTimeController">

	 Old version of skinning instance.
	 
	 <add name="Num Bones" type="uint">The number of node bones referenced as influences.</add>
	 <add name="Vertex Counts" type="uint" arr1="Num Bones">The number of vertex weights stored for each bone.</add>
	 <add name="Bones" type="Ptr" template="NiBone" arr1="Num Bones">List of all armature bones.</add>
	 <add name="Bone Data" type="OldSkinData" arr1="Num Bones" arr2="Vertex Counts">
	 Contains skin weight data for each node that this skin is influenced by.
	 </add>
	 </niobject>
	 */

	public int numBones;

	public int[] vertexCounts;

	public NifPtr[] bones;

	public NifOldSkinData[][] boneData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numBones = ByteConvert.readInt(stream);

		vertexCounts = ByteConvert.readInts(numBones, stream);
		bones = new NifPtr[numBones];
		for (int i = 0; i < numBones; i++)
		{
			bones[i] = new NifPtr(NiBone.class, stream);
		}

		boneData = new NifOldSkinData[numBones][];
		for (int i = 0; i < numBones; i++)
		{
			boneData = new NifOldSkinData[i][vertexCounts[i]];
			for (int j = 0; j < vertexCounts[i]; j++)
			{
				boneData[i][j] = new NifOldSkinData(stream);
			}
		}

		return success;
	}
}