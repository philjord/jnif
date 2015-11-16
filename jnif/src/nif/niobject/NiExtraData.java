package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

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

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
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