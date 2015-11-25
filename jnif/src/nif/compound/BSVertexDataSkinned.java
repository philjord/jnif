package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexDataSkinned extends BSVertexDataRigid
{
	public float[] BoneWeights = new float[4];//half
	public int[] BoneIndices = new int[4];///surely this is unsigned byte?

	public BSVertexDataSkinned(int vertexFormatFlags, InputStream stream) throws IOException
	{
		super(vertexFormatFlags, stream);

		for (int i = 0; i < 4; i++)
			BoneWeights[i] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		for (int i = 0; i < 4; i++)
			BoneIndices[i] = ByteConvert.readUnsignedByte(stream);
	}

	public String toString()
	{
		return "[BSVertexDataSkinned] vertex " + vertex + " : texCoord " + texCoord;
	}

}
