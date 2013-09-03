package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifChannelData;
import nif.compound.NifMipMap;
import nif.enums.PixelFormat;

public abstract class ATextureRenderData extends NiObject
{
	/**
	 
	 <niobject name="ATextureRenderData" abstract="1" inherit="NiObject">
	 <add name="Pixel Format" type="PixelFormat">
	 The format of the pixels in this internally stored image.
	 </add>
	 <add name="Red Mask" type="uint" ver2="10.2.0.0">
	 0x000000ff (for 24bpp and 32bpp) or 0x00000000 (for 8bpp)
	 </add>
	 <add name="Green Mask" type="uint" ver2="10.2.0.0">
	 0x0000ff00 (for 24bpp and 32bpp) or 0x00000000 (for 8bpp)
	 </add>
	 <add name="Blue Mask" type="uint" ver2="10.2.0.0">
	 0x00ff0000 (for 24bpp and 32bpp) or 0x00000000 (for 8bpp)
	 </add>
	 <add name="Alpha Mask" type="uint" ver2="10.2.0.0">
	 0xff000000 (for 32bpp) or 0x00000000 (for 24bpp and 8bpp)
	 </add>
	 <add name="Bits Per Pixel" type="byte" ver2="10.2.0.0">Bits per pixel, 0 (?), 8, 24 or 32.</add>
	 <add name="Unknown 3 Bytes" type="byte" arr1="3" ver2="10.2.0.0">Zero?</add>
	 <add name="Unknown 8 Bytes" type="byte" arr1="8" ver2="10.2.0.0">

	 [96,8,130,0,0,65,0,0] if 24 bits per pixel
	 [129,8,130,32,0,65,12,0] if 32 bits per pixel
	 [34,0,0,0,0,0,0,0] if 8 bits per pixel
	 [4,0,0,0,0,0,0,0] if 0 (?) bits per pixel
	 
	 </add>
	 <add name="Unknown Int" type="uint" ver1="10.1.0.0" ver2="10.2.0.0">Seems to always be zero.</add>
	 <add name="Bits Per Pixel" type="byte" ver1="20.0.0.4">Bits per pixel, 0 (?), 8, 24 or 32.</add>
	 <add name="Unknown Int 2" type="int" ver1="20.0.0.4">Unknown.  Could be reference pointer.</add>
	 <add name="Unknown Int 3" type="uint" ver1="20.0.0.4">Seems to always be zero.</add>
	 <add name="Flags" type="byte" ver1="20.0.0.4">Flags</add>
	 <add name="Unknown Int 4" type="uint" ver1="20.0.0.4">Seems to always be zero.</add>
	 <add name="Unknown Byte 1" type="byte" ver1="20.3.0.6">Unknown.</add>
	 <add name="Channels" type="ChannelData" arr1="4" ver1="20.0.0.4">Channel Data</add>
	 <add name="Palette" type="Ref" template="NiPalette">Link to NiPalette, for 8-bit textures.</add>
	 <add name="Num Mipmaps" type="uint">Number of mipmaps in the texture.</add>
	 <add name="Bytes Per Pixel" type="uint">Bytes per pixel (Bits Per Pixel / 8).</add>
	 <add name="Mipmaps" type="MipMap" arr1="Num Mipmaps">Mipmap descriptions (width, height, offset).</add>
	 </niobject>
	 
	 */

	public PixelFormat pixelFormat;

	public byte bitsPerPixel;

	public int unknownInt2;

	public int unknownInt3;

	public byte flags;

	public int unknownInt4;

	public byte unknownByte1;

	public NifChannelData channels;

	public NifRef palette;

	public int numMipmaps;

	public int bytesPerPixel;

	public NifMipMap[] mipmaps;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		pixelFormat = new PixelFormat(stream);
		bitsPerPixel = ByteConvert.readByte(stream);

		unknownInt2 = ByteConvert.readInt(stream);

		unknownInt3 = ByteConvert.readInt(stream);

		flags = ByteConvert.readByte(stream);

		unknownInt4 = ByteConvert.readInt(stream);

		unknownByte1 = ByteConvert.readByte(stream);

		channels = new NifChannelData(stream);

		palette = new NifRef(NiPalette.class, stream);

		numMipmaps = ByteConvert.readInt(stream);

		bytesPerPixel = ByteConvert.readInt(stream);

		mipmaps = new NifMipMap[numMipmaps];
		for (int i = 0; i < numMipmaps; i++)
		{
			mipmaps[i] = new NifMipMap(stream);
		}

		return success;
	}
}