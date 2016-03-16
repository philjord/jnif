package nif.niobject;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;

public class NiSpecularProperty extends NiProperty
{
	/**
	 <niobject name="NiSpecularProperty" abstract="0" inherit="NiProperty">

	 Gives specularity to a shape. Flags 0x0001.
	 
	 <add name="Flags" type="Flags">1's Bit = Enable specular lighting on this shape.</add>
	 </niobject>
	 */

	public NifFlags flags;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		return success;
	}
}