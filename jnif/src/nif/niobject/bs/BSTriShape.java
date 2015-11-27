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
	public static boolean LOAD_OPTIMIZED = true;
	public static final float ES_TO_METERS_SCALE = 0.02f;

	public int vertexType;
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
	public float[] colorsOpt;
	public float[] uVSetOpt;

	//OPTIMISATION
	//public NifTriangle[] triangles;
	public int[] trianglesOpt;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		vertexType = ByteConvert.readUnsignedByte(stream);
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
				
				if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 3)
				{
					uVSetOpt = new float[numVertices * 2];
				}
				
				if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 4)
				{
					normalsOpt = new float[numVertices * 3];
				}
				
				if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0)
						|| ((vertexFormatFlags7 & 0x01) == 0 && vertexType >= 6))
				{
					colorsOpt = new float[numVertices * 4];
				}
				

				for (int i = 0; i < numVertices; i++)
				{
					if ((vertexFormatFlags7 & 0x40) != 0)
					{
						verticesOpt[i * 3 + 0] = ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 2] = -ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 1] = ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE;

						ByteConvert.readInt(stream);
					}
					else if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 2)
					{
						verticesOpt[i * 3 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 2] = -MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;

						ByteConvert.readUnsignedShort(stream);
					}

					if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 3)
					{
						uVSetOpt[i * 2 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						uVSetOpt[i * 2 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
					}

					if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 4)
					{
						normalsOpt[i * 3 + 0] = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
						normalsOpt[i * 3 + 2] = -(ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
						normalsOpt[i * 3 + 1] = (ByteConvert.readByte(stream) / 255.0f) * 2.0f - 1.0f;
						ByteConvert.readByte(stream);
					}

					if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 5)
					{
						// tangent
						ByteConvert.readBytes(4, stream);
					}

					if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0)
							|| ((vertexFormatFlags7 & 0x01) == 0 && vertexType >= 6))
					{
						colorsOpt[i * 4 + 0] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 1] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 2] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 3] = ByteConvert.readUnsignedByte(stream) / 255f;
					}

					if ((vertexFormatFlags7 & 0x01) != 0)
					{
						if ((vertexFormatFlags7 & 0x04) != 0)
						{
							ByteConvert.readInt(stream);
							ByteConvert.readInt(stream);
							ByteConvert.readInt(stream);
						}

						if ((vertexFormatFlags7 & 0x10) != 0)
						{
							ByteConvert.readInt(stream);
						}
					}
					else
					{
						//TODO: 9 could in fact be grabbing bone data (and 10)
						if (vertexType >= 7)
						{
							ByteConvert.readInt(stream);
						}
						if (vertexType >= 8)
						{
							ByteConvert.readInt(stream);
						}
						if (vertexType >= 9)
						{
							ByteConvert.readInt(stream);
						}
						if (vertexType >= 10)
						{
							ByteConvert.readInt(stream);
						}
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
					vertexData[v] = new BSVertexData(vertexFormatFlags7, vertexType, stream);
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
