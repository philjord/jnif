package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifKeyGroup.NifKeyGroupFloat;

public class NiFloatData extends NiObject
{
	/**
	 
	 <niobject name="NiFloatData" abstract="0" inherit="NiObject">

	 Possibly the 1D position along a 3D path.
	 
	 <add name="Data" type="KeyGroup" template="float">The keys.</add>
	 </niobject>
	 
	 */

	public NifKeyGroupFloat data;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		data = new NifKeyGroupFloat(stream, nifVer);

		return success;
	}
}