package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSSubIndexTriShape extends BSTriShape
{

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

	//	System.out.println("vertexFormatFlags "+vertexFormatFlags);		// 3 does not indicate anything
	//	System.out.println("numTriangles "+numTriangles);
	//	System.out.println("numVertices "+numVertices);
		int s1 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s1 = "+s1);
		int s2 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s2 = "+s2);
		int s3 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s3 = "+s3);// if this disagrees with s5 heaps more are needed?
		int s4 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s4 = "+s4);
		int s5 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s5 = "+s5);
		int s6 = ByteConvert.readUnsignedShort(stream);
	//	System.out.println("s6 = "+s6);
		
		for (int i = 0; i < s5; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				int s = ByteConvert.readUnsignedShort(stream);
			}

		}

		return success;
	}
}