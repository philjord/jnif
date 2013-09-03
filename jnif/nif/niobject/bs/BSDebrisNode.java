package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiNode;

public class BSDebrisNode extends NiNode
{
	/**
	 
	 <niobject name="BSDebrisNode" inherit="NiNode" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific node.
	 
	 <add name="Unknown byte 1" type="byte">Unknown</add>
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 </niobject>
	 
	 */
	public byte unknownByte1;

	public short unknownShort1b;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownByte1 = ByteConvert.readByte(stream);
		unknownShort1b = ByteConvert.readShort(stream);

		return success;
	}
}