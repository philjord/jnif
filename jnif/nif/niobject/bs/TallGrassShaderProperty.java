package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class TallGrassShaderProperty extends BSShaderProperty
{
	/**
	 
	 <niobject name="TallGrassShaderProperty" abstract="0" inherit="BSShaderProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node.
	 
	 <add name="File Name" type="SizedString">Texture file name</add>
	 </niobject>
	 */

	public String fileName;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		fileName = ByteConvert.readSizedString(stream);

		return success;
	}
}