package nif.niobject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifColor4;
import nif.compound.NifTexCoord;
import nif.compound.NifVector3;
import nif.enums.ConsistencyType;
import nif.niobject.particle.NiPSysData;

public abstract class NiGeometryData extends NiObject
{
	public static boolean LOAD_OPTIMIZED = true;
	public static final float ES_TO_METERS_SCALE = 0.02f;

	/**
	  <niobject name="NiGeometryData" abstract="1" inherit="NiObject">
	    Mesh data: vertices, vertex normals, etc.
	    <add name="Unknown Int" type="int" ver1="10.2.0.0">Unknown identifier. Always 0.</add>
	    <add name="Num Vertices" type="ushort" >Number of vertices. For NiPSysData this is max particles.</add>
	    <add name="Keep Flags" type="byte" ver1="10.1.0.0">Used with NiCollision objects when OBB or TRI is set.</add>
	    <add name="Compress Flags" type="byte" ver1="10.1.0.0">Unknown.</add>
	    <add name="Has Vertices" type="bool" default="1">Is the vertex array present? (Always non-zero.)</add>
	    <add name="Vertices" type="Vector3" arr1="Num Vertices" cond="Has Vertices">The mesh vertices.</add>
	    <add name="Num UV Sets" type="ushort" vercond="((Version >= 10.0.1.0) &amp;&amp; (!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))))">Methods for saving binormals and tangents saved in upper byte.  Texture flags in lower byte.</add>
	    <add name="Num UV Sets" type="ushort" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Bethesda's version of this field for nif versions 20.2.0.7 and up. Only a single bit denotes whether uv's are present. For example, see meshes/architecture/megaton/megatonrampturn45sml.nif in Fallout 3.</add>
	    <add name="Unknown Int 2" type="uint" ver1="20.2.0.7" userver="12">Unknown, seen in Skyrim.</add>	
	    <add name="Has Normals" type="bool">Do we have lighting normals? These are essential for proper lighting: if not present, the model will only be influenced by ambient light.</add>
	    <add name="Normals" type="Vector3" arr1="Num Vertices" cond="Has Normals">The lighting normals.</add>
	    <add name="Tangents" type="Vector3" arr1="Num Vertices" cond="(Has Normals) &amp;&amp; (Num UV Sets &amp; 61440)" ver1="10.1.0.0">Unknown. Binormal &amp; tangents?</add>
	    <add name="Binormals" type="Vector3" arr1="Num Vertices" cond="(Has Normals) &amp;&amp; (Num UV Sets &amp; 61440)" ver1="10.1.0.0">Unknown. Binormal &amp; tangents? has_normals must be set as well for this field to be present.</add>
	    <add name="Center" type="Vector3">Center of the bounding box (smallest box that contains all vertices) of the mesh.</add>
	    <add name="Radius" type="float">Radius of the mesh: maximal Euclidean distance between the center and all vertices.</add>
	    <add name="Unknown 13 shorts" type="short" arr1="13" ver1="20.3.0.9" ver2="20.3.0.9" userver="131072">Unknown, always 0?</add>
	    <add name="Has Vertex Colors" type="bool">
	        Do we have vertex colors? These are usually used to fine-tune the lighting of the model.
	
	        Note: how vertex colors influence the model can be controlled by having a NiVertexColorProperty object as a property child of the root node. If this property object is not present, the vertex colors fine-tune lighting.
	
	        Note 2: set to either 0 or 0xFFFFFFFF for NifTexture compatibility.
	    </add>
	    <add name="Vertex Colors" type="Color4" arr1="Num Vertices" cond="Has Vertex Colors">The vertex colors.</add>
	    <add name="Num UV Sets" type="ushort" ver2="4.2.2.0">The lower 6 (or less?) bits of this field represent the number of UV texture sets. The other bits are probably flag bits. For versions 10.1.0.0 and up, if bit 12 is set then extra vectors are present after the normals.</add>
	    <add name="Has UV" type="bool" ver2="4.0.0.2">
	        Do we have UV coordinates?
	
	        Note: for compatibility with NifTexture, set this value to either 0x00000000 or 0xFFFFFFFF.
	    </add>
	    <add name="UV Sets" type="TexCoord" arr1="Num UV Sets &amp; 63" arr2="Num Vertices" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">The UV texture coordinates. They follow the OpenGL standard: some programs may require you to flip the second coordinate.</add>
	    <add name="UV Sets" type="TexCoord" arr1="Num UV Sets &amp; 1" arr2="Num Vertices"  vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">The UV texture coordinates. They follow the OpenGL standard: some programs may require you to flip the second coordinate.</add>
	    <add name="Consistency Flags" type="ConsistencyType" ver1="10.0.1.0" default="CT_MUTABLE">Consistency Flags</add>
	    <add name="Additional Data" type="Ref" template="AbstractAdditionalGeometryData" ver1="20.0.0.4">Unknown.</add>
	</niobject>
	
	 */

	public int unknownInt1;

	public int numVertices;

	public int BSMaxVertices;

	public byte keepFlags;

	public byte compressFlags;

	public boolean hasVertices;

	public NifVector3[] vertices;
	//OPTOMISATIONS
	public FloatBuffer verticesOptBuf;

	public int numUVSets;

	public boolean hasUV;//not used

	public int actNumUVSets;// calculated for clarity

	public int unknownInt2;

	public boolean hasNormals;

	public NifVector3[] normals;
	//OPTOMISATIONS
	public FloatBuffer normalsOptBuf;

	public NifVector3[] binormals;
	//OPTOMISATIONS
	public FloatBuffer binormalsOptBuf;

	public NifVector3[] tangents;
	//OPTOMISATIONS
	public FloatBuffer tangentsOptBuf;

	public NifVector3 center;

	public float radius;

	public short[] unknown13Shorts;

	public boolean hasVertexColors;

	public NifColor4[] vertexColors;
	//OPTOMISATIONS
	public FloatBuffer vertexColorsOptBuf;

	public NifTexCoord[][] uVSets;
	//OPTOMISATIONS
	public FloatBuffer[] uVSetsOptBuf;

	public ConsistencyType consistencyType;

	public NifRef additionalData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0)
		{
			unknownInt1 = ByteConvert.readInt(stream);
		}

		if (!(this instanceof NiPSysData))
		{
			numVertices = ByteConvert.readUnsignedShort(stream);
		}
		else
		{
			if (nifVer.LOAD_VER < NifVer.VER_20_2_0_7 || nifVer.LOAD_USER_VER < 11)
			{
				numVertices = ByteConvert.readUnsignedShort(stream);
			}
			else if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11)
			{
				BSMaxVertices = ByteConvert.readUnsignedShort(stream);
			}
		}
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			keepFlags = ByteConvert.readByte(stream);
			compressFlags = ByteConvert.readByte(stream);
		}

		hasVertices = ByteConvert.readBool(stream, nifVer);
		if (hasVertices)
		{
			if (LOAD_OPTIMIZED)
			{
				verticesOptBuf = createFB(numVertices * 3);
				for (int i = 0; i < numVertices; i++)
				{
					verticesOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
					verticesOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
					verticesOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
				}

			}
			else
			{
				vertices = new NifVector3[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					vertices[i] = new NifVector3(stream);
				}
			}
		}
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			numUVSets = ByteConvert.readUnsignedShort(stream);
		}

		if (!(this instanceof NiPSysData) && nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && !nifVer.isBP())
		{
			unknownInt2 = ByteConvert.readInt(stream);
		}

		hasNormals = ByteConvert.readBool(stream, nifVer);
		if (hasNormals)
		{
			if (LOAD_OPTIMIZED)
			{
				normalsOptBuf = createFB(numVertices * 3);
				for (int i = 0; i < numVertices; i++)
				{
					normalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
					normalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
					normalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
				}

			}
			else
			{
				normals = new NifVector3[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					normals[i] = new NifVector3(stream);
				}
			}

			if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
			{
				if ((numUVSets & 61440) != 0)
				{
					if (LOAD_OPTIMIZED)
					{
						tangentsOptBuf = createFB(numVertices * 3);
						for (int i = 0; i < numVertices; i++)
						{
							tangentsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
							tangentsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
							tangentsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
						}

						binormalsOptBuf = createFB(numVertices * 3);
						for (int i = 0; i < numVertices; i++)
						{
							binormalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
							binormalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
							binormalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
						}

					}
					else
					{
						tangents = new NifVector3[numVertices];
						for (int i = 0; i < numVertices; i++)
						{
							tangents[i] = new NifVector3(stream);
						}

						binormals = new NifVector3[numVertices];
						for (int i = 0; i < numVertices; i++)
						{
							binormals[i] = new NifVector3(stream);
						}

					}
				}
			}
		}

		center = new NifVector3(stream);
		radius = ByteConvert.readFloat(stream);

		if (nifVer.LOAD_VER == NifVer.VER_20_3_0_9 && nifVer.LOAD_USER_VER == 131072)
		{
			unknown13Shorts = ByteConvert.readShorts(13, stream);
		}

		hasVertexColors = ByteConvert.readBool(stream, nifVer);
		if (hasVertexColors)
		{
			if (LOAD_OPTIMIZED)
			{
				vertexColorsOptBuf = createFB(numVertices * 4);
				for (int i = 0; i < numVertices; i++)
				{
					vertexColorsOptBuf.put(i * 4 + 0, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 1, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 2, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 3, ByteConvert.readFloat(stream));
				}

			}
			else
			{
				vertexColors = new NifColor4[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					vertexColors[i] = new NifColor4(stream);
				}
			}
		}

		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			numUVSets = ByteConvert.readUnsignedShort(stream);
		}

		if (nifVer.LOAD_VER <= NifVer.VER_4_0_0_2)
		{
			hasUV = ByteConvert.readBool(stream, nifVer);
		}

		//calculated actual value based on version
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && !nifVer.isBP())
		{
			actNumUVSets = numUVSets & 1;
		}
		else
		{
			actNumUVSets = numUVSets & 63;
		}
		if (LOAD_OPTIMIZED)
		{
			uVSetsOptBuf = new FloatBuffer[actNumUVSets];
			for (int j = 0; j < actNumUVSets; j++)
			{
				uVSetsOptBuf[j] = createFB(numVertices * 2);
				for (int i = 0; i < numVertices; i++)
				{
					uVSetsOptBuf[j].put(i * 2 + 0, ByteConvert.readFloat(stream));
					uVSetsOptBuf[j].put(i * 2 + 1, ByteConvert.readFloat(stream));
				}
			}

		}
		else
		{
			uVSets = new NifTexCoord[actNumUVSets][numVertices];
			for (int j = 0; j < actNumUVSets; j++)
			{
				for (int i = 0; i < numVertices; i++)
				{
					uVSets[j][i] = new NifTexCoord(stream);
				}
			}
		}

		if ((!(this instanceof NiPSysData) || nifVer.LOAD_USER_VER < 12) && nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			consistencyType = new ConsistencyType(stream);
		}

		if ((!(this instanceof NiPSysData) || nifVer.LOAD_USER_VER < 12) && nifVer.LOAD_VER >= NifVer.VER_20_0_0_4)
		{
			additionalData = new NifRef(NiAdditionalGeometryData.class, stream);
		}

		return success;
	}

	public void loadTangentAndBinormalsFromExtraData(InputStream stream, NifVer nifVer) throws IOException
	{
		if (tangentsOptBuf != null)
			new Throwable("ALERT tangents already set!!!1!").printStackTrace();

		if (hasNormals)
		{
			if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
			{
				if (LOAD_OPTIMIZED)
				{
					tangentsOptBuf = createFB(numVertices * 3);
					for (int i = 0; i < numVertices; i++)
					{
						tangentsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
						tangentsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
						tangentsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
					}

					binormalsOptBuf = createFB(numVertices * 3);
					for (int i = 0; i < numVertices; i++)
					{
						binormalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
						binormalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
						binormalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
					}

				}
				else
				{
					tangents = new NifVector3[numVertices];
					for (int i = 0; i < numVertices; i++)
					{
						tangents[i] = new NifVector3(stream);
					}

					binormals = new NifVector3[numVertices];
					for (int i = 0; i < numVertices; i++)
					{
						binormals[i] = new NifVector3(stream);
					}

				}
			}
		}
	}

	protected static FloatBuffer createFB(int l)
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(l * 4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();

	}

}