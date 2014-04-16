package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifFilePath;
import nif.enums.AlphaFormat;
import nif.enums.MipMapFormat;
import nif.enums.PixelLayout;

public class NiSourceTexture extends NiTexture
{

	/**
	 <niobject name="NiSourceTexture" abstract="0" inherit="NiTexture">

	 Describes texture source and properties.
	 
	 <add name="Use External" type="byte" default="1">Is the texture external?</add>
	 <add name="File Name" type="FilePath" cond="Use External == 1">The external texture file name.</add>
	 <add name="Unknown Link" type="Ref" template="NiObject" cond="Use External == 1" ver1="10.1.0.0">Unknown.</add>
	 <add name="Unknown Byte" type="byte" cond="Use External == 0" ver2="10.0.1.0">Unknown.</add>
	 <add name="File Name" type="FilePath" cond="Use External == 0" ver1="10.1.0.0">
	 The original source filename of the image embedded by the referred NiPixelData object.
	 </add>
	 <add name="Pixel Data" type="Ref" template="ATextureRenderData" cond="Use External == 0">
	 Pixel data object index. NiPixelData or NiPersistentSrcTextureRendererData
	 </add>
	 <add name="Pixel Layout" type="PixelLayout" default="5">Specifies the way the image will be stored.</add>
	 <add name="Use Mipmaps" type="MipMapFormat" default="2">Specifies whether mip maps are used.</add>
	 <add name="Alpha Format" type="AlphaFormat" default="3">
	 Note: the NiTriShape linked to this object must have a NiAlphaProperty in its list of properties to enable material and/or texture transparency.
	 </add>
	 <add name="Is Static" type="byte" default="1">Is Static?</add>
	 <add name="Direct Render" type="bool" default="1" ver1="10.1.0.106">Load direct to renderer</add>
	 <add name="Persist Render Data" type="bool" default="0" ver1="20.2.0.7">Render data is persistant</add>
	 </niobject>
	 */

	public byte useExternal = 1;

	public NifFilePath fileName;

	public NifRef unknownLink;

	public NifRef pixelData;

	public PixelLayout pixelLayout;

	public MipMapFormat useMipmaps;

	public AlphaFormat alphaFormat;

	public byte isStatic;

	public boolean directRender;

	public boolean persistRenderData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		useExternal = ByteConvert.readByte(stream);
		if (useExternal == 1)
		{
			fileName = new NifFilePath(stream, nifVer);
			unknownLink = new NifRef(NiObject.class, stream);
		}
		else
		{
			fileName = new NifFilePath(stream, nifVer);
			pixelData = new NifRef(ATextureRenderData.class, stream);
		}

		pixelLayout = new PixelLayout(stream);
		useMipmaps = new MipMapFormat(stream);
		alphaFormat = new AlphaFormat(stream);
		isStatic = ByteConvert.readByte(stream);
		directRender = ByteConvert.readBool(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			persistRenderData = ByteConvert.readBool(stream, nifVer);
		}

		return success;
	}
}