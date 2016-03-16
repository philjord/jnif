package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifRagdollDescriptor;

public class bhkRagdollConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkRagdollConstraint" abstract="0" inherit="bhkConstraint" ver1="20.0.0.5">
	
	 Ragdoll constraint.
	 
	 <add name="Ragdoll" type="RagdollDescriptor">Ragdoll constraint.</add>
	 
	 </niobject>
	 
	 */

	public NifRagdollDescriptor ragdoll;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		ragdoll = new NifRagdollDescriptor(stream, nifVer);
		return success;
	}
}
