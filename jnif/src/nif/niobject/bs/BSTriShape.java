package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.HashMap;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifTriangle;
import nif.niobject.NiTriBasedGeom;
import tools.BufferWrap;
import tools.MiniFloat;

public class BSTriShape extends NiTriBasedGeom
{
	public static boolean LOAD_OPTIMIZED = true;
	public static final float ES_TO_METERS_SCALE = 0.02f;

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
	public int dataSize;

	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;

	//OPTIMISATION
	//public BSVertexData[] vertexData;
	private float[] verticesOpt;
	private float[] normalsOpt;
	private float[] tangentsOpt;
	private float[] binormalsOpt;
	private float[] colorsOpt;
	private float[] uVSetOpt;
	public FloatBuffer verticesOptBuf;
	public FloatBuffer normalsOptBuf;
	public FloatBuffer tangentsOptBuf;
	public FloatBuffer binormalsOptBuf;
	public FloatBuffer colorsOptBuf;
	public FloatBuffer uVSetOptBuf;

	//OPTIMISATION
	//public NifTriangle[] triangles;
	public int[] trianglesOpt;

	public static HashMap<String, Integer> allFormatToCount = new HashMap<String, Integer>();

	//In the presence of 7&1==1
	public static int flags7ToSizeDisagreements = 0;
	public static HashMap<Integer, Integer> flags7ToSize = new HashMap<Integer, Integer>();

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
		numTriangles = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);

		String format = "dwpv " + dwordsPerVertex + " f2:" + vertexFormat2 + " f3:" + vertexFormatFlags3 + " f4:" + vertexFormat4 + " f5:"
				+ vertexFormat5 + " f6:" + vertexFormatFlags6 + " f7:" + vertexFormatFlags7 + " f8:" + vertexFormat8;
				//System.out.println("format " + format);

		/*	if( vertexFormatFlags7==0&& dwordsPerVertex>0)
			{
				System.out.println(""+nVer.fileName);
				System.out.println("format " + format);
			}*/

		if (allFormatToCount.get(format) == null)
			allFormatToCount.put(format, 1);
		else
			allFormatToCount.put(format, allFormatToCount.get(format) + 1);

		if (dataSize > 0)
		{
			////////////////////////decode code
			int bytesPerVert = ((dataSize - (numTriangles * 6)) / numVertices);

			if ((vertexFormatFlags7 & 0x01) != 0)
			{
				if (flags7ToSize.get(vertexFormatFlags7) != null && flags7ToSize.get(vertexFormatFlags7) != bytesPerVert)
					flags7ToSizeDisagreements++;
				flags7ToSize.put(vertexFormatFlags7, bytesPerVert);
			}
			///////////////////////

			if (LOAD_OPTIMIZED)
			{
				verticesOpt = new float[numVertices * 3];
				binormalsOpt = new float[numVertices * 3]; // not always filled use tangent!=null

				//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 3)
				if ((vertexFormatFlags6 & 0x20) != 0)
				{
					uVSetOpt = new float[numVertices * 2];
				}

				//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 4)
				if ((vertexFormatFlags6 & 0x80) != 0)
				{
					normalsOpt = new float[numVertices * 3];
				}

				//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 5)
				if ((vertexFormatFlags3 & 0x40) != 0)
				{
					tangentsOpt = new float[numVertices * 3];
				}

				//if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0)
				//		|| ((vertexFormatFlags7 & 0x01) == 0 && dwordsPerVertex >= 6))
				if ((vertexFormatFlags7 & 0x02) != 0)
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

						binormalsOpt[i * 3 + 0] = ByteConvert.readFloat(stream);
					}
					else //if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 2)
					{
						verticesOpt[i * 3 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 2] = -MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;
						verticesOpt[i * 3 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE;

						binormalsOpt[i * 3 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
					}

					//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 3)
					if ((vertexFormatFlags6 & 0x20) != 0)
					{
						uVSetOpt[i * 2 + 0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						uVSetOpt[i * 2 + 1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
					}

					//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 4)
					if ((vertexFormatFlags6 & 0x80) != 0)
					{
						normalsOpt[i * 3 + 0] = (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f;
						normalsOpt[i * 3 + 2] = -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
						normalsOpt[i * 3 + 1] = (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f;

						binormalsOpt[i * 3 + 2] = -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
					}

					//if ((vertexFormatFlags7 & 0x01) != 0 || dwordsPerVertex >= 5)
					if ((vertexFormatFlags3 & 0x40) != 0)
					{
						tangentsOpt[i * 3 + 0] = (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f;
						tangentsOpt[i * 3 + 2] = -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
						tangentsOpt[i * 3 + 1] = (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f;

						binormalsOpt[i * 3 + 1] = (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f;
					}

					//if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0)
					//		|| ((vertexFormatFlags7 & 0x01) == 0 && dwordsPerVertex >= 6))
					if ((vertexFormatFlags7 & 0x02) != 0)
					{
						colorsOpt[i * 4 + 0] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 1] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 2] = ByteConvert.readUnsignedByte(stream) / 255f;
						colorsOpt[i * 4 + 3] = ByteConvert.readUnsignedByte(stream) / 255f;
					}

					//if ((vertexFormatFlags7 & 0x01) != 0)
					{
						//TODO: load these
						if ((vertexFormatFlags7 & 0x04) != 0)
						{
							// 4 bone Weight half floats
							ByteConvert.readInt(stream);
							ByteConvert.readInt(stream);
							ByteConvert.readInt(stream);
						}

						if ((vertexFormatFlags7 & 0x10) != 0)
						{
							//4 bone indices bytes
							ByteConvert.readInt(stream);
						}
					}
					/*else
					{
						
						if (dwordsPerVertex >= 7)
						{
							ByteConvert.readInt(stream);
						}
						if (dwordsPerVertex >= 8)
						{
							ByteConvert.readInt(stream);
						}
						if (dwordsPerVertex >= 9)
						{
							ByteConvert.readInt(stream);
						}
						if (dwordsPerVertex >= 10)
						{
							ByteConvert.readInt(stream);
						}
					}*/

				}

				//////////////FLIP to buffers now and release float memory					 
				verticesOptBuf = BufferWrap.makeFloatBuffer(verticesOpt);
				verticesOpt = null;
				binormalsOptBuf = BufferWrap.makeFloatBuffer(binormalsOpt);
				binormalsOpt = null;
				if ((vertexFormatFlags6 & 0x20) != 0)
				{
					uVSetOptBuf = BufferWrap.makeFloatBuffer(uVSetOpt);
					uVSetOpt = null;
				}
				if ((vertexFormatFlags6 & 0x80) != 0)
				{
					normalsOptBuf = BufferWrap.makeFloatBuffer(normalsOpt);
					normalsOpt = null;
				}
				if ((vertexFormatFlags3 & 0x40) != 0)
				{
					tangentsOptBuf = BufferWrap.makeFloatBuffer(tangentsOpt);
					tangentsOpt = null;
				}
				if ((vertexFormatFlags7 & 0x02) != 0)
				{
					colorsOptBuf = BufferWrap.makeFloatBuffer(colorsOpt);
					colorsOpt = null;
				}
				///////////////////////////////////////////////////////////////////

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
				vertexData = new BSVertexData[numVertices];
				for (int v = 0; v < numVertices; v++)
				{
					vertexData[v] = new BSVertexData(vertexFormatFlags7, vertexFormatFlags6, vertexFormatFlags3, dwordsPerVertex, stream);
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
		else
		{
			//System.out.println("BSTrishape " + this.translation + " : " + this.radius);
			//System.out.println("format " + format);
		}

		return success;
	}

}
