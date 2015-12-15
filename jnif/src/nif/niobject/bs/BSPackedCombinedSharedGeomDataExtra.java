package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifTriangle;

public class BSPackedCombinedSharedGeomDataExtra extends BSExtraData
{

	public int dwordsPerVertex;
	public int vertexFormat2;
	public int vertexFormatFlags3;
	public int vertexFormat4;
	public int vertexFormat5;
	public int vertexFormatFlags6;
	public int vertexFormatFlags7;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		dwordsPerVertex = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormat5 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags6 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags7 = ByteConvert.readUnsignedByte(stream);
		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numVertices = ByteConvert.readInt(stream);
		numTriangles = ByteConvert.readUnsignedShort(stream);
		
		System.out.println("numVertices " +numVertices + " numTriangles " +numTriangles + " dwordsPerVertex "+ dwordsPerVertex);

		//format bpv 8 f2:4 f3:101 f4:7 f5:0 f6:176 f7:67 f8:0 with 8 verts = 14 bytes each
		if (dwordsPerVertex == 8)
		{
			vertexData = new BSVertexData[numVertices];
			for (int v = 0; v < numVertices; v++)
			{
				//vertexData[v] = new BSVertexData(vertexFormatFlags7, vertexFormatFlags6, vertexFormatFlags3, dwordsPerVertex, stream);
				//System.out.println("" + v + " " + vertexData[v]);
				ByteConvert.readBytes(14, stream);
			}

			triangles = new NifTriangle[numTriangles];
			for (int t = 0; t < numTriangles; t++)
			{
				triangles[t] = new NifTriangle(stream);
				//System.out.println("" + t + " " + triangles[t]);
			}
		}
		else
		{
			vertexData = new BSVertexData[numVertices];
			for (int v = 0; v < numVertices; v++)
			{
				//vertexData[v] = new BSVertexData(vertexFormatFlags7, vertexFormatFlags6, vertexFormatFlags3, dwordsPerVertex, stream);
				//System.out.println("" + v + " " + vertexData[v]);
				ByteConvert.readBytes(7, stream);
			}

			triangles = new NifTriangle[numTriangles];
			for (int t = 0; t < numTriangles; t++)
			{
				//triangles[t] = new NifTriangle(stream);
				//System.out.println("" + t + " " + triangles[t]);
				ByteConvert.readBytes(3, stream);
			}
		}

		return success;
	}
}
