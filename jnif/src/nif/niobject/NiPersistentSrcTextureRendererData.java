package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public abstract class NiPersistentSrcTextureRendererData extends ATextureRenderData
{
	/**
	 
	 <niobject name="NiPersistentSrcTextureRendererData" inherit="ATextureRenderData">
	 <add name="Num Pixels" type="uint">Unknown</add>
	 <add name="Unknown Int 6" type="uint">Unknown</add>
	 <add name="Num Faces" type="uint">Unknown</add>
	 <add name="Unknown Int 7" type="uint">Unknown</add>
	 <add name="Pixel Data" type="byte" nifskopetype="blob" arr1="Num Faces" arr2="Num Pixels">
	 Raw pixel data holding the mipmaps.  Mipmap zero is the full-size texture and they get smaller by half as the number increases.
	 </add>
	 </niobject>
	 
	 */

	public int numPixels;

	public int unknownInt6;

	public int numFaces;

	public int unknownInt7;

	public byte[][] pixelData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numPixels = ByteConvert.readInt(stream);

		unknownInt6 = ByteConvert.readInt(stream);

		numFaces = ByteConvert.readInt(stream);

		unknownInt7 = ByteConvert.readInt(stream);

		pixelData = new byte[numFaces][numPixels];
		for (int i = 0; i < numFaces; i++)
		{
			pixelData[i] = ByteConvert.readBytes(numPixels, stream);

		}

		return success;
	}
}