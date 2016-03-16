package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifSkinPartition
{
	/**
	     <compound name="SkinPartition" ver1="4.2.1.0">
	    Skinning data for a submesh, optimized for hardware skinning. Part of NiSkinPartition.
	    <add name="Num Vertices" type="ushort">Number of vertices in this submesh.</add>
	    <add name="Num Triangles" type="ushort">Number of triangles in this submesh.</add>
	    <add name="Num Bones" type="ushort">Number of bones influencing this submesh.</add>
	    <add name="Num Strips" type="ushort">Number of strips in this submesh (zero if not stripped).</add>
	    <add name="Num Weights Per Vertex" type="ushort">Number of weight coefficients per vertex. The Gamebryo engine seems to work well only if this number is equal to 4, even if there are less than 4 influences per vertex.</add>
	    <add name="Bones" type="ushort" arr1="Num Bones">List of bones.</add>
	    <add name="Has Vertex Map" type="bool" ver1="10.1.0.0">Do we have a vertex map?</add>
	    <add name="Vertex Map" type="ushort" arr1="Num Vertices" ver2="10.0.1.2">Maps the weight/influence lists in this submesh to the vertices in the shape being skinned.</add>
	    <add name="Vertex Map" type="ushort" arr1="Num Vertices" cond="Has Vertex Map" ver1="10.1.0.0">Maps the weight/influence lists in this submesh to the vertices in the shape being skinned.</add>
	    <add name="Has Vertex Weights" type="bool" ver1="10.1.0.0">Do we have vertex weights?</add>
	    <add name="Vertex Weights" type="float" arr1="Num Vertices" arr2="Num Weights Per Vertex" ver2="10.0.1.2">The vertex weights.</add>
	    <add name="Vertex Weights" type="float" arr1="Num Vertices" arr2="Num Weights Per Vertex" cond="Has Vertex Weights" ver1="10.1.0.0">The vertex weights.</add>
	    <add name="Strip Lengths" type="ushort" arr1="Num Strips">The strip lengths.</add>
	    <add name="Has Faces" type="bool" ver1="10.1.0.0">Do we have triangle or strip data?</add>
	    <add name="Strips" type="ushort" arr1="Num Strips" arr2="Strip Lengths" cond="Num Strips != 0" ver2="10.0.1.2">The strips.</add>
	    <add name="Strips" type="ushort" arr1="Num Strips" arr2="Strip Lengths" cond="(Has Faces) &amp;&amp; (Num Strips != 0)" ver1="10.1.0.0">The strips.</add>
	    <add name="Triangles" type="Triangle" arr1="Num Triangles" cond="Num Strips == 0" ver2="10.0.1.2">The triangles.</add>
	    <add name="Triangles" type="Triangle" arr1="Num Triangles" cond="(Has Faces) &amp;&amp; (Num Strips == 0)" ver1="10.1.0.0">The triangles.</add>
	    <add name="Has Bone Indices" type="bool">Do we have bone indices?</add>
	    <add name="Bone Indices" type="byte" arr1="Num Vertices" arr2="Num Weights Per Vertex" cond="Has Bone Indices">Bone indices, they index into &#039;Bones&#039;.</add>
		<add name="Unknown Short" type="ushort" vercond="User Version >= 12">Unknown</add>
	
	    <!-- related to the file posted in tracker item #3117836:
	        http://sourceforge.net/tracker/?func=detail&aid=3117836&group_id=149157&atid=776343 -->
	    <add name="Unknown 83 C3" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1" ></add>
	    <add name="Unknown 00 00 1" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	    <add name="Num Vertices 2" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	    <add name="Unknown 00 00 2" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	    <add name="Unknown 00 00 3" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	    <add name="Unknown 00 00 4" type="ushort" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	    <add name="Unknown Arr 1" type="SkinPartitionUnknownItem1" arr1="Num Vertices 2" ver1="10.2.0.0" ver2="10.2.0.0" vercond="User Version == 1"></add>
	</compound>
	 */

	public short numVertices;

	public short numTriangles;

	public short numBones;

	public short numStrips;

	public short numWeightsPerVertex;

	public short[] bones;

	public boolean hasVertexMap = true;

	public short[] vertexMap;

	public boolean hasVertexWeights = true;

	public float[][] vertexWeights;

	public short[] stripLengths;

	public boolean hasStrips = true;

	public short[][] strips;

	public NifTriangle[] triangles;

	public boolean hasBoneIndices;

	public short[][] boneIndices;

	public short UnknownShort;

	public NifSkinPartition(ByteBuffer stream, NifVer nifVer) throws IOException
	{

		numVertices = ByteConvert.readShort(stream);
		numTriangles = ByteConvert.readShort(stream);
		numBones = ByteConvert.readShort(stream);
		numStrips = ByteConvert.readShort(stream);
		numWeightsPerVertex = ByteConvert.readShort(stream);
		bones = new short[numBones];
		for (int i = 0; i < numBones; i++)
		{
			bones[i] = ByteConvert.readShort(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
			hasVertexMap = ByteConvert.readBool(stream, nifVer);

		if (hasVertexMap)
		{
			vertexMap = new short[numVertices];
			for (int i = 0; i < numVertices; i++)
			{
				vertexMap[i] = ByteConvert.readShort(stream);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
			hasVertexWeights = ByteConvert.readBool(stream, nifVer);

		if (hasVertexWeights)
		{
			vertexWeights = new float[numVertices][numWeightsPerVertex];
			for (int i = 0; i < numVertices; i++)
			{
				vertexWeights[i] = ByteConvert.readFloats(numWeightsPerVertex, stream);
			}
		}
		stripLengths = new short[numStrips];
		for (int i = 0; i < numStrips; i++)
		{
			stripLengths[i] = ByteConvert.readShort(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
			hasStrips = ByteConvert.readBool(stream, nifVer);

		if (hasStrips)
		{
			if (numStrips > 0)
			{
				strips = new short[numStrips][];
				for (int i = 0; i < numStrips; i++)
				{
					strips[i] = ByteConvert.readShorts(stripLengths[i], stream);
				}
			}
			else
			{
				triangles = new NifTriangle[numTriangles];
				for (int i = 0; i < numTriangles; i++)
				{
					triangles[i] = new NifTriangle(stream);
				}
			}
		}
		
		hasBoneIndices = ByteConvert.readBool(stream, nifVer);
		if (hasBoneIndices)
		{

			boneIndices = new short[numVertices][numWeightsPerVertex];
			for (int i = 0; i < numVertices; i++)
			{
				boneIndices[i] = ByteConvert.readUnsignedBytes(numWeightsPerVertex, stream);
			}
		}

		if (nifVer.LOAD_USER_VER >= 12 && !nifVer.isBP())
		{
			UnknownShort = ByteConvert.readShort(stream);
		}
	}
}
