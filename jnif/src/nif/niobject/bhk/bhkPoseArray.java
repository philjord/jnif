package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifBonePoseArray;
import nif.niobject.NiObject;

public class bhkPoseArray extends NiObject
{

	/*<niobject name="bhkPoseArray" inherit="NiObject">
	Found in Fallout 3, extra ragdoll info for NPCs/creatures. (usually idleanims\deathposes.psa)
	<add name="Num Bones" type="int">Number of target bones</add>
	<add name="Bones" type="string" arr1="Num Bones">Bones in index</add>
	<add name="Num Poses" type="int">Unknown</add>
	<add name="Pose Array" type="BonePoseArray" arr1="Num Poses">Unknown</add>
	</niobject>
	*/

	public int numBones;

	public String[] bones;

	public int numPoses;

	public NifBonePoseArray[] poses;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numBones = ByteConvert.readInt(stream);
		bones = new String[numBones];
		for (int i = 0; i < numBones; i++)
		{
			bones[i] = ByteConvert.readIndexString(stream, nifVer);
		}

		numPoses = ByteConvert.readInt(stream);
		poses = new NifBonePoseArray[numPoses];
		for (int i = 0; i < numPoses; i++)
		{
			poses[i] = new NifBonePoseArray(stream);
		}
		return success;
	}
}