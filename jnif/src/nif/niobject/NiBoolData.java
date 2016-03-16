package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifKeyGroup;

public class NiBoolData extends NiObject
{
	/**
	 
	 <niobject name="NiBoolData" abstract="0" inherit="NiObject" ver1="10.2.0.0">

	 Timed boolean data.
	 
	 <add name="Data" type="KeyGroup" template="byte">The boolean keys.</add>
	 </niobject>
	 
	 */

	public NifKeyGroup data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		data = new NifKeyGroup(Byte.class, stream, nifVer);

		return success;
	}
}