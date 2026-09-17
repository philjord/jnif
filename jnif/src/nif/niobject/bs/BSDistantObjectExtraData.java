package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSDistantObjectExtraData extends NiExtraData
{
	/**
	    <niobject name="BSDistantObjectExtraData" inherit="NiExtraData" module="BSMain" versions="#F76#">
	   Distant Object flags.
	   <field name="Distant Object Flags" type="uint" />
		</niobject>
	*/
	
	public int DistantObjectFlags;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		DistantObjectFlags = ByteConvert.readInt(stream);
		return success;
	}
}
