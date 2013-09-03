package nif.niobject;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.compound.NifMultiTextureElement;

public class NiMultiTextureProperty extends NiProperty
{
	/**
	 <niobject name="NiMultiTextureProperty" abstract="0" inherit="NiProperty" ver1="3.1" ver2="3.1">

	 (note: not quite complete yet... but already reads most of the DAoC ones)
	 
	 <add name="Flags" type="Flags">Property flags.</add>
	 <add name="Unknown Int" type="uint">
	 Unknown. Always 5 for DAoC files, and always 6 for Bridge Commander.  Seems to have nothing to do with the number of Texture Element slots that follow.
	 </add>
	 <add name="Texture Elements" type="MultiTextureElement" arr1="5">
	 Describes the various textures used by this mutli-texture property.  Each slot probably has special meaning like thoes in NiTexturingProperty.
	 </add>
	 </niobject>
	 */

	public NifFlags flags;

	public int unknownInt;

	public NifMultiTextureElement[] textureElements;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		unknownInt = ByteConvert.readInt(stream);

		textureElements = new NifMultiTextureElement[5];
		for (int i = 0; i < 5; i++)
		{
			textureElements[i] = new NifMultiTextureElement(stream, nifVer);
		}

		return success;
	}
}