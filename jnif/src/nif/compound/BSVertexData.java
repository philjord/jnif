package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexData
{

	public int vertexFormatFlags;

	public BSHalfFloatVector3 vertex;

	public float f1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSHalfFloatVector3 normal;

	public NifByteColor4 color;

	public byte b1;

	public int s1;

	public int s2;

	public int s3;

	public int s4;

	public BSVertexData(int vertexFormatFlags, InputStream stream) throws IOException
	{
		this.vertexFormatFlags = vertexFormatFlags;

		if (vertexFormatFlags > 7)
		{
			System.out.println("NEW VERTEX FORMAT TO DEAL WITH! " + vertexFormatFlags);
		}

		// perhaps normals are calculated? I perhaps they a just 3 bytes?
		// I would expect to see heaps of identical color values? though perhaps in teh extras as white is common

		vertex = new BSHalfFloatVector3(stream);

		f1 = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));

		texCoord = new BSHalfFloatTexCoord2(stream);

		// not right!
		normal = new BSHalfFloatVector3(stream);
		// float len = (float) Math.sqrt((normal.x * normal.x) + (normal.y * normal.y) + (normal.z * normal.z));

		// s4 = ByteConvert.readUnsignedShort(stream);
		ByteConvert.readByte(stream);
		ByteConvert.readByte(stream);

		if ((vertexFormatFlags & 0x1) != 0)
		{
			// no extra data in this case
		}

		if ((vertexFormatFlags & 0x2) != 0)
		{
			// No not right!
			color = new NifByteColor4(stream);
			// s1 = ByteConvert.readUnsignedShort(stream);
			// s2 = ByteConvert.readUnsignedShort(stream);
		}

		if ((vertexFormatFlags & 0x4) != 0)
		{
			// 3 normal floats? colors? 6 half floats?
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
		}

	}

	public String toString()
	{
		return "[CompressedVertexData] " + vertex + " " + color;
	}

}
