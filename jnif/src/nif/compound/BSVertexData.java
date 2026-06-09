package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.niobject.bs.BSTriShape.VertexFormat;
import nif.tools.FP16;
/**



<struct name="BSVertexData" versions="#SSE# #FO4# #F76#" module="BSMain">
    Byte fields for normal, tangent and bitangent map [0, 255] to [-1, 1].
	<field name="Vertex" type="Vector3" cond="(#ARG# #BITAND# 0x401) == 0x401" />
	<field name="Bitangent X" type="float" cond="(#ARG# #BITAND# 0x411) == 0x411" />
	<field name="Unused W" type="uint" cond="(#ARG# #BITAND# 0x411) == 0x401" />

	<field name="Vertex" type="HalfVector3" cond="(#ARG# #BITAND# 0x401) == 0x1" />
	<field name="Bitangent X" type="hfloat" cond="(#ARG# #BITAND# 0x411) == 0x11" />
	<field name="Unused W" type="ushort" cond="(#ARG# #BITAND# 0x411) == 0x1" />

	<field name="UV" type="HalfTexCoord" cond="(#ARG# #BITAND# 0x2) != 0" />
	<field name="Normal" type="ByteVector3" cond="(#ARG# #BITAND# 0x8) != 0" />
	<field name="Bitangent Y" type="normbyte" cond="(#ARG# #BITAND# 0x8) != 0" />
	<field name="Tangent" type="ByteVector3" cond="(#ARG# #BITAND# 0x18) == 0x18" />
	<field name="Bitangent Z" type="normbyte" cond="(#ARG# #BITAND# 0x18) == 0x18" />
	<field name="Vertex Colors" type="ByteColor4" cond="(#ARG# #BITAND# 0x20) != 0" />
	<field name="Bone Weights" type="hfloat" length="4" cond="(#ARG# #BITAND# 0x40) != 0" />
	<field name="Bone Indices" type="byte" length="4" cond="(#ARG# #BITAND# 0x40) != 0" />
	<field name="Eye Data" type="float" cond="(#ARG# #BITAND# 0x100) != 0" />
</struct>

<struct name="BSVertexDataSSE" versions="#SSE# #FO4#" module="BSMain">
	<field name="Vertex" type="Vector3" cond="(#ARG# #BITAND# 0x1) != 0" />
	<field name="Bitangent X" type="float" cond="(#ARG# #BITAND# 0x11) == 0x11" />
	<field name="Unused W" type="uint" cond="(#ARG# #BITAND# 0x11) == 0x1" />
	<field name="UV" type="HalfTexCoord" cond="(#ARG# #BITAND# 0x2) != 0" />
	<field name="Normal" type="ByteVector3" cond="(#ARG# #BITAND# 0x8) != 0" />
	<field name="Bitangent Y" type="normbyte" cond="(#ARG# #BITAND# 0x8) != 0" />
	<field name="Tangent" type="ByteVector3" cond="(#ARG# #BITAND# 0x18) == 0x18" />
	<field name="Bitangent Z" type="normbyte" cond="(#ARG# #BITAND# 0x18) == 0x18" />
	<field name="Vertex Colors" type="ByteColor4" cond="(#ARG# #BITAND# 0x20) != 0" />
	<field name="Bone Weights" type="hfloat" length="4" cond="(#ARG# #BITAND# 0x40) != 0" />
	<field name="Bone Indices" type="byte" length="4" cond="(#ARG# #BITAND# 0x40) != 0" />
	<field name="Eye Data" type="float" cond="(#ARG# #BITAND# 0x100) != 0" />
</struct>
 */
public class BSVertexData
{
	

	
	
	public VertexFormat vertexFormat;

	public NifVector3 vertex;
	public float bitangentX;

	public float unknownShort1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSByteColor4 color;

	public BSByteVector3 normal;
	public float bitangentY;

	public BSByteVector3 tangent;
	public float bitangentZ;

	public float[] BoneWeights = new float[4];
	public int[] BoneIndices = new int[4];

	

	public BSVertexData(VertexFormat vertexFormat, ByteBuffer stream) throws IOException
	{
		this.vertexFormat = vertexFormat;
		if (vertexFormat.isSet(VertexFormat.VF_Vertex))
		{
			if (vertexFormat.isSet(VertexFormat.VF_Full_Precision))
			{
				vertex = new NifVector3(stream);
				bitangentX = ByteConvert.readFloat(stream);
			}
			else
			{
				vertex = new BSHalfFloatVector3(stream);
				bitangentX = FP16.toFloat(ByteConvert.readShort(stream));
			}
		}

		if (vertexFormat.isSet(VertexFormat.VF_UVs))
		{
			texCoord = new BSHalfFloatTexCoord2(stream);
		}

		if (vertexFormat.isSet(VertexFormat.VF_Normals))
		{
			normal = new BSByteVector3(stream);
			bitangentY = ((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
			if (vertexFormat.isSet(VertexFormat.VF_Tangents))
			{
				tangent = new BSByteVector3(stream);
				bitangentZ = ((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
			}
		}

		if (vertexFormat.isSet(VertexFormat.VF_Vertex_Colors))
		{
			color = new BSByteColor4(stream);
		}

		if (vertexFormat.isSet(VertexFormat.VF_Skinned))
		{
			BoneWeights = new float[4];
			for (int b = 0; b < 4; b++)
				BoneWeights[b] = FP16.toFloat(ByteConvert.readShort(stream));
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

	public String toString()
	{
		return "[BSVertexData] vertex " + vertex + " : texCoord " + texCoord;
	}

}
