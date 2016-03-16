package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifFurniturePosition;
import nif.niobject.NiExtraData;

public class BSFurnitureMarker extends NiExtraData
{
	/**
	 
	 <niobject name="BSFurnitureMarker" abstract="0" inherit="NiExtraData" ver1="20.0.0.5">

	 Unknown. Marks furniture sitting positions?
	 
	 <add name="Num Positions" type="uint">Number of positions.</add>
	 <add name="Positions" type="FurniturePosition" arr1="Num Positions">
	 Unknown. Probably has something to do with the furniture positions?
	 </add>
	 </niobject>
	 */

	public int numPositions;

	public NifFurniturePosition[] positions;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numPositions = ByteConvert.readInt(stream);
		positions = new NifFurniturePosition[numPositions];
		for (int i = 0; i < numPositions; i++)
		{
			positions[i] = new NifFurniturePosition(stream, nifVer);
		}

		return success;
	}
}