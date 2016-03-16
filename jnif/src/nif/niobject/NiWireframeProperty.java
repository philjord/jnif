package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;

public class NiWireframeProperty extends NiProperty
{
	/**
	 <niobject name="NiWireframeProperty" abstract="0" inherit="NiProperty">

	 Unknown.
	 
	 <add name="Flags" type="Flags">

	 Property flags.
	 0 - Wireframe Mode Disabled
	 1 - Wireframe Mode Enabled
	 
	 </add>
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