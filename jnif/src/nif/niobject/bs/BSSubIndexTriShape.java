package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifTriangle;
import tools.MiniFloat;

public class BSSubIndexTriShape extends BSTriShape
{

	int numTriangles2;
	int numA;
	public NifTriangle[] triangles2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numTriangles2 = ByteConvert.readUnsignedShort(stream);

		numA = ByteConvert.readUnsignedShort(stream);

			 

		/*System.out.println("numTriangles2 " + numTriangles2);
		System.out.println("numA " + numA);
		
		System.out.println("1 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("2 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("3 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("4 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("5 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("6 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("7 "+  ByteConvert.readUnsignedShort(stream));
		System.out.println("8 "+  ByteConvert.readUnsignedShort(stream));
		*/
		
		//5 0 5 0  = 80 data size=32 (needs vetex format of 10??
		//7 0 12 0 = 424 data size=32
		//16 0 16 = 256    datasize =8
		
		// first is numtri second is? vertexes?
		
	/*	for (int fidx = 0; fidx < 44; fidx++)
		{
			int f = ByteConvert.readUnsignedShort(stream);
			if (f >= 0 && f <= 10000)
				System.out.println(" sidx " + fidx + " " + f + " " + MiniFloat.toFloat(f));
		}*/

		//264 more? 26 tri *10?
		//8=264?
		//32 = 432

		//numTri*size? = another round of vertexs?
		



	

		return success;
	}
}