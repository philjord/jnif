package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifKeyGroup;
import nif.compound.NifVector3;

public class NiPosData extends NiObject
{
	/**
	 
	 <niobject name="NiPosData" abstract="0" inherit="NiObject">

	 Position data.
	 
	 <add name="Data" type="KeyGroup" template="Vector3">The position keys.</add>
	 </niobject>
	 
	 */

	public NifKeyGroup data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifKeyGroup(NifVector3.class, stream, nifVer);
		return success;
	}
}