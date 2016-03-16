package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiObject;
import nif.niobject.NiPixelData;

public class NifTexSource
{
	/**
	 <compound name="TexSource">

	 A texture source.
	 
	 <add name="Use External" type="byte">Is the texture external?</add>
	 <add name="File Name" type="FilePath" cond="Use External == 1">

	 The external texture file name.

	 Note: all original morrowind nifs use name.ext only for addressing the textures, but most mods use something like 
	 textures/[subdir/]name.ext. This is due to a feature in Morrowind resource manager: it loads name.ext, textures/name.ext and 
	 textures/subdir/name.ext but NOT subdir/name.ext.
	 
	 </add>
	 <add name="Unknown Link" type="Ref" template="NiObject" cond="Use External == 1" ver1="10.1.0.0">Unknown.</add>
	 <add name="Unknown Byte" type="byte" cond="Use External == 0" ver2="10.0.1.0">Unknown.</add>
	 <add name="File Name" type="FilePath" cond="Use External == 0" ver1="10.1.0.0">
	 The original source filename of the image embedded by the referred NiPixelData object.
	 </add>
	 <add name="Pixel Data" type="Ref" template="NiPixelData" cond="Use External == 0">Pixel data object index.</add>
	 </compound>
	 */

	public boolean useExternal;

	public NifFilePath fileName;

	public NifRef unknownLink;

	public NifRef pixelData;

	public NifTexSource(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		useExternal = ByteConvert.readBool(stream, nifVer);
		if (useExternal)
		{
			fileName = new NifFilePath(stream, nifVer);
			unknownLink = new NifRef(NiObject.class, stream);
		}
		else
		{
			pixelData = new NifRef(NiPixelData.class, stream);
		}

	}
}
