package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiClodData extends NiTriBasedGeomData
{
	/**
	 
	 <niobject name="NiClodData" abstract="0" inherit="NiTriBasedGeomData">

	 Holds mesh data for continuous level of detail shapes.
	 Pesumably a progressive mesh with triangles specified by edge splits.
	 Seems to be specific to Freedom Force.
	 The structure of this is uncertain and highly experimental at this point.
	 No file with this data can currently be read properly.
	 
	 <add name="Unknown Shorts" type="ushort"/>
	 <add name="Unknown Count 1" type="ushort"/>
	 <add name="Unknown Count 2" type="ushort"/>
	 <add name="Unknown Count 3" type="ushort"/>
	 <add name="Unknown Float" type="float"/>
	 <add name="Unknown Short" type="ushort"/>
	 <add name="Unknown Clod Shorts 1" type="ushort" arr1="Unknown Count 1" arr2="6"/>
	 <add name="Unknown Clod Shorts 2" type="ushort" arr1="Unknown Count 2"/>
	 <add name="Unknown Clod Shorts 3" type="ushort" arr1="Unknown Count 3" arr2="6"/>
	 </niobject>
	 
	 */

	public short unknownShorts;

	public short unknownCount1;

	public short unknownCount2;

	public short unknownCount3;

	public float unknownFloat;

	public short unknownShort;

	public short[][] unknownClodShorts1;

	public short[] unknownClodShorts2;

	public short[][] unknownClodShorts3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShorts = ByteConvert.readShort(stream);
		unknownCount1 = ByteConvert.readShort(stream);
		unknownCount2 = ByteConvert.readShort(stream);
		unknownCount3 = ByteConvert.readShort(stream);
		unknownFloat = ByteConvert.readFloat(stream);
		unknownShort = ByteConvert.readShort(stream);

		unknownClodShorts1 = new short[unknownCount1][6];
		for (int i = 0; i < unknownCount1; i++)
		{
			unknownClodShorts1[i] = ByteConvert.readShorts(6, stream);
		}

		unknownClodShorts2 = new short[unknownCount2];
		for (int i = 0; i < unknownCount2; i++)
		{
			unknownClodShorts2[i] = ByteConvert.readShort(stream);
		}

		unknownClodShorts3 = new short[unknownCount3][6];
		for (int i = 0; i < unknownCount3; i++)
		{
			unknownClodShorts3[i] = ByteConvert.readShorts(6, stream);
		}

		return success;
	}
}