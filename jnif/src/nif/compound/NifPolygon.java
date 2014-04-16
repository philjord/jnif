package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifPolygon
{
	/**
	 <compound name="Polygon">

	 Two dimensional screen elements.
	 
	 <add name="Num Vertices" type="ushort">Number of vertices in this polygon</add>
	 <add name="Vertex Offset" type="ushort">Vertex Offset</add>
	 <add name="Num Triangles" type="ushort">Number of faces in this polygon</add>
	 <add name="Triangle Offset" type="ushort">Triangle offset in shape</add>
	 </compound>	
	 */

	public short numVertices;

	public short vertexOffset;

	public short numTriangles;

	public short triangleOffset;

	public NifPolygon(InputStream stream) throws IOException
	{
		numVertices = ByteConvert.readShort(stream);
		vertexOffset = ByteConvert.readShort(stream);
		numTriangles = ByteConvert.readShort(stream);
		triangleOffset = ByteConvert.readShort(stream);
	}
}
