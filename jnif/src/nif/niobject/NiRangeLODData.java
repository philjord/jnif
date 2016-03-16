package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifLODRange;
import nif.compound.NifVector3;

public class NiRangeLODData extends NiLODData
{
	/**
	 
	 <niobject name="NiRangeLODData" abstract="0" inherit="NiLODData">

	 Describes levels of detail based on distance of object from camera.
	 
	 <add name="LOD Center" type="Vector3">?</add>
	 <add name="Num LOD Levels" type="uint">Number of levels of detail.</add>
	 <add name="LOD Levels" type="LODRange" arr1="Num LOD Levels">
	 The ranges of distance that each level of detail applies in.
	 </add>
	 </niobject>
	 */

	public NifVector3 lODCenter;

	public int numLODLevels;

	public NifLODRange[] lODLevels;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		lODCenter = new NifVector3(stream);
		numLODLevels = ByteConvert.readInt(stream);
		lODLevels = new NifLODRange[numLODLevels];
		for (int i = 0; i < numLODLevels; i++)
		{
			lODLevels[i] = new NifLODRange(stream);
		}

		return success;
	}
}