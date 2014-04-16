package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifLimitedHingeDescriptor;

public class bhkLimitedHingeConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkLimitedHingeConstraint" abstract="0" inherit="bhkConstraint" ver1="20.0.0.5">

	 Hinge constraint.
	 
	 <add name="Limited Hinge" type="LimitedHingeDescriptor">Describes a limited hinge constraint</add>
	 <add name="Unknown Float 1" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Is Death Pose" type="byte" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 1" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 2" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 3" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 4" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 5" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 6" type="int" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Byte 1" type="byte" cond="Is Death Pose" ver1="20.2.0.7">Unknown</add>
	 </niobject>
	 */

	public NifLimitedHingeDescriptor limitedHinge;

	public float unknownFloat1;

	public byte isDeathPose;

	public int unknownInt1;

	public int unknownInt2;

	public int unknownInt3;

	public int unknownInt4;

	public int unknownInt5;

	public int unknownInt6;

	public byte unknownByte1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		limitedHinge = new NifLimitedHingeDescriptor(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownFloat1 = ByteConvert.readFloat(stream);

			isDeathPose = ByteConvert.readByte(stream);
			if (isDeathPose != 0)
			{

				unknownInt1 = ByteConvert.readInt(stream);

				unknownInt2 = ByteConvert.readInt(stream);

				unknownInt3 = ByteConvert.readInt(stream);

				unknownInt4 = ByteConvert.readInt(stream);

				unknownInt5 = ByteConvert.readInt(stream);

				unknownInt6 = ByteConvert.readInt(stream);

				unknownByte1 = ByteConvert.readByte(stream);
			}
		}
		return success;
	}
}
