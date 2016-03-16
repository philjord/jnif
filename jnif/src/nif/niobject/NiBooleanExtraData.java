package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiBooleanExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiBooleanExtraData" abstract="0" inherit="NiExtraData" ver1="10.0.1.0">

	 Boolean extra data.
	 
	 <add name="Boolean Data" type="byte">The boolean extra data value.</add>
	 </niobject>
	 
	 */

	public byte booleanData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		booleanData = ByteConvert.readByte(stream);
		return success;
	}
}