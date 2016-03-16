package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifColor4;
import nif.compound.NifKeyGroup;

public class NiColorData extends NiObject
{
	/**
	 
	 <niobject name="NiColorData" abstract="0" inherit="NiObject">

	 Color data for material color controller.
	 
	 <add name="Data" type="KeyGroup" template="Color4">The color keys.</add>
	 </niobject>
	 */

	public NifKeyGroup data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		data = new NifKeyGroup(NifColor4.class, stream, nifVer);

		return success;
	}
}