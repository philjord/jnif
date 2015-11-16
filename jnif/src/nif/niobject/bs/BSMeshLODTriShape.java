package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSMeshLODTriShape extends BSTriShape
{
	int[] lods;//???

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		// idx4 = num tris in  a lot of cases, somes cases idx2 =numTris 
		//looks like LOD distances like old system, where the count of how far tri should be used
		lods = ByteConvert.readUnsignedShorts(6, stream);
		//	for (int v = 0; v < 6; v++)
		{
			//		System.out.print(" " + lods[v]);
		}
		//	System.out.println("");

		return success;
	}
}
