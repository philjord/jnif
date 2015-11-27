package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifTriangle;
import nif.niobject.NiTriBasedGeom;
import tools.MiniFloat;

public class BSTriShape extends NiTriBasedGeom
{
	public static boolean LOAD_OPTIMIZED = false;
	public static final float ES_TO_METERS_SCALE = 0.02f;

	public int vertexFormatFlags1;
	public int vertexFormat2;
	public int vertexFormat3;
	public int vertexFormat4;
	public int vertexFormatFlags5;
	public int vertexFormat6;
	public int vertexFormatFlags7;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public int dataSize;

	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;

	//OPTIMISATION
	//public BSVertexData[] vertexData;
	public float[] verticesOpt;
	public float[] normalsOpt;
	public float[] vertexColorsOpt;
	public float[] uVSetOpt;

	//OPTIMISATION
	//public NifTriangle[] triangles;
	public int[] trianglesOpt;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		vertexFormatFlags1 = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormat3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags5 = ByteConvert.readUnsignedByte(stream);
		vertexFormat6 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags7 = ByteConvert.readUnsignedByte(stream);
		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numTriangles = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);

		if (dataSize > 0)
		{
			if (LOAD_OPTIMIZED)
			{
				verticesOpt = new float[numVertices * 3];
				if ((vertexFormatFlags7 & 0x4) != 0)
				{
					normalsOpt = new float[numVertices * 3];
				}
				if ((vertexFormatFlags7 & 0x1) != 0)
				{
					vertexColorsOpt = new float[numVertices * 4];
				}
				if (vertexFormatFlags1 > 2)
				{
					uVSetOpt = new float[numVertices * 2];
				}

				for (int i = 0; i < numVertices; i++)
				{
					verticesOpt[i * 3 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
					verticesOpt[i * 3 + 2] = -MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
					verticesOpt[i * 3 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;

					ByteConvert.readUnsignedShort(stream);

					if (vertexFormatFlags1 > 2)
					{
						uVSetOpt[i * 2 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						uVSetOpt[i * 2 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
					}

					if ((vertexFormatFlags7 & 0x1) != 0)
					{
						vertexColorsOpt[i * 4 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						vertexColorsOpt[i * 4 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						vertexColorsOpt[i * 4 + 2] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						vertexColorsOpt[i * 4 + 3] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
					}

					if ((vertexFormatFlags7 & 0x2) != 0)
					{
						ByteConvert.readBytes(4, stream);
					}

					if ((vertexFormatFlags7 & 0x4) != 0)
					{
						normalsOpt[i * 3 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						normalsOpt[i * 3 + 2] = -MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						normalsOpt[i * 3 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));

						ByteConvert.readBytes(6, stream);
					}

					if ((vertexFormatFlags7 & 0x40) != 0)
					{
						ByteConvert.readBytes(8, stream);
					}
				}

				trianglesOpt = new int[numTriangles * 3];
				for (int i = 0; i < numTriangles; i++)
				{
					trianglesOpt[i * 3 + 0] = ByteConvert.readUnsignedShort(stream);
					trianglesOpt[i * 3 + 1] = ByteConvert.readUnsignedShort(stream);
					trianglesOpt[i * 3 + 2] = ByteConvert.readUnsignedShort(stream);
				}

			}
			else
			{
				//int dataSizeWithoutTris = dataSize - (numTriangles * 6);
				//int dataPerVert = (dataSizeWithoutTris / numVertices);
				//System.out.println("vertexFormatFlags7 " + vertexFormatFlags7);
				//System.out.println("vertexFormatFlags5 " + vertexFormatFlags5);
				//System.out.println("vertexFormatFlags1 " + vertexFormatFlags1);
				//System.out.println("dataPerVert " + dataPerVert);			 

				vertexData = new BSVertexData[numVertices];
				for (int v = 0; v < numVertices; v++)
				{
					vertexData[v] = new BSVertexData(vertexFormatFlags7, vertexFormatFlags1, stream);
					//System.out.println("" + v + " " + vertexData[v]);
				}

				triangles = new NifTriangle[numTriangles];
				for (int t = 0; t < numTriangles; t++)
				{
					triangles[t] = new NifTriangle(stream);
					//System.out.println("" + t + " " + triangles[t]);
				}
			}
		}
		return success;
	}

}
