package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifKeyGroup.NifKeyGroupNifColor4;

public class NiColorData extends NiObject
{
	/**
	 
	 <niobject name="NiColorData" abstract="0" inherit="NiObject">

	 Color data for material color controller.
	 
	 <add name="Data" type="KeyGroup" template="Color4">The color keys.</add>
	 </niobject>
	 */

	public NifKeyGroupNifColor4 data;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		data = new NifKeyGroupNifColor4(stream, nifVer);

		return success;
	}
}