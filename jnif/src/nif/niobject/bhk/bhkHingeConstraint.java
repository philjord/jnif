package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifHingeDescriptor;

public class bhkHingeConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkHingeConstraint" abstract="0" inherit="bhkConstraint" ver1="20.0.0.5">

	 A hinge constraint.
	 
	 <add name="Hinge" type="HingeDescriptor">Hinge constraing.</add>
	 <add name="Unknown Float 1" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7">Unknown</add>
	 </niobject>
	 
	 */

	public NifHingeDescriptor hinge;

	public float unknownFloat1;

	public byte unknownByte1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		hinge = new NifHingeDescriptor(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownFloat1 = ByteConvert.readFloat(stream);
			unknownByte1 = ByteConvert.readByte(stream);
		}
		return success;
	}
}
