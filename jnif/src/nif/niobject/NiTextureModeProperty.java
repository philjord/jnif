package nif.niobject;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiTextureModeProperty extends NiProperty
{
	/**
	 <niobject name="NiTextureModeProperty" abstract="0" inherit="NiProperty" ver1="3.0" ver2="3.1">

	 Unknown
	 
	 <add name="Unknown Short" type="short">Unknown. Either 210 or 194.</add>
	 <add name="PS2 L" type="short" default="0" ver1="3.1" ver2="10.2.0.0">0?</add>
	 <add name="PS2 K" type="short" default="-75" ver1="3.1" ver2="10.2.0.0">-75?</add>
	 </niobject>
	 */

	public short unknownShort;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort = ByteConvert.readShort(stream);
		return success;
	}
}