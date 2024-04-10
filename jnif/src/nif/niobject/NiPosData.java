package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifKeyGroup.NifKeyGroupNifVector3;

public class NiPosData extends NiObject
{
	/**
	 
	 <niobject name="NiPosData" abstract="0" inherit="NiObject">

	 Position data.
	 
	 <add name="Data" type="KeyGroup" template="Vector3">The position keys.</add>
	 </niobject>
	 
	 */

	public NifKeyGroupNifVector3 data;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifKeyGroupNifVector3(stream, nifVer);
		return success;
	}
}