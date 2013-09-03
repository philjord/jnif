package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
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

	public int unknownInt2;

	public NifRef unknownLink1;

	public NifRef unknownLink2;

	public int unknownInt3;

	public NifHingeDescriptor hinge;

	public NifRagdollDescriptor ragdoll;

	public NifLimitedHingeDescriptor limitedHinge;

	public float tau;

	public byte unknownByte1;

	public float damping;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		type = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownLink1 = new NifRef(NiObject.class, stream);
		unknownLink2 = new NifRef(NiObject.class, stream);
		unknownInt3 = ByteConvert.readInt(stream);
		if (type == 1)
		{
			hinge = new NifHingeDescriptor(stream, nifVer);
		}
		else if (type == 7)
		{
			ragdoll = new NifRagdollDescriptor(stream, nifVer);
		}
		else if (type == 2)
		{
			limitedHinge = new NifLimitedHingeDescriptor(stream, nifVer);
		}
		tau = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownByte1 = ByteConvert.readByte(stream);
		}
		damping = ByteConvert.readFloat(stream);
		return success;
	}
}
