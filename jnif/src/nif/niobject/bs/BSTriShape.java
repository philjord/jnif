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
	public static boolean LOAD_MEGA_OPTIMIZED = false;
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

	public float[] BoneWeights;
	public int[] BoneIndices;

	//OPTIMISATION
	//public NifTriangle[] triangles;
	public int[] trianglesOpt;

	//MEGA_OPTIMIZISATION
	public int interleavedStride = -1;
	public int geoToCoordOffset = -1;
	public int geoToColorsOffset = -1;
	public int geoToNormalsOffset = -1;
	public int geoToTanOffset = -1;
	public int geoToBiTanOffset = -1;
	public int geoToTexCoordOffset = -1;

	public ByteBuffer interleavedBuffer;
	public ByteBuffer coordBuffer;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		dwordsPerVertex = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormat5 = ByteConvert.readUnsignedByte(stream);
		vertexFormat = new VertexFormat(stream);

		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numTriangles = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);

		if (dataSize > 0)
		{
			if (LOAD_OPTIMIZED)
			{
				if (LOAD_MEGA_OPTIMIZED && !vertexFormat.isSet(VertexFormat.VF_Skinned) && !vertexFormat.isSet(VertexFormat.VF_Male_Eyes))
				{
					int offset = 0;
					if (vertexFormat.isSet(VertexFormat.VF_Vertex))
					{
						geoToCoordOffset = 0;

						// TODO: pipeline can't do full yet
						// though I suspect a separate coords system to handle physics and morph would be good
						//if (vertexFormat.isSet(VertexFormat.VF_Full_Precision))
						//{
						//	offset += 3 * 4;
						//}
						//else
						{
							offset += 8;// 3 half float = 6bytes aligned on 4
						}

					}
					if (vertexFormat.isSet(VertexFormat.VF_UVs))
					{
						geoToTexCoordOffset = offset;
						offset += 4;// minimum alignment (2 shorts)
					}

					if (vertexFormat.isSet(VertexFormat.VF_Normals))
					{
						geoToNormalsOffset = offset;
						offset += 4;// minimum alignment (3 bytes)

						if (vertexFormat.isSet(VertexFormat.VF_Tangents))
						{
							geoToTanOffset = offset;
							offset += 4;// minimum alignment (3 bytes)

							geoToBiTanOffset = offset;
							offset += 4;// minimum alignment (3 bytes)
						}
					}
					if (vertexFormat.isSet(VertexFormat.VF_Vertex_Colors))
					{
						geoToColorsOffset = offset;
						offset += 4;// minimum alignment (4 bytes)
					}

					interleavedStride = offset;

					interleavedBuffer = ByteBuffer.allocateDirect(numVertices * interleavedStride);
					interleavedBuffer.order(ByteOrder.nativeOrder());

					for (int i = 0; i < numVertices; i++)
					{
						if (vertexFormat.isSet(VertexFormat.VF_Vertex))
						{
							interleavedBuffer.position(i * interleavedStride);
							if (vertexFormat.isSet(VertexFormat.VF_Full_Precision))
							{
								short x = (short) MiniFloat.fromFloat(ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
								short z = (short) -MiniFloat.fromFloat(ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
								short y = (short) MiniFloat.fromFloat(ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);

								interleavedBuffer.putShort(x);
								interleavedBuffer.putShort(y);
								interleavedBuffer.putShort(z);

								if (vertexFormat.isSet(VertexFormat.VF_Normals) && vertexFormat.isSet(VertexFormat.VF_Tangents))
								{
									interleavedBuffer.position(i * interleavedStride + geoToBiTanOffset + 0);
									interleavedBuffer.put((byte) (ByteConvert.readFloat(stream) * 255));
								}
								else
								{
									ByteConvert.readFloat(stream);//discard
								}
							}
							else
							{
								short x = (short) MiniFloat
										.fromFloat(MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
								short z = (short) MiniFloat
										.fromFloat(-MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
								short y = (short) MiniFloat
										.fromFloat(MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);

								interleavedBuffer.putShort(x);
								interleavedBuffer.putShort(y);
								interleavedBuffer.putShort(z);
								if (vertexFormat.isSet(VertexFormat.VF_Normals) && vertexFormat.isSet(VertexFormat.VF_Tangents))
								{
									interleavedBuffer.position(i * interleavedStride + geoToBiTanOffset + 0);
									interleavedBuffer.put((byte) (MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * 255));
								}
								else
								{
									ByteConvert.readUnsignedShort(stream);//discard
								}
							}
						}

						if (vertexFormat.isSet(VertexFormat.VF_UVs))
						{
							interleavedBuffer.position(i * interleavedStride + geoToTexCoordOffset);
							interleavedBuffer.putShort((short) ByteConvert.readUnsignedShort(stream));
							interleavedBuffer.putShort((short) ByteConvert.readUnsignedShort(stream));
						}

						if (vertexFormat.isSet(VertexFormat.VF_Normals))
						{
							interleavedBuffer.position(i * interleavedStride + geoToNormalsOffset);
							byte nx = ByteConvert.readByte(stream);
							byte nz = (byte) -ByteConvert.readByte(stream);
							byte ny = ByteConvert.readByte(stream);

							interleavedBuffer.put(nx);
							interleavedBuffer.put(ny);
							interleavedBuffer.put(nz);

							
							if (vertexFormat.isSet(VertexFormat.VF_Tangents))
							{
								interleavedBuffer.position(i * interleavedStride + geoToBiTanOffset + 2);
								interleavedBuffer.put((byte) -ByteConvert.readByte(stream));
								
								interleavedBuffer.position(i * interleavedStride + geoToTanOffset);
								byte tx = ByteConvert.readByte(stream);
								byte tz = (byte) -ByteConvert.readByte(stream);
								byte ty = ByteConvert.readByte(stream);
								interleavedBuffer.put(tx);
								interleavedBuffer.put(ty);
								interleavedBuffer.put(tz);

								interleavedBuffer.position(i * interleavedStride + geoToBiTanOffset + 1);
								interleavedBuffer.put(ByteConvert.readByte(stream));
							}
							else
							{
								ByteConvert.readByte(stream);//discard
							}
						}

						if (vertexFormat.isSet(VertexFormat.VF_Vertex_Colors))
						{
							interleavedBuffer.position(i * interleavedStride + geoToColorsOffset);
							interleavedBuffer.put(ByteConvert.readByte(stream));
							interleavedBuffer.put(ByteConvert.readByte(stream));
							interleavedBuffer.put(ByteConvert.readByte(stream));
							interleavedBuffer.put(ByteConvert.readByte(stream));
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
				}
				else
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
								verticesOptBuf.put(i * 3 + 0,
										MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
								verticesOptBuf.put(i * 3 + 2,
										-MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
								verticesOptBuf.put(i * 3 + 1,
										MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)) * ES_TO_METERS_SCALE);
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

	public static FloatBuffer createFB(int l)
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
