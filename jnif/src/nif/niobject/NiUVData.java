package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifKeyGroup.NifKeyGroupFloat;

public class NiUVData extends NiObject
{
	/**
	 <niobject name="NiUVData" abstract="0" inherit="NiObject">

	 Texture coordinate data.
	 
	 <add name="UV Groups" type="KeyGroup" template="float" arr1="4">

	 Four UV data groups.  Perhaps the first two control x and y.
	 The existence of the second two is a guess - there are always two zero values following the first two in all official files.
	 
	 </add>
	 </niobject>
	 */
	public NifKeyGroupFloat[] uVGroups;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		uVGroups = new NifKeyGroupFloat[4];
		for (int i = 0; i < 4; i++)
		{
			uVGroups[i] = new NifKeyGroupFloat(stream, nifVer);
		}

		return success;
	}
}