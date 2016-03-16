package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifByteColor4;

public class NiPalette extends NiObject
{
	/**
	 
	 <niobject name="NiPalette" abstract="0" inherit="NiObject" ver1="4.2.2.0">

	 A color palette.
	 
	 <add name="Unknown Byte" type="byte">Unknown, Usually = 0.</add>
	 <add name="Num Entries" type="uint">The number of palette entries.  Always = 256.</add>
	 <add name="Palette" type="ByteColor4" arr1="256">The color palette.</add>
	 </niobject>
	 
	 */

	public byte unknownByte;

	public int numEntries;

	public NifByteColor4[] palette;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownByte = ByteConvert.readByte(stream);
		numEntries = ByteConvert.readInt(stream);

		palette = new NifByteColor4[256];
		for (int i = 0; i < 256; i++)
		{
			palette[i] = new NifByteColor4(stream);
		}

		return success;
	}
}