package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMatrix33;
import nif.compound.NifVector3;

public class NiBezierTriangle4 extends NiObject
{
	/**
	 
	 <niobject name="NiBezierTriangle4" abstract="0" inherit="NiObject">

	 Sub data of NiBezierMesh
	 
	 <add name="Unknown 1" type="uint" arr1="6">unknown</add>
	 <add name="Unknown 2" type="ushort">unknown</add>
	 <add name="Matrix" type="Matrix33">unknown</add>
	 <add name="Vector 1" type="Vector3">unknown</add>
	 <add name="Vector 2" type="Vector3">unknown</add>
	 <add name="Unknown 3" type="short" arr1="4">unknown</add>
	 <add name="Unknown 4" type="byte">unknown</add>
	 <add name="Unknown 5" type="uint">unknown</add>
	 <add name="Unknown 6" type="short" arr1="24">unknown</add>
	 </niobject>
	 
	 */

	public int[] unknown1;

	public short unknown2;

	public NifMatrix33 matrix;

	public NifVector3 vector1;

	public NifVector3 vector2;

	public short[] unknown3;

	public byte unknown4;

	public int unknown5;

	public short[] unknown6;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknown1 = ByteConvert.readInts(6, stream);
		unknown2 = ByteConvert.readShort(stream);

		matrix = new NifMatrix33(stream);
		vector1 = new NifVector3(stream);
		vector2 = new NifVector3(stream);

		unknown3 = ByteConvert.readShorts(4, stream);
		unknown4 = ByteConvert.readByte(stream);
		unknown5 = ByteConvert.readInt(stream);
		unknown6 = ByteConvert.readShorts(24, stream);

		return success;
	}
}