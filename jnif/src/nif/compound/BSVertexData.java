package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexData
{

	public int vertexFormatFlags;

	public int s1;

	public int s2;

	public BSHalfFloatVector3 vertex;

	public float x;

	public float y;

	public float z;

	public NifByteColor3 color;

	public byte b1;

	public int s3;

	public int s4;

	public BSVertexData(int vertexFormatFlags, InputStream stream) throws IOException
	{
		this.vertexFormatFlags = vertexFormatFlags;

		if (vertexFormatFlags > 7)
		{
			System.out.println("NEW VERTEX FORMAT TO DEAL WITH! " + vertexFormatFlags);
		}

		if ((vertexFormatFlags & 0x1) != 0)
		{
			// no extra data in this case			
		}

		if ((vertexFormatFlags & 0x2) != 0)
		{
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
		}

		if ((vertexFormatFlags & 0x4) != 0)
		{
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
		}

		// y and z are never negative? UV?
		// x y and z are always <1 (1>x<-1) in fact some are slightly over here and there

		//perhaps normals are calculated? I see identical normal positions, which is wrong

		// I would expect to see heaps of identical color values?

		// maybe colors are fixed from one value unless a flag?

		vertex = new BSHalfFloatVector3(stream);

		x = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		//	System.out.print("x " + x);
		y = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		//	System.out.print("\ty " + y);
		z = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		//len = (float) Math.sqrt((x * x) + (y * y) + (z * z));
		//	System.out.println("\tz " + z + " len " + len);

		// I need colors? these shorts below? or bytes
		// I need uv coords? 2 bytes?

		color = new NifByteColor3(stream);// color is way wrong
		b1 = ByteConvert.readByte(stream);
		s3 = ByteConvert.readUnsignedShort(stream);
		s4 = ByteConvert.readUnsignedShort(stream);

	}

	public String toString()
	{
		return "[CompressedVertexData] " + vertex + " " + color;
	}

}
