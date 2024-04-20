package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.enums.TexClampMode;
import nif.enums.TexFilterMode;
import nif.niobject.NiImage;

public class NifMultiTextureElement
{
	/**
	 <compound name="MultiTextureElement">
	 <add name="Has Image" type="bool">Looks like a memory address, so probably a bool.</add>
	 <add name="Image" type="Ref" template="NiImage" cond="Has Image">Link to the texture image.</add>
	 <add name="Clamp?" type="TexClampMode" cond="Has Image" default="3">May be texture clamp mode.</add>
	 <add name="Filter?" type="TexFilterMode" cond="Has Image" default="2">May be texture filter mode.</add>
	 <add name="UV Set?" type="uint" cond="Has Image" default="1">
	 This may be the UV set counting from 1 instead of zero.
	 </add>
	 <add name="PS2 L" type="short" cond="Has Image" default="0" ver1="3.03" ver2="10.2.0.0">0?</add>
	 <add name="PS2 K" type="short" cond="Has Image" default="-75" ver1="3.03" ver2="10.2.0.0">-75?</add>
	 <add name="Unknown Short 3" type="short" cond="Has Image" default="0" ver1="3.03">Unknown.  Usually 0 but sometimes 257</add>
	 </compound>		
	 
	 */

	public boolean hasImage;

	public NifRef image;

	public TexClampMode clamp = TexClampMode.WRAP_S_WRAP_T;

	public TexFilterMode filter;

	public int uvSet;

	public short unknownShort3;

	public NifMultiTextureElement(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		hasImage = ByteConvert.readBool(stream, nifVer);
		if (hasImage)
		{
			image = new NifRef(NiImage.class, stream);
			clamp = TexClampMode.load(stream);
			filter = new TexFilterMode(stream);
			uvSet = ByteConvert.readInt(stream);
			unknownShort3 = ByteConvert.readShort(stream);
		}
	}
}
