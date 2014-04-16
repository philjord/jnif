package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifFlags;

public class NiShadeProperty extends NiProperty
{
	/**
	 <niobject name="NiShadeProperty" abstract="0" inherit="NiProperty">

	 Determines whether flat shading or smooth shading is used on a shape.
	 
	 <add name="Flags" type="Flags">

	 1's Bit:  Enable smooth phong shading on this shape.

	 If 1's bit is not set, hard-edged flat shading will be used on this shape.
	 
	 </add>
	 </niobject>
	 */

	public NifFlags flags;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);

		return success;
	}
}