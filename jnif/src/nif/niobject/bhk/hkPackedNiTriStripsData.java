package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifOblivionSubShape;
import nif.compound.NifVector3;
import nif.compound.NifhkTriangle;

public class hkPackedNiTriStripsData extends bhkShapeCollection
{
	/**
	 
	 <niobject name="hkPackedNiTriStripsData" abstract="0" inherit="bhkShapeCollection" ver1="20.0.0.5">

	 NiTriStripsData for havok data?
	 
	 <add name="Num Triangles" type="uint">Number of triangles?</add>
	 <add name="Triangles" type="hkTriangle" arr1="Num Triangles">The physics triangles?</add>
	 <add name="Num Vertices" type="uint">Number of vertices.</add>
	 <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7">Unknown.</add>
	 <add name="Vertices" type="Vector3" arr1="Num Vertices">The vertices?</add>
	 <add name="Num Sub Shapes" type="ushort" ver1="20.2.0.7">Number of subparts.</add>
	 <add name="Sub Shapes" type="OblivionSubShape" arr1="Num Sub Shapes" ver1="20.2.0.7">The subparts.</add>
	 </niobject>
	 */

	public int numTriangles;

	public NifhkTriangle[] triangles;

	public int numVertices;

	public byte unknownByte1;

	public NifVector3[] vertices;

	public short numSubShapes;

	public NifOblivionSubShape[] subShapes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numTriangles = ByteConvert.readInt(stream);
		triangles = new NifhkTriangle[numTriangles];
		for (int i = 0; i < numTriangles; i++)
		{
			triangles[i] = new NifhkTriangle(stream, nifVer);

		}
		numVertices = ByteConvert.readInt(stream);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownByte1 = ByteConvert.readByte(stream);
		}
		vertices = new NifVector3[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vertices[i] = new NifVector3(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			numSubShapes = ByteConvert.readShort(stream);

			subShapes = new NifOblivionSubShape[numSubShapes];
			for (int i = 0; i < numSubShapes; i++)
			{
				subShapes[i] = new NifOblivionSubShape(stream);
			}
		}
		return success;
	}
}