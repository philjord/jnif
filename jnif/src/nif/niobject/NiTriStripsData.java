package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiTriStripsData extends NiTriBasedGeomData
{
	/**
	 
	 <niobject name="NiTriStripsData" abstract="0" inherit="NiTriBasedGeomData" ver1="4.1.0.12">

	 Holds mesh data using strips of triangles.
	 
	 <add name="Num Strips" type="ushort">Number of OpenGL triangle strips that are present.</add>
	 <add name="Strip Lengths" type="ushort" arr1="Num Strips">The number of points in each triangle strip.</add>
	 <add name="Has Points" type="bool" ver1="10.0.1.3">Do we have strip point data?</add>
	 <add name="Points" type="ushort" arr1="Num Strips" arr2="Strip Lengths" ver2="10.0.1.2">
	 The points in the Triangle strips.  Size is the sum of all entries in Strip Lengths.
	 </add>
	 <add name="Points" type="ushort" arr1="Num Strips" arr2="Strip Lengths" cond="Has Points != 0" ver1="10.0.1.3">
	 The points in the Triangle strips. Size is the sum of all entries in Strip Lengths.
	 </add>
	 </niobject>
	 */

	public short numStrips;

	public int[] stripLengths; //potentially over half a short

	public boolean hasPoints;

	public int[][] points;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numStrips = ByteConvert.readShort(stream);
		stripLengths = new int[numStrips];
		for (int i = 0; i < numStrips; i++)
		{
			stripLengths[i] = ByteConvert.readUnsignedShort(stream);
			//stripLengths[i] += stripLengths[i] < 0 ? 65536 : 0;

		}
		hasPoints = ByteConvert.readBool(stream, nifVer);
		if (hasPoints)
		{
			points = new int[numStrips][];
			for (int i = 0; i < numStrips; i++)
			{
				points[i] = new int[stripLengths[i]];
				for (int j = 0; j < stripLengths[i]; j++)
				{
					points[i][j] = ByteConvert.readUnsignedShort(stream);
					//points[i][j] += points[i][j] < 0 ? 65536 : 0;
				}
			}
		}

		return success;
	}
}