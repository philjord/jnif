package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifColor4;

public class NiColorExtraData extends NiExtraData
{
	/**
	 <niobject name="NiColorExtraData" abstract="0" inherit="NiExtraData" ver1="20.0.0.4">

	 Unknown.
	 
	 <add name="Data" type="Color4">RGBA Color?</add>
	 </niobject>
	 
	 */

	public NifColor4 data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifColor4(stream);
		return success;
	}
}