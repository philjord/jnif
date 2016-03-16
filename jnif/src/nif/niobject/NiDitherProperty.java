package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;

public class NiDitherProperty extends NiProperty
{
	/**
	 <niobject name="NiDitherProperty" abstract="0" inherit="NiProperty">

	 Unknown.
	 
	 <add name="Flags" type="Flags">1's Bit: Enable dithering</add>
	 </niobject>
	 */

	public NifFlags flags;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);

		return success;
	}
}