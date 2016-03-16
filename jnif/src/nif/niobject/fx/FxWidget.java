package nif.niobject.fx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiNode;

public class FxWidget extends NiNode
{
	/**
	 
	 <niobject name="FxWidget" abstract="0" inherit="NiNode" ver1="10.0.1.0">

	 Firaxis-specific UI widgets?
	 
	 <add name="Unknown1" type="byte">Unknown.</add>
	 <add name="Unknown 292 Bytes" type="byte" arr1="292">Looks like 9 links and some string data.</add>
	 </niobject>	 
	 */

	public byte unknown1;

	public byte[] unknown292;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknown1 = ByteConvert.readByte(stream);

		unknown292 = ByteConvert.readBytes(292, stream);

		return success;
	}
}