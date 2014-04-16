package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifByteColor3;
import nif.compound.NifByteColor4;

public class NiRawImageData extends NiObject
{
	/**
	 <niobject name="NiRawImageData" abstract="0" inherit="NiObject" ver1="3.1" ver2="3.1">

	 Raw image data.
	 
	 <add name="Width" type="uint">Image width</add>
	 <add name="Height" type="uint">Image height</add>
	 <add name="Image Type" type="ImageType">The format of the raw image data.</add>
	 <add name="RGB Image Data" type="ByteColor3" arr1="Width" arr2="Height" cond="Image Type == 1">Image pixel data.</add>
	 <add name="RGBA Image Data" type="ByteColor4" arr1="Width" arr2="Height" cond="Image Type == 2">Image pixel data.</add>
	 </niobject>	
	 */

	public int width;

	public int height;

	public int imageType;

	public NifByteColor3[][] rGBImageData;

	public NifByteColor4[][] rGBAImageData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		width = ByteConvert.readInt(stream);
		height = ByteConvert.readInt(stream);

		imageType = ByteConvert.readInt(stream);
		if (imageType == 1)
		{
			rGBImageData = new NifByteColor3[width][height];
			for (int w = 0; w < width; w++)
			{
				for (int h = 0; h < height; h++)
				{
					rGBImageData[w][h] = new NifByteColor3(stream);
				}
			}

		}
		else if (imageType == 2)
		{
			rGBAImageData = new NifByteColor4[width][height];
			for (int w = 0; w < width; w++)
			{
				for (int h = 0; h < height; h++)
				{
					rGBAImageData[w][h] = new NifByteColor4(stream);
				}
			}
		}

		return success;
	}
}