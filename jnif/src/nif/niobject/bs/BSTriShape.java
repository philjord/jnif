package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexDataOther;
import nif.compound.BSVertexDataRigid;
import nif.compound.BSVertexDataSkinned;
import nif.compound.NifTriangle;
import nif.niobject.NiTriBasedGeom;

public class BSTriShape extends NiTriBasedGeom
{
	public int vertexFormatFlags1;
	public int vertexFormat2;
	public int vertexFormat3;
	public int vertexFormat4;
	public int vertexFormatFlags5;
	public int vertexFormat6;
	public int vertexFormatFlags7;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public int dataSize;
	public BSVertexDataRigid[] vertexDataRigid;
	public BSVertexDataSkinned[] vertexDataSkinned;
	public BSVertexDataOther[] vertexDataOther;
	public NifTriangle[] triangles;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		//CAREFUL CAREFUL!!! Optimized version  exists in jnifj3d!!!

		vertexFormatFlags1 = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormat3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags5 = ByteConvert.readUnsignedByte(stream);
		vertexFormat6 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags7 = ByteConvert.readUnsignedByte(stream);
		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numTriangles = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readUnsignedShort(stream);

		dataSize = ByteConvert.readInt(stream);

		if ((vertexFormatFlags7 & 0x8) != 0 || (vertexFormatFlags7 & 0x10) != 0 || (vertexFormatFlags7 & 0x20) != 0)
		{
			System.out.println("NEW VERTEX FORMAT TO DEAL WITH! " + vertexFormatFlags7);
		}
		if ((vertexFormatFlags7 & 0x40) != 0)
		{
			System.out.println("(vertexFormatFlags & 0x40) != 0)");
		}

		if (dataSize > 0)
		{
			// good for testing formats
			// f:\game media\fallout4\meshes\landscape\animated\primegroundattack01\primegroundattack01.nif
			if ((vertexFormatFlags7 & 0x1) != 0)
			{
				if ((vertexFormatFlags7 & 0x4) == 0)
				{
					vertexDataRigid = new BSVertexDataRigid[numVertices];
					for (int v = 0; v < numVertices; v++)
					{
						vertexDataRigid[v] = new BSVertexDataRigid(vertexFormatFlags7, stream);
						//System.out.println("" + v + " " + vertexData[v]);
					}
				}
				else if (vertexFormatFlags5 == 0)
				{
					vertexDataSkinned = new BSVertexDataSkinned[numVertices];
					for (int v = 0; v < numVertices; v++)
					{
						vertexDataSkinned[v] = new BSVertexDataSkinned(vertexFormatFlags7, stream);
						//System.out.println("" + v + " " + vertexData[v]);
					}
				}
			}

			if ((vertexFormatFlags7 & 0x1) == 0 || vertexFormatFlags5 > 0)
			{
				vertexDataOther = new BSVertexDataOther[numVertices];
				for (int v = 0; v < numVertices; v++)
				{
					vertexDataOther[v] = new BSVertexDataOther(vertexFormatFlags1, stream);
					//System.out.println("" + v + " " + vertexData[v]);
				}
			}

			triangles = new NifTriangle[numTriangles];
			for (int t = 0; t < numTriangles; t++)
			{
				triangles[t] = new NifTriangle(stream);
				//System.out.println("" + t + " " + triangles[t]);
			}
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
