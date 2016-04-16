package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;
import nif.tools.MiniFloat;

public class BSPositionData extends NiExtraData
{
	/*
	  <niobject name="BSPositionData" inherit="NiExtraData">
	    Fallout 4 Positional Data
	    <add name="Num Data" type="uint" />
	    <add name="Data" type="hfloat" arr1="Num Data" />
	</niobject>non-Javadoc)
	
	 */

	public int NumData;
	public float[] Data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumData = ByteConvert.readInt(stream);
		Data = new float[NumData];
		for (int i = 0; i < NumData; i++)
		{
			Data[i] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
		}

		return success;
	}
}