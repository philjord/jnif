package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class SkyShaderProperty extends BSShaderProperty
{
	/**
	 
	 <niobject name="SkyShaderProperty" abstract="0" inherit="BSShaderProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific node? Found in Fallout3
	 
	 <add name="Unknown Int 4" type="int">Unknown</add>
	 <add name="File Name" type="SizedString">The texture.</add>
	 <add name="Unknown Int 5" type="int" default="3">Unknown</add>
	 </niobject>
	 */

	public int unknownInt4;

	public String fileName;

	public int unknownInt5;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt4 = ByteConvert.readInt(stream);
		fileName = ByteConvert.readSizedString(stream);
		unknownInt5 = ByteConvert.readInt(stream);

		return success;
	}
}