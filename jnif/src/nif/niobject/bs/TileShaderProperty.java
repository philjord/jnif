package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class TileShaderProperty extends BSShaderLightingProperty
{
	/**
	 
	 <niobject name="TileShaderProperty" abstract="0" inherit="BSShaderLightingProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node.
	 
	 <add name="File Name" type="SizedString">Texture file name</add>
	 </niobject>
	 */

	public String fileName;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		fileName = ByteConvert.readSizedString(stream);

		return success;
	}
}