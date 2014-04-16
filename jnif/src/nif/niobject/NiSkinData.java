package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMatrix33;
import nif.compound.NifSkinData;
import nif.compound.NifVector3;

public class NiSkinData extends NiObject
{
	/**
	 
	 <niobject name="NiSkinData" abstract="0" inherit="NiObject">

	 Skinning data.
	 
	 <add name="Rotation" type="Matrix33">

	 The overall rotation offset of the skin from this bone in the bind position.
	 (This is a guess, it has always been the identity matrix so far)
	 
	 </add>
	 <add name="Translation" type="Vector3">
	 The overall translation offset of the skin from this bone in the bind position. (This is a guess, it has always been (0.0, 0.0, 0.0) so far)
	 </add>
	 <add name="Scale" type="float">
	 The scale offset of the skin from this bone in the bind position. (This is an assumption - it has always been 1.0 so far)
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

	public NifMatrix33 rotation;

	public NifVector3 translation;

	public float scale;

	public int numBones;

	public boolean hasVertexWeights;

	public NifSkinData[] boneList;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		rotation = new NifMatrix33(stream);
		translation = new NifVector3(stream);
		scale = ByteConvert.readFloat(stream);
		numBones = ByteConvert.readInt(stream);
		hasVertexWeights = ByteConvert.readBool(stream, nifVer);
		boneList = new NifSkinData[numBones];
		for (int i = 0; i < numBones; i++)
		{
			boneList[i] = new NifSkinData(hasVertexWeights, stream);
		}

		return success;
	}
}