package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.compound.NifColor3;

public class NiFogProperty extends NiProperty
{
	/**
	 <niobject name="NiFogProperty" abstract="0" inherit="NiProperty" ver1="10.1.0.0">

	 Describes... fog?
	 
	 <add name="Flags" type="Flags">

	 1's bit: Enables Fog
	 2's bit: Sets Fog Function to FOG_RANGE_SQ
	 4's bit: Sets Fog Function to FOG_VERTEX_ALPHA

	 If 2's and 4's bit are not set, but fog is enabled, Fog function is FOG_Z_LINEAR.
	 
	 </add>
	 <add name="Fog Depth" type="float">The thickness of the fog?  Default is 1.0</add>
	 <add name="Fog Color" type="Color3">The color of the fog.</add>
	 </niobject>
	 */

	public NifFlags flags;

	public float fogDepth;

	public NifColor3 fogColor;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		fogDepth = ByteConvert.readFloat(stream);
		fogColor = new NifColor3(stream);

		return success;
	}
}