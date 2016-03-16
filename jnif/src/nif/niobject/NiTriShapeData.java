package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMatchGroup;
import nif.compound.NifTriangle;

public class NiTriShapeData extends NiTriBasedGeomData
{
	/**
	 <niobject name="NiTriShapeData" abstract="0" inherit="NiTriBasedGeomData">
	
	 Holds mesh data using a list of singular triangles.
	 
	 <add name="Num Triangle Points" type="uint">Num Triangles times 3.</add>
	 <add name="Has Triangles" type="bool" ver1="10.1.0.0">Do we have triangle data?</add>
	 <add name="Triangles" type="Triangle" arr1="Num Triangles" ver2="10.0.1.2">Triangle data.</add>
	 <add name="Triangles" type="Triangle" arr1="Num Triangles" cond="Has Triangles != 0" ver1="10.0.1.3">Triangle face data.</add>
	 <add name="Num Match Groups" type="ushort" ver1="3.1">Number of shared normals groups.</add>
	 <add name="Match Groups" type="MatchGroup" arr1="Num Match Groups" ver1="3.1">The shared normals.</add>
	 </niobject>
	 */

	public int numTrianglePoints;

	public boolean hasTriangles = true;

	public NifTriangle[] triangles;
	//OPTOMISATION
	public int[] trianglesOpt;
	public IntBuffer trianglesOptBuf;

	public int numMatchGroups;

	public NifMatchGroup[] matchGroups;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numTrianglePoints = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			hasTriangles = ByteConvert.readBool(stream, nifVer);
		}
		// has triangles wasn't used  until a few version after it appeared
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_3 || hasTriangles)
		{
			if (LOAD_OPTIMIZED)
			{
				trianglesOpt = new int[numTriangles * 3];
				for (int i = 0; i < numTriangles; i++)
				{
					trianglesOpt[i * 3 + 0] = ByteConvert.readUnsignedShort(stream);
					trianglesOpt[i * 3 + 1] = ByteConvert.readUnsignedShort(stream);
					trianglesOpt[i * 3 + 2] = ByteConvert.readUnsignedShort(stream);
				}
			}
			else
			{
				triangles = new NifTriangle[numTriangles];
				for (int i = 0; i < numTriangles; i++)
				{
					triangles[i] = new NifTriangle(stream);
				}
			}
		}

		numMatchGroups = ByteConvert.readUnsignedShort(stream);
		matchGroups = new NifMatchGroup[numMatchGroups];
		for (int i = 0; i < numMatchGroups; i++)
		{
			matchGroups[i] = new NifMatchGroup(stream);
		}

		return success;
	}
}