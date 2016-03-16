package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector3;

public class bhkBreakableConstraint extends bhkConstraint
{
	/**
	     <niobject name="bhkBreakableConstraint" abstract="0" inherit="bhkConstraint">
	    Bethesda-Specific node.
	    <add name="Unknown Ints 1" type="int" arr1="41" vercond="User Version &lt;= 11"> </add>
	    <add name="Unknown Short 1" type="short" vercond="User Version &lt;= 11">Unknown</add>
	    
		<add name="Unknown Int 1" type="uint" vercond="User Version == 12">A count or flag?</add>
		<add name="Num Entities 2" type="uint" vercond="User Version == 12">Number of bodies affected by this constraint.</add>
	    <add name="Entities 2" type="Ptr" template="bhkEntity" arr1="Num Entities 2" vercond="User Version == 12">The entities affected by this constraint.</add>
	    <add name="Priority 2" type="uint" default="1" vercond="User Version == 12">Usually 1. Higher values indicate higher priority of this constraint?</add>
	    <add name="Unknown Int 2" type="uint" vercond="User Version == 12">Unknown</add>
	    <add name="Position" type="Vector3" vercond="User Version == 12">Unknown</add>
	    <add name="Rotation" type="Vector3" vercond="User Version == 12">Unknown</add>
	    <add name="Unknown Int 3" type="uint" vercond="User Version == 12">Unknown</add>
	    <add name="Threshold" type="float" vercond="User Version == 12">Unknown</add>
	    <add name="Unknown Float 1" type="float" cond="Unknown Int 1 >= 1" vercond="User Version == 12">Unknown</add>
	    <add name="Remove if Broken" type="byte" vercond="User Version == 12">Unknown</add>
	</niobject>
	 */

	public int[] unknownInts1;

	public short unknownShort1;

	public int UnknownInt1;

	public int NumEntities2;

	public NifPtr[] Entities2;

	public int Priority2;

	public int UnknownInt2;

	public NifVector3 Position;

	public NifVector3 Rotation;

	public int UnknownInt3;

	public float Threshold;

	public float UnknownFloat1;

	public byte RemoveifBroken;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_USER_VER <= 11)
		{
			unknownInts1 = ByteConvert.readInts(41, stream);
			unknownShort1 = ByteConvert.readShort(stream);
		}
		else if (nifVer.LOAD_USER_VER > 11)
		{
			UnknownInt1 = ByteConvert.readInt(stream);

			NumEntities2 = ByteConvert.readInt(stream);

			Entities2 = new NifPtr[NumEntities2];
			for (int i = 0; i < NumEntities2; i++)
			{
				Entities2[i] = new NifPtr(bhkEntity.class, stream);
			}

			Priority2 = ByteConvert.readInt(stream);

			UnknownInt2 = ByteConvert.readInt(stream);

			Position = new NifVector3(stream);

			Rotation = new NifVector3(stream);

			UnknownInt3 = ByteConvert.readInt(stream);

			Threshold = ByteConvert.readFloat(stream);

			if (UnknownInt1 >= 1)
				UnknownFloat1 = ByteConvert.readFloat(stream);

			RemoveifBroken = ByteConvert.readByte(stream);
		}

		return success;
	}
}