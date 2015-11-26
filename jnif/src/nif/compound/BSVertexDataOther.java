package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexDataOther
{
	public int vertexFormatFlags;

	public BSHalfFloatVector3 vertex;

	public float unknownDot;
	public float unknownShort1;
	public float unknownInt1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSByteColor4 color;

	public BSByteVector3 normal;

	public byte b1;

	public BSByteVector3 tangent;

	public byte b2;

	public float[] BoneWeights = new float[4];//half
	public int[] BoneIndices = new int[4];///surely this is unsigned byte?

	public int unknownInt2;

	public BSVertexDataOther(int vertexFormatFlags, InputStream stream) throws IOException
	{

		this.vertexFormatFlags = vertexFormatFlags;

		vertex = new BSHalfFloatVector3(stream);

		if (vertexFormatFlags != 6 && vertexFormatFlags != 3)
			unknownDot = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		else
			unknownShort1 = ByteConvert.readUnsignedShort(stream);

		// differs from nif.xml
		if ((vertexFormatFlags & 0x01) != 0 || (vertexFormatFlags & 0x02) != 0)
		{
			texCoord = new BSHalfFloatTexCoord2(stream);
		}

		if (vertexFormatFlags > 3 && vertexFormatFlags != 7)
		{
			normal = new BSByteVector3(stream);
			b1 = ByteConvert.readByte(stream);

			tangent = new BSByteVector3(stream);
			b2 = ByteConvert.readByte(stream);
		}

		if (vertexFormatFlags == 6 || vertexFormatFlags == 7 || vertexFormatFlags == 9 || vertexFormatFlags == 10)
		{
			color = new BSByteColor4(stream);
		}

		if (vertexFormatFlags > 6)
		{
			for (int i = 0; i < 4; i++)
				BoneWeights[i] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			for (int i = 0; i < 4; i++)
				BoneIndices[i] = ByteConvert.readUnsignedByte(stream);
		}

		if (vertexFormatFlags == 10)
		{
			unknownInt2 = ByteConvert.readInt(stream);
		}
		
		
	

	}

	public String toString()
	{
		return "[BSVertexData] vertex " + vertex + " : texCoord " + texCoord;
	}

}
