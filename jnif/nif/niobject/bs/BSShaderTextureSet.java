package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiObject;

public class BSShaderTextureSet extends NiObject
{
	/**
	 
	 <niobject name="BSShaderTextureSet" abstract="0" inherit="NiObject" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node.
	 
	 <add name="Num Textures" type="int" default="6">Number of Textures</add>
	 <add name="Textures" type="SizedString" arr1="Num Textures">Textures</add>
	 </niobject>
	 */

	//NOTE see NiTexturingProperty for a list of the 6 texture 0 is base 1 is an _n type
	public int numTextures;

	public String[] textures;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numTextures = ByteConvert.readInt(stream);
		textures = ByteConvert.readSizedStrings(numTextures, stream);
		return success;
	}
}