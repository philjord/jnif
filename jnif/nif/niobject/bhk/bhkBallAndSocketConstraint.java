package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class bhkBallAndSocketConstraint extends bhkConstraint
{
	/**
	 *     <niobject name="bhkBallAndSocketConstraint" abstract="0" inherit="bhkConstraint">
	        A Ball and Socket Constraint.
	        <add name="Unknown 4 bytes" type="byte" arr1="4">Unknown</add>
	        <add name="Unknown Floats 1" type="Vector3">Unknown</add>
	        <add name="Unknown Floats 2" type="Vector3">Unknown</add>
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	    </niobject>
	 */

	public byte[] unknownBytes;

	public NifVector3 unknownFloats1;

	public NifVector3 unknownFloats2;

	public int unknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownBytes = ByteConvert.readBytes(4, stream);
		unknownFloats1 = new NifVector3(stream);
		unknownFloats2 = new NifVector3(stream);
		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}
