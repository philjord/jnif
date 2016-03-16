package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;

public abstract class bhkConstraint extends bhkSerializable
{
	/**
	 
	 <niobject name="bhkConstraint" abstract="1" inherit="bhkSerializable">

	 Describes a physical constraint.
	 
	 <add name="Num Entities" type="uint">Number of bodies affected by this constraint.</add>
	 <add name="Entities" type="Ptr" template="bhkEntity" arr1="Num Entities">The entities affected by this constraint.</add>
	 <add name="Priority" type="uint" default="1">
	 Usually 1. Higher values indicate higher priority of this constraint?
	 </add>
	 </niobject> 	
	 
	 */

	public int numEntities;

	public NifPtr[] entities;

	public int priority;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numEntities = ByteConvert.readInt(stream);
		entities = new NifPtr[numEntities];
		for (int i = 0; i < numEntities; i++)
		{
			entities[i] = new NifPtr(bhkEntity.class, stream);
		}
		priority = ByteConvert.readInt(stream);

		return success;
	}
}
