package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.NiIntegerExtraData;

public class BSXFlags extends NiIntegerExtraData
{
	/**
	 
	 <niobject name="BSXFlags" abstract="0" inherit="NiIntegerExtraData" ver1="20.0.0.5">

	 Controls animation and collision.  Integer holds flags:
	 Bit 0 : enable animation
	 Bit 1 : enable collision
	 Bit 2 : is skeleton nif?
	 Bit 3 : toggable? unknown, set to 1 on oblivion signs
	 Bit 4 : FlameNodes present
	 Bit 5 : EditorMarkers present
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}