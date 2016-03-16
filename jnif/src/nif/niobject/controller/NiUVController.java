package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiUVData;

public class NiUVController extends NiTimeController
{
	/**
	 <niobject name="NiUVController" abstract="0" inherit="NiTimeController">

	 Time controller for texture coordinates.
	 
	 <add name="Unknown Short" type="ushort">Always 0?</add>
	 <add name="Data" type="Ref" template="NiUVData">Texture coordinate controller data index.</add>
	 </niobject>
	 */

	public short unknownShort;

	public NifRef data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort = ByteConvert.readShort(stream);

		data = new NifRef(NiUVData.class, stream);

		return success;
	}
}