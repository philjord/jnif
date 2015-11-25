package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexDataRigid
{
	public int vertexFormatFlags;

	public BSHalfFloatVector3 vertex;

	public float unknownDot;

	public BSHalfFloatTexCoord2 texCoord;

	public BSByteColor4 color;

	public BSByteVector3 normal;

	public byte b1;

	public BSByteVector3 tangent;

	public byte b2;

	public BSVertexDataRigid(int vertexFormatFlags, InputStream stream) throws IOException
	{
		this.vertexFormatFlags = vertexFormatFlags;

		vertex = new BSHalfFloatVector3(stream);

		unknownDot = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));

		texCoord = new BSHalfFloatTexCoord2(stream);

		normal = new BSByteVector3(stream);
		b1 = ByteConvert.readByte(stream);

		tangent = new BSByteVector3(stream);
		b2 = ByteConvert.readByte(stream);

		if ((vertexFormatFlags & 0x2) != 0)
		{
			color = new BSByteColor4(stream);
		}
		
		// this flag destroys the vertex format completely
		if ((vertexFormatFlags & 0x40) != 0)
		{
			ByteConvert.readBytes(8, stream);
		}

	}

	public String toString()
	{
		return "[BSVertexDataRigid] vertex " + vertex + " : texCoord " + texCoord;
	}

}
