package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import tools.io.LittleEndianPrimitiveBytes;

public class NiBezierMesh extends NiAVObject
{
	/**
	 
	 <niobject name="NiBezierMesh" abstract="0" inherit="NiAVObject">

	 Unknown
	 
	 <add name="Num Bezier Triangles" type="uint">references.</add>
	 <add name="Bezier Triangle" type="Ref" template="NiBezierTriangle4" arr1="Num Bezier Triangles">unknown</add>
	 <add name="Unknown 1" type="uint">Unknown.</add>
	 <add name="Count 1" type="ushort">Data count.</add>
	 <add name="Unknown 2" type="ushort">Unknown.</add>
	 <add name="Points 1" type="Vector3" arr1="Count 1">data.</add>
	 <add name="Unknown 3" type="uint">Unknown (illegal link?).</add>
	 <add name="Points 2" type="float" arr1="Count 1" arr2="2">data.</add>
	 <add name="Unknown 4" type="uint">unknown</add>
	 <add name="Count 2" type="ushort">data count 2.</add>
	 <add name="Data 2" type="ushort" arr1="Count 2" arr2="4">data count.</add>
	 </niobject>
	 
	 */

	public int numBezierTriangles;

	public NifRef[] bezierTriangle;

	public int unknown1;

	public short count1;

	public short unknown2;

	public NifVector3[] points1;

	public int unknown3;

	public float[][] points2;

	public int unknown4;

	public short count2;

	public short[][] data2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numBezierTriangles = LittleEndianPrimitiveBytes.readInt(stream);

		bezierTriangle = new NifRef[numBezierTriangles];
		for (int i = 0; i < numBezierTriangles; i++)
		{
			bezierTriangle[i] = new NifRef(NiBezierTriangle4.class, stream);
		}

		unknown1 = LittleEndianPrimitiveBytes.readInt(stream);

		count1 = LittleEndianPrimitiveBytes.readShort(stream);

		unknown2 = LittleEndianPrimitiveBytes.readShort(stream);

		points1 = new NifVector3[count1];
		for (int i = 0; i < count1; i++)
		{
			points1[i] = new NifVector3(stream);
		}

		unknown3 = LittleEndianPrimitiveBytes.readInt(stream);

		points2 = new float[count1][2];
		for (int i = 0; i < count1; i++)
		{
			points2[i] = LittleEndianPrimitiveBytes.readFloats(2, stream);
		}

		unknown4 = LittleEndianPrimitiveBytes.readInt(stream);

		count2 = LittleEndianPrimitiveBytes.readShort(stream);

		data2 = new short[count2][4];
		for (int i = 0; i < count2; i++)
		{
			data2[i] = LittleEndianPrimitiveBytes.readShorts(4, stream);
		}

		return success;
	}
}