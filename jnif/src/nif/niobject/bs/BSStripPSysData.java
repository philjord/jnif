package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysData;

public class BSStripPSysData extends NiPSysData
{
	/**
	 
	 <niobject name="BSStripPSysData" inherit="NiPSysData" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific (mesh?) Particle System Data.
	 
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 <add name="Unknown byte 1" type="byte">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Int 3" type="int">Unknown</add>
	 </niobject>
	 */

	public short unknownShort1b;

	public byte unknownByte1;

	public int unknownInt1b;

	public int unknownInt2b;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownShort1b = ByteConvert.readShort(stream);
		unknownByte1 = ByteConvert.readByte(stream);
		unknownInt1b = ByteConvert.readInt(stream);
		unknownInt2b = ByteConvert.readInt(stream);

		return success;
	}
}