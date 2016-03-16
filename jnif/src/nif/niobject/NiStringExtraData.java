package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiStringExtraData extends NiExtraData
{
	/**
	 <niobject name="NiStringExtraData" abstract="0" inherit="NiExtraData">

	 Apparently commands for an optimizer instructing it to keep things it would normally discard.
	 Also refers to NiNode objects (through their name) in animation .kf files.
	 
	 <add name="Bytes Remaining" type="uint" ver2="4.2.2.0">
	 The number of bytes left in the record.  Equals the length of the following string + 4.
	 </add>
	 <add name="String Data" type="string">The string.</add>
	 </niobject>
	 
	 */
	public int bytesRemaining;

	public String stringData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			bytesRemaining = ByteConvert.readInt(stream);
		}
		stringData = ByteConvert.readIndexString(stream, nifVer);
		return success;
	}
}