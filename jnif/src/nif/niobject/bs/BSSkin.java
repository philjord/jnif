package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.BSSkinBoneTrans;
import nif.compound.NifVector3;
import nif.niobject.NiObject;

public class BSSkin
{
	/*
	 * <niobject name="BSSkin::Instance" inherit="NiObject">
	    Fallout 4 Skin Instance
	    <add name="Target" type="Ptr" />
	    <add name="Bone Data" type="Ref" template="BSSkin::BoneData" />
	    <add name="Num Bones" type="uint" />
	    <add name="Bones" type="Ptr" arr1="Num Bones" />
	    <add name="Num Unknown" type="uint" />
	    <add name="Unknown" type="Vector3" arr1="Num Unknown" />
	</niobject>
	
	<niobject name="BSSkin::BoneData" inherit="NiObject">
	    Fallout 4 Bone Data
	    <add name="Num Bones" type="uint" />
	    <add name="Bones" type="BSSkinBoneTrans" arr1="Num Bones" />
	</niobject>
	
	 */
	public static class Instance extends NiObject
	{
		public NifPtr Target;
		public NifRef BoneData;
		public int NumBones;
		public NifPtr[] Bones;
		public int NumUnknown;
		public NifVector3[] Unkown;

		public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);
			Target = new NifPtr(NiObject.class, stream);
			BoneData = new NifRef(BSSkin.BoneData.class, stream);
			NumBones = ByteConvert.readInt(stream);
			Bones = new NifPtr[NumBones];
			for (int i = 0; i < NumBones; i++)
			{
				Bones[i] = new NifPtr(NiObject.class, stream);
			}
			NumUnknown = ByteConvert.readInt(stream);
			Unkown = new NifVector3[NumUnknown];
			for (int i = 0; i < NumUnknown; i++)
			{
				Unkown[i] = new NifVector3(stream);
			}

			return success;
		}
	}

	public static class BoneData extends NiObject
	{
		public int NumBones;
		public BSSkinBoneTrans[] Bones;

		public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);
			NumBones = ByteConvert.readInt(stream);
			Bones = new BSSkinBoneTrans[NumBones];
			for (int i = 0; i < NumBones; i++)
			{
				Bones[i] = new BSSkinBoneTrans(stream);
			}
			return success;
		}
	}
}
