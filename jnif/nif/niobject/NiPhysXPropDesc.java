package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifphysXMaterialRef;

public class NiPhysXPropDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXPropDesc" abstract="0" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Num Dests" type="int">Number of NiPhysXActorDesc references</add>
	 <add name="Actor Descs" type="Ref" template="NiPhysXActorDesc" arr1="Num Dests">Unknown</add>
	 <add name="Num Joints" type="uint">Unknown</add>
	 <add name="Joint Descs" type="Ref" template="NiPhysXD6JointDesc" arr1="Num Joints">PhysX Joint Descriptions</add>
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Num Materials" type="uint">Unknown</add>
	 <add name="Material Descs" type="physXMaterialRef" arr1="Num Materials">PhysX Material Descriptions</add>
	 <add name="Unknown Int 2" type="uint">Unknown</add>
	 </niobject>
	 */

	public int numDests;

	public NifRef[] actorDescs;

	public int numJoints;

	public NifRef[] jointDescs;

	public int unknownInt1;

	public int numMaterials;

	public NifRef[] materialsDescs;

	public int unknownInt2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numDests = ByteConvert.readInt(stream);
		actorDescs = new NifRef[numDests];
		for (int i = 0; i < numDests; i++)
		{
			actorDescs[i] = new NifRef(NiPhysXActorDesc.class, stream);
		}

		numJoints = ByteConvert.readInt(stream);
		jointDescs = new NifRef[numJoints];
		for (int i = 0; i < numJoints; i++)
		{
			jointDescs[i] = new NifRef(NiPhysXD6JointDesc.class, stream);
		}

		unknownInt1 = ByteConvert.readInt(stream);

		numMaterials = ByteConvert.readInt(stream);
		materialsDescs = new NifRef[numMaterials];
		for (int i = 0; i < numMaterials; i++)
		{
			materialsDescs[i] = new NifRef(NifphysXMaterialRef.class, stream);
		}

		unknownInt2 = ByteConvert.readInt(stream);
		return success;
	}
}