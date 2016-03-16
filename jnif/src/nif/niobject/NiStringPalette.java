package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifStringPalette;

public class NiStringPalette extends NiObject
{
	/**
	 
	 <niobject name="NiStringPalette" abstract="0" inherit="NiObject" ver1="10.2.0.0">

	 List of 0x00-seperated strings, which are names of controlled objects and controller types. Used in .kf files in conjunction with NiControllerSequence.
	 
	 <add name="Palette" type="StringPalette">A bunch of 0x00 seperated strings.</add>
	 </niobject>
	 
	 */

	public NifStringPalette palette;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		palette = new NifStringPalette(stream);
		return success;
	}
}