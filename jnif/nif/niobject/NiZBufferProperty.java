package nif.niobject;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifFlags;
import nif.enums.CompareMode;

public class NiZBufferProperty extends NiProperty
{
	/**
	 <niobject name="NiZBufferProperty" abstract="0" inherit="NiProperty">

	 This Property controls the Z buffer (OpenGL: depth buffer).
	 
	 <add name="Flags" type="Flags" default="3">

	 Bit 0 enables the z test
	 Bit 1 controls wether the Z buffer is read only (0) or read/write (1)
	 
	 </add>
	 <add name="Function" type="CompareMode" default="3" ver1="4.1.0.12" ver2="20.0.0.5">

	 Z-Test function (see: glDepthFunc). In Flags from 20.1.0.3 on.
	 
	 </add>
	 </niobject>
	 */

	public NifFlags flags;

	public CompareMode function;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			function = new CompareMode(stream);
		}
		return success;
	}
}