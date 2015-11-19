package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifTriangle;
import nif.niobject.NiTriBasedGeom;

public class BSTriShape extends NiTriBasedGeom
{	
	public int vertexFormat1;
	public int vertexFormat2;
	public int vertexFormat3;
	public int vertexFormat4;
	public int vertexFormat5;
	public int vertexFormat6;
	public int vertexFormatFlags;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public int dataSize;
	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

	
		vertexFormat1 = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormat3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormat5 = ByteConvert.readUnsignedByte(stream);
		vertexFormat6 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags = ByteConvert.readUnsignedByte(stream);
		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numTriangles = ByteConvert.readInt(stream); 
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);
	/*	System.out.println("vertexFormat1 "+vertexFormat1 );
		System.out.println("vertexFormat2 "+vertexFormat2 );
		System.out.println("vertexFormat3 "+vertexFormat3 );
		System.out.println("vertexFormat4 "+vertexFormat4 );
		System.out.println("vertexFormat5 "+vertexFormat5 );
		System.out.println("vertexFormat6 "+vertexFormat6 );
		System.out.println("vertexFormat8 "+vertexFormat8 );
		System.out.println("vertexFormatFlags "+vertexFormatFlags );
		System.out.println("numTriangles "+numTriangles );
		System.out.println("numVertices "+numVertices );
		System.out.println("dataSize "+dataSize ); 
		System.out.println("vertexdatasize  "+((dataSize-(numTriangles*6))/numVertices));*/

		vertexData = new BSVertexData[numVertices];
		for (int v = 0; v < numVertices; v++)
		{
			vertexData[v] = new BSVertexData(vertexFormat1, vertexFormatFlags, stream);
			//System.out.println("" + v + " " + vertexData[v]);
		}

		triangles = new NifTriangle[numTriangles];
		for (int t = 0; t < numTriangles; t++)
		{
			triangles[t] = new NifTriangle(stream);
			//System.out.println("" + t + " " + triangles[t]);
		}

		return success;
	}
	/*
	  NOTE this is a double use of mark (along with NIfFileReader) so the following blocks 
	 loads fail if bad
	stream.mark(0);
		for (int off = 0; off < 3; off++)
		{
			stream.reset();
			System.out.println("offset " + off);
			ByteConvert.readBytes(off, stream);
			for (int fidx = 0; fidx < 350; fidx++)
			{
				int f = ByteConvert.readInt(stream);
				if (f >= -1 && f <= 10000)
					System.out.println("off " + off + " iidx " + fidx + " " + f);
			}
		}
		stream.reset();
	
		stream.mark(0);
		for (int off = 0; off < 1; off++)
		{
			stream.reset();
			System.out.println("offset " + off);
			ByteConvert.readBytes(off, stream);
			for (int fidx = 0; fidx < 350; fidx++)
			{
				int f = ByteConvert.readUnsignedShort(stream);
				if (f >= 0 && f <= 10000)
					System.out.println("off " + off + " sidx " + fidx + " " + f);
			}
		}
		stream.reset();
	
		stream.mark(0);
		for (int off = 0; off < 3; off++)
		{
			stream.reset();
			System.out.println("offset " + off);
			ByteConvert.readBytes(off, stream);
			for (int fidx = 0; fidx < 350; fidx++)
			{
				float f = ByteConvert.readFloat(stream);
				if (f >= -1000 && f <= 1000 && Math.abs(f)>0.00000001)
					System.out.println("off " + off + " fidx " + fidx + " " + f);
			}
		}
		stream.reset();
		
	*/

}
