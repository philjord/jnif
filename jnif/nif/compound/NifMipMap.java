package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifMipMap
{
	/**
	 * <compound name="MipMap">

	 Description of a MipMap within a NiPixelData object.
	 
	 <add name="Width" type="uint">Width of the mipmap image.</add>
	 <add name="Height" type="uint">Height of the mipmap image.</add>
	 <add name="Offset" type="uint">Offset into the pixel data array where this mipmap starts.
	 </add>
	 </compound>
	 */

	public int width;

	public int height;

	public int offset;

	public NifMipMap(InputStream stream) throws IOException
	{
		width = ByteConvert.readInt(stream);
		height = ByteConvert.readInt(stream);
		offset = ByteConvert.readInt(stream);
	}
}
