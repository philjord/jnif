package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifStringPalette
{
	/**
	 <compound name="StringPalette" nifskopetype="stringpalette">

	 A list of \\0 terminated strings.
	 
	 <add name="Palette" type="SizedString">A bunch of 0x00 seperated strings.</add>
	 <add name="Length" type="uint">Length of the palette string is repeated here.</add>
	 </compound>
	 */

	public String palette;

	public int length;

	//public String[] strings;

	public NifStringPalette(InputStream stream) throws IOException
	{
		palette = ByteConvert.readSizedString(stream);
		length = ByteConvert.readInt(stream);
		//strings = palette.split("" + Character.toChars(0x00)[0]);
	}
}
