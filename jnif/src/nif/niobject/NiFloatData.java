package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifKeyGroup;

public class NiFloatData extends NiObject
{
	/**
	 
	 <niobject name="NiFloatData" abstract="0" inherit="NiObject">

	 Possibly the 1D position along a 3D path.
	 
	 <add name="Data" type="KeyGroup" template="float">The keys.</add>
	 </niobject>
	 
	 */

	public NifKeyGroup data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		data = new NifKeyGroup(Float.class, stream, nifVer);

		return success;
	}
}