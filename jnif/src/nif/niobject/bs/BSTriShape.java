package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifTriangle;
import nif.niobject.NiTriBasedGeom;
import nif.tools.MiniFloat;

public class BSTriShape extends NiTriBasedGeom
{
	public static boolean LOAD_OPTIMIZED = true;
	public static final float ES_TO_METERS_SCALE = 0.0254f / 2f;//0.02f;

	public int dwordsPerVertex;
	public int vertexFormat2;
	public int vertexFormatFlags3;
	public int vertexFormat4;
	public int vertexFormat5;
	//public int vertexFormatFlags6;
	//public int vertexFormatFlags7;
	public VertexFormat vertexFormat;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public int dataSize;

	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;

	//OPTIMISATION
	//public BSVertexData[] vertexData;
	public FloatBuffer verticesOptBuf;
	public FloatBuffer normalsOptBuf;
	public FloatBuffer tangentsOptBuf;
	public FloatBuffer binormalsOptBuf;
	public FloatBuffer colorsOptBuf;
	public FloatBuffer uVSetOptBuf;

	float[] BoneWeights;
	int[] BoneIndices;

	//OPTIMISATION
	//public NifTriangle[] triangles;
	public int[] trianglesOpt;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		dwordsPerVertex = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormat5 = ByteConvert.readUnsignedByte(stream);
		// haha an int of vertex format
		//vertexFormatFlags6 = ByteConvert.readUnsignedByte(stream);
		//vertexFormatFlags7 = ByteConvert.readUnsignedByte(stream);
		vertexFormat = new VertexFormat(stream);

		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numTriangles = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);

		if (dataSize > 0)
		{
			if (LOAD_OPTIMIZED)
			{
				verticesOptBuf = createFB(numVertices * 3);

				if (vertexFormat.isSet(VertexFormat.VF_UVs))
				{
					uVSetOptBuf = createFB(numVertices * 2);
				}

				if (vertexFormat.isSet(VertexFormat.VF_Normals))
				{
					normalsOptBuf = createFB(numVertices * 3);
					binormalsOptBuf = createFB(numVertices * 3);
					if (vertexFormat.isSet(VertexFormat.VF_Tangents))
					{
						tangentsOptBuf = createFB(numVertices * 3);
					}
				}

				if (vertexFormat.isSet(VertexFormat.VF_Vertex_Colors))
				{
					colorsOptBuf = createFB(numVertices * 4);
				}

				for (int i = 0; i < numVertices; i++)
				{
					if (vertexFormat.isSet(VertexFormat.VF_Vertex))
					{
						if (vertexFormat.isSet(VertexFormat.VF_Full_Precision))
						{
							verticesOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
							verticesOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
							verticesOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
							if (vertexFormat.isSet(VertexFormat.VF_Normals))
							{
								binormalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
							}
							else
							{
								ByteConvert.readFloat(stream);//discard
							}
						}
						else
						{
							verticesOptBuf.put(i * 3 + 0, MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
							verticesOptBuf.put(i * 3 + 2, -MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
							verticesOptBuf.put(i * 3 + 1, MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
							if (vertexFormat.isSet(VertexFormat.VF_Normals))
							{
								binormalsOptBuf.put(i * 3 + 0, MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)));
							}
							else
							{
								MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));//discard
							}
						}
					}

					if (vertexFormat.isSet(VertexFormat.VF_UVs))
					{
						uVSetOptBuf.put(i * 2 + 0, MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)));
						uVSetOptBuf.put(i * 2 + 1, MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)));
					}

					if (vertexFormat.isSet(VertexFormat.VF_Normals))
					{
						normalsOptBuf.put(i * 3 + 0, (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
						normalsOptBuf.put(i * 3 + 2, -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f));
						normalsOptBuf.put(i * 3 + 1, (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);

						binormalsOptBuf.put(i * 3 + 2, -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f));
						if (vertexFormat.isSet(VertexFormat.VF_Tangents))
						{
							tangentsOptBuf.put(i * 3 + 0, (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
							tangentsOptBuf.put(i * 3 + 2, -((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f));
							tangentsOptBuf.put(i * 3 + 1, (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);

							binormalsOptBuf.put(i * 3 + 1, (ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
						}
					}

					if (vertexFormat.isSet(VertexFormat.VF_Vertex_Colors))
					{
						colorsOptBuf.put(i * 4 + 0, ByteConvert.readUnsignedByte(stream) / 255f);
						colorsOptBuf.put(i * 4 + 1, ByteConvert.readUnsignedByte(stream) / 255f);
						colorsOptBuf.put(i * 4 + 2, ByteConvert.readUnsignedByte(stream) / 255f);
						colorsOptBuf.put(i * 4 + 3, ByteConvert.readUnsignedByte(stream) / 255f);
					}

					if (vertexFormat.isSet(VertexFormat.VF_Skinned))
					{
						BoneWeights = new float[4];
						for (int b = 0; b < 4; b++)
							BoneWeights[b] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
						BoneIndices = new int[4];
						for (int b = 0; b < 4; b++)
							BoneIndices[b] = ByteConvert.readUnsignedByte(stream);
					}

					if (vertexFormat.isSet(VertexFormat.VF_Male_Eyes))
					{
						//<add name="Unknown Int 2" type="uint" cond="(ARG &amp; 4096) != 0" />
						ByteConvert.readInt(stream);
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
				vertexData = new BSVertexData[numVertices];
				for (int v = 0; v < numVertices; v++)
				{
					vertexData[v] = new BSVertexData(vertexFormat, stream);
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

	protected static FloatBuffer createFB(int l)
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(l * 4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();

	}

	public static class VertexFormat
	{
		public int vertexFlags;

		public VertexFormat(ByteBuffer stream) throws IOException
		{
			vertexFlags = ByteConvert.readUnsignedShort(stream);
		}

		public boolean isSet(int flagBit)
		{
			return ((vertexFlags >> flagBit) & 0x01) != 0;
		}

		//bits
		public static int VF_Unknown_1 = 0;
		public static int VF_Unknown_2 = 1;
		public static int VF_Unknown_3 = 2;
		public static int VF_Unknown_4 = 3;
		public static int VF_Vertex = 4;
		public static int VF_UVs = 5;
		public static int VF_Unknown_5 = 6;
		public static int VF_Normals = 7;
		public static int VF_Tangents = 8;
		public static int VF_Vertex_Colors = 9;
		public static int VF_Skinned = 10;
		public static int VF_Unknown_6 = 11;
		public static int VF_Male_Eyes = 12;
		public static int VF_Unknown_7 = 13;
		public static int VF_Full_Precision = 14;
		public static int VF_Unknown_8 = 15;
	}

}
