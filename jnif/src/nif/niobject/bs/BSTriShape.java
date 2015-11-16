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
	// neither of thes is useful as yet
	// note halffloats are used in BSPackedAdditionalGeometryData<!-- Fallout NV (nvdlc01vaultposter01.nif) -->
	// wow http://docs.oracle.com/cd/E17802_01/j2se/javase/technologies/desktop/java3d/forDevelopers/j3dguide/AppendixCompress.doc.html

	int s1;

	int b1;

	int b2;

	int s2;

	public int vertexFormatFlags;

	public int numTriangles;

	int s3;

	public int numVertices;

	int s4;

	int s5;

	public BSVertexData[] vertexData;

	public NifTriangle[] triangles;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		//Problem  in f:\game media\fallout4\meshes\actors\character\characterassets\1stpersonreversearm.nif
		//i=29 type= BSTriShape should have read off 51724 but in fact read off 48180
		//3544 not a multiple of vertexnum 
		// for this roblem vertex 1118 is in fact the start of tris! but count read off is 1491

		//other examples
		//Problem  in f:\game media\fallout4\meshes\architecture\commercial\monorailstation01.nif i=4 type= BSTriShape should have read off 982 but in fact read off 1366
		//Problem  in f:\game media\fallout4\meshes\architecture\warehouse\greenhouse\greenhsdoorsm01.nif i=26 type= BSTriShape should have read off 2398 but in fact read off 2025
		
		
		
		
		
		//System.out.println("name " + this.name);
		//System.out.println("properties[0] " + properties[0]);
		//System.out.println("properties[1] " + properties[1]);

		s1 = ByteConvert.readUnsignedShort(stream);
		//System.out.println("s1 " + s1); //normally 517 but when 518 when format is 3

		b1 = ByteConvert.readUnsignedByte(stream);
		//System.out.println("b1 " + b1); //67?
		b2 = ByteConvert.readUnsignedByte(stream);
		//System.out.println("b2 " + b2); //0? 3x5 seen with 1=518 I think format change
		s2 = ByteConvert.readUnsignedShort(stream);
		//System.out.println("s2 " + s2); // 45056 in all cases, one case 12288

		vertexFormatFlags = ByteConvert.readUnsignedShort(stream); // 1, 3, 5 seen
		//System.out.println("vertexFormatFlags " + vertexFormatFlags);
		numTriangles = ByteConvert.readUnsignedShort(stream);
		//System.out.println("numTriangles " + numTriangles);

		s3 = ByteConvert.readUnsignedShort(stream);
		//System.out.println("s3 " + s3);//0?

		numVertices = ByteConvert.readUnsignedShort(stream);
		//System.out.println("numVertices " + numVertices);

		s4 = ByteConvert.readUnsignedShort(stream);
		//System.out.println("s4 " + s4);//random - flags?
		s5 = ByteConvert.readUnsignedShort(stream);
		//System.out.println("s5 " + s4);//0 in all cases 1x1 seen rockpileL01

		//	BSTriShape size = 122, mean load no vertex data
		//TODO: lots of the above are 0's when size is 122 but numVertices is not 0
		if (s1 != 0)
		{
			vertexData = new BSVertexData[numVertices];
			for (int v = 0; v < numVertices; v++)
			{
				vertexData[v] = new BSVertexData(vertexFormatFlags, stream);
				//System.out.println("" + v + " " + vertexData[v]);
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
