package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifLimitedHingeDescriptor;

public class bhkLimitedHingeConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkLimitedHingeConstraint" abstract="0" inherit="bhkConstraint" ver1="20.0.0.5">

	 Hinge constraint.
	 
	 <add name="Limited Hinge" type="LimitedHingeDescriptor">Describes a limited hinge constraint</add>
	 
	 </niobject>
	 */

	public NifLimitedHingeDescriptor limitedHinge;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		limitedHinge = new NifLimitedHingeDescriptor(stream, nifVer);

		return success;
	}
}
