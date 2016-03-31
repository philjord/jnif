package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.NifHingeDescriptor;
import nif.compound.NifLimitedHingeDescriptor;
import nif.compound.NifRagdollDescriptor;
import nif.niobject.NiObject;

public class bhkMalleableConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkMalleableConstraint" abstract="0" inherit="bhkConstraint">
	
	 A malleable constraint.
	 
	 <add name="Type" type="uint">Type of constraint.</add>
	 <add name="Unknown Int 2" type="uint">Unknown.</add>
	 <add name="Unknown Link 1" type="Ref" template="NiObject">Usually -1?</add>
	 <add name="Unknown Link 2" type="Ref" template="NiObject">Usually -1?</add>
	 <add name="Unknown Int 3" type="uint">Unknown. 1?</add>
	 <add name="Hinge" type="HingeDescriptor" cond="Type == 1"/>
	 <add name="Ragdoll" type="RagdollDescriptor" cond="Type == 7"/>
	 <add name="Limited Hinge" type="LimitedHingeDescriptor" cond="Type == 2"/>
	 <add name="Tau" type="float"/>
	 <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7">Unknown</add>
	 <add name="Damping" type="float"/>
	 </niobject>
	 
	 */

	public int type;

	public int NumEntities;

	public NifPtr[] Entities;

	public int Priority;

	public NifHingeDescriptor hinge;

	public NifRagdollDescriptor ragdoll;

	public NifLimitedHingeDescriptor limitedHinge;

	public float tau;

	public float damping;

	public float strength;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		type = ByteConvert.readInt(stream);
		NumEntities = ByteConvert.readInt(stream);
		Entities = new NifPtr[NumEntities];
		for (int i = 0; i < NumEntities; i++)
			Entities[i] = new NifPtr(NiObject.class, stream);

		Priority = ByteConvert.readInt(stream);
		if (type == 0)
		{
			//hinge = new NifHingeDescriptor(stream, nifVer);
			new Throwable("Ball and socket descriptor to do!").printStackTrace();
		}
		else if (type == 1)
		{
			hinge = new NifHingeDescriptor(stream, nifVer);
		}
		else if (type == 2)
		{
			limitedHinge = new NifLimitedHingeDescriptor(stream, nifVer);
		}
		else if (type == 6)
		{
			//hinge = new NifHingeDescriptor(stream, nifVer);
			new Throwable("Prismatic descriptor to do!").printStackTrace();
		}
		else if (type == 7)
		{
			ragdoll = new NifRagdollDescriptor(stream, nifVer);
		}
		else if (type == 8)
		{
			//hinge = new NifHingeDescriptor(stream, nifVer);
			new Throwable("Stiff Spring descriptor to do!").printStackTrace();
		}
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			tau = ByteConvert.readFloat(stream);
			damping = ByteConvert.readFloat(stream);
		}
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			strength = ByteConvert.readFloat(stream);
		}

		return success;
	}
}
