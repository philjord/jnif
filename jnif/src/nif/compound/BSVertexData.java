package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexData
{
	public int vertexFormatFlags1;
	public int vertexFormatFlags2;

	public BSHalfFloatVector3 vertex;

	public float f1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSHalfFloatColor4 color;

	public BSHalfFloatVector3 normal;

	public byte b1;

	public int s1;

	public int s2;

	public int s3;

	public int s4;

	public float f2;

	public int s5;

	public int s6;

	public BSVertexData(int vertexFormatFlags1, int vertexFormatFlags2, InputStream stream) throws IOException
	{
		// good for testing formats
		// f:\game media\fallout4\meshes\landscape\animated\primegroundattack01\primegroundattack01.nif
		this.vertexFormatFlags2 = vertexFormatFlags2;

		if (vertexFormatFlags2 > 7)
		{
			System.out.println("NEW VERTEX FORMAT TO DEAL WITH! " + vertexFormatFlags2);
		}

		vertex = new BSHalfFloatVector3(stream);

		f1 = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));

		if (vertexFormatFlags1 > 2)
			texCoord = new BSHalfFloatTexCoord2(stream);

		if ((vertexFormatFlags2 & 0x1) != 0)
		{
			// colors? no like that!
			color = new BSHalfFloatColor4(stream);
			//System.out.println("color any good? " + color);
		}

		if ((vertexFormatFlags2 & 0x2) != 0)
		{
			// I see these on winged creates a lot? transparency?
			s1 = ByteConvert.readUnsignedShort(stream);
			s2 = ByteConvert.readUnsignedShort(stream);
		}

		if ((vertexFormatFlags2 & 0x4) != 0)
		{
			// I feel good about this lens are near 1 ish 
			//(unless more UV sets?? could be 2 more with the f1 float? 
			normal = new BSHalfFloatVector3(stream);
			//float len = (float) Math.sqrt((normal.x * normal.x) + (normal.y * normal.y) + (normal.z * normal.z));
			//System.out.println("len? " + len + " " + normal);

			f2 = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			s5 = ByteConvert.readUnsignedShort(stream);
			s6 = ByteConvert.readUnsignedShort(stream);

		}

	}

	public String toString()
	{
		return "[BSVertexData] vertex " + vertex + " : texCoord " + texCoord;
	}

}
