package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifShaderTexDesc
{
	/**
	 <compound name="ShaderTexDesc" ver1="10.0.1.0">

	 An extended texture description for shader textures.
	 
	 <add name="Is Used" type="bool">Is it used?</add>
	 <add name="Texture Data" type="TexDesc" cond="Is Used != 0">The texture data.</add>
	 <add name="Map Index" type="uint" cond="Is Used != 0">Map Index</add>
	 </compound>
	 */

	public boolean isUsed;

	public NifTexDesc textureData;

	public int mapIndex;

	public NifShaderTexDesc(InputStream stream, NifVer nifVer) throws IOException
	{
		isUsed = ByteConvert.readBool(stream, nifVer);
		if (isUsed)
		{
			textureData = new NifTexDesc(stream, nifVer);
			mapIndex = ByteConvert.readInt(stream);
		}
	}
}
