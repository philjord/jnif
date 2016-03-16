package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPixelData extends ATextureRenderData
{
	/**
	 
	 <niobject name="NiPixelData" abstract="0" inherit="ATextureRenderData">

	 A texture.
	 
	 <add name="Num Pixels" type="uint">Total number of pixels</add>
	 <add name="Num Faces" type="uint" ver1="20.0.0.4" default="1">Unknown</add>
	 <add name="Pixel Data" type="byte" nifskopetype="blob" arr1="Num Faces" arr2="Num Pixels" ver1="20.0.0.4">
	 Raw pixel data holding the mipmaps.  Mipmap zero is the full-size texture and they get smaller by half as the number increases.
	 </add>
	 <add name="Pixel Data" type="byte" nifskopetype="blob" arr1="1" arr2="Num Pixels" ver2="10.2.0.0">
	 Raw pixel data holding the mipmaps.  Mipmap zero is the full-size texture and they get smaller by half as the number increases.
	 </add>
	 </niobject>
	 */

	public int numPixels;

	public int numFaces;

	public byte[][] pixelData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numPixels = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4)
		{
			numFaces = ByteConvert.readInt(stream);

			pixelData = new byte[numFaces][numPixels];
			for (int i = 0; i < numFaces; i++)
			{
				pixelData[i] = ByteConvert.readBytes(numPixels, stream);
			}
		}

		if (nifVer.LOAD_VER <= NifVer.VER_10_2_0_0)
		{
			pixelData = new byte[1][numPixels];
			pixelData[0] = ByteConvert.readBytes(numPixels, stream);
		}

		return success;
	}
}