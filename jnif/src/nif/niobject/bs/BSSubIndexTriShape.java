package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifTriangle;

public class BSSubIndexTriShape extends BSTriShape
{

	int numTriangles2;
	int numA;
	int int1;
	int int2;
	public NifTriangle[] triangles2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numTriangles2 = ByteConvert.readUnsignedShort(stream);
		numA = ByteConvert.readUnsignedShort(stream);
		int1 = ByteConvert.readInt(stream);
		int2 = ByteConvert.readInt(stream);

		//2  2 = 34  16*2=32 ( or 8 for each one)
		//4  4 = 64 (16*4)
		//5  5 0 = 80  (16*5=80)
		//6  6 = 96 (16*6)
		//11  11 = 176 (16*11) 
		//16  16 = 256  (16*16=256)		


		//1 2 = 111
		//7  12  = 424 datasize=32
		//2  3 = 152 datasize =36  (36*2 + 10 + 3*12 = 152)
		//2  3 = 153 datasize =36 so there is a size inside this guy?		
		//7  27 = 1024 datasize=32 ok houston! these guys differ
		//f:\game media\fallout4\meshes\actors\alien\characterassets\alien_body.nif
		//7  27 = 843 datasize=32  
		//f:\game media\fallout4\meshes\actors\gorilla\characterassets\gorilla.nif

		ByteConvert.readBytes(int1 * 16, stream);
		if (int1 < int2)
		{
			int diff = (int2 - int1);
			System.out.println("int1!=int2 " + int1 + " " + int2 + " diff = " + diff);
			/*	System.out.println("vertexFormat1 " + vertexFormat1);
				System.out.println("vertexFormat2 " + vertexFormat2);
				System.out.println("vertexFormat3 " + vertexFormat3);
				System.out.println("vertexFormat4 " + vertexFormat4);
				System.out.println("vertexFormat5 " + vertexFormat5);
				System.out.println("vertexFormat6 " + vertexFormat6);
				System.out.println("vertexFormat8 " + vertexFormat8);
				System.out.println("vertexFormatFlags " + vertexFormatFlags);
				System.out.println("numTriangles " + numTriangles);
				System.out.println("numVertices " + numVertices);
				System.out.println("dataSize " + dataSize);
				System.out.println("vertexdatasize  " + ((dataSize - (numTriangles * 6)) / numVertices));*/

			/*	for (int fidx = 0; fidx < 46; fidx++)
				{
					int f = ByteConvert.readUnsignedShort(stream);
					System.out.println(" sidx " + fidx + " " + f + " "+ MiniFloat.toFloat(f));
				}*/
		}

		return success;
	}
}