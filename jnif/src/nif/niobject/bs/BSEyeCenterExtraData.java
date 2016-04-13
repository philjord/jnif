package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSEyeCenterExtraData extends NiExtraData
{
	/*
	  <niobject name="BSEyeCenterExtraData" inherit="NiExtraData">
	    Fallout 4 Eye Center Data
	    <add name="Num Data" type="int" />
	    <add name="Data" type="float" arr1="Num Data" />
	</niobject>
	 */

	public int NumData;
	public float[] Data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumData = ByteConvert.readInt(stream);
		Data = ByteConvert.readFloats(NumData, stream);
		return success;
	}
}
