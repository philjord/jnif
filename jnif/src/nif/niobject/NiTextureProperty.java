package nif.niobject;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifRef;

public class NiTextureProperty extends NiProperty
{
	/**
	 <niobject name="NiTextureProperty" abstract="0" inherit="NiProperty" ver1="3.0" ver2="3.1">
	 <add name="Flags" type="Flags">Property flags.</add>
	 <add name="Image" type="Ref" template="NiImage">Link to the texture image.</add>
	 <add name="Unknown Int 1" type="uint" ver2="3.03">Unknown.  0?</add>
	 <add name="Unknown Int 2" type="uint" ver2="3.03">Unknown.  0xFFFFFFFF?</add>
	 </niobject>
	 */

	public NifFlags flags;

	public NifRef image;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		image = new NifRef(NiImage.class, stream);
		return success;
	}
}