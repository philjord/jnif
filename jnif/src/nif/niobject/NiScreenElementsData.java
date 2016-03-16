package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifPolygon;

public class NiScreenElementsData extends NiTriShapeData
{
	/**
	 
	 <niobject name="NiScreenElementsData" inherit="NiTriShapeData">

	 Two dimensional screen elements.
	 
	 <add name="Max Polygons" type="ushort">Maximum number of polygons?</add>
	 <add name="Polygons" type="Polygon" arr1="Max Polygons">Polygons</add>
	 <add name="Polygon Indices" type="ushort" arr1="Max Polygons">Polygon Indices</add>
	 <add name="Unknown UShort 1" type="ushort" default="1">Unknown</add>
	 <add name="Num Polygons" type="ushort">Number of Polygons actually in use</add>
	 <add name="Used Vertices" type="ushort">Number of in-use vertices</add>
	 <add name="Unknown UShort 2" type="ushort" default="1">Unknown</add>
	 <add name="Used Triangle Points" type="ushort">Number of in-use triangles</add>
	 <add name="Unknown UShort 3" type="ushort" default="1">Maximum number of faces</add>
	 </niobject>
	 
	 */

	public short maxPolygons;

	public NifPolygon[] polygons;

	public short[] polygonIndices;

	public short unknownUShort1;

	public short numPolygons;

	public short usedVertices;

	public short unknownUShort2;

	public short usedTrianglePoints;

	public short unknownUShort3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		maxPolygons = ByteConvert.readShort(stream);
		polygons = new NifPolygon[maxPolygons];
		for (int i = 0; i < maxPolygons; i++)
		{
			polygons[i] = new NifPolygon(stream);
		}
		polygonIndices = ByteConvert.readShorts(maxPolygons, stream);

		unknownUShort1 = ByteConvert.readShort(stream);
		numPolygons = ByteConvert.readShort(stream);
		usedVertices = ByteConvert.readShort(stream);
		unknownUShort2 = ByteConvert.readShort(stream);
		usedTrianglePoints = ByteConvert.readShort(stream);
		unknownUShort3 = ByteConvert.readShort(stream);

		return success;
	}
}