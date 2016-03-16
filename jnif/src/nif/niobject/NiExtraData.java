package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.bs.BSbhkNPObject;

public class NiExtraData extends NiObject
{
	/**
	 <niobject name="NiExtraData" abstract="1" inherit="NiObject">
	
	 A generic extra data object.
	 
	 <add name="Name" type="string" ver1="10.0.1.0">Name of this object.</add>
	 <add name="Next Extra Data" type="Ref" template="NiExtraData" ver2="4.2.2.0">Block number of the next extra data object.</add>
	 </niobject>
	 
	 */

	public String name;

	public NifRef NextExtraData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0 && !(this instanceof BSbhkNPObject))
		{
			name = ByteConvert.readIndexString(stream, nifVer);
		}
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			NextExtraData = new NifRef(NiExtraData.class, stream);
		}
		return success;
	}
}