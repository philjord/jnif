package nif.niobject.controller;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.TexTransform;
import nif.enums.TexType;

public class NiTextureTransformController extends NiFloatInterpController
{
	/**
	 <niobject name="NiTextureTransformController" abstract="0" inherit="NiFloatInterpController" ver1="10.1.0.0">

	 Texture transformation controller.
	 
	 <add name="Unknown2" type="byte">Unknown.</add>
	 <add name="Texture Slot" type="TexType"> The target texture slot.</add>
	 <add name="Operation" type="TexTransform">
	 Determines how this controller animates the UV Coordinates.
	 </add>
	 <add name="Data" type="Ref" template="NiFloatData" ver2="10.1.0.0">Link to NiFloatData.</add>
	 </niobject>
	 
	 */

	public byte unknown2;

	public TexType textureSlot;

	public TexTransform operation;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknown2 = ByteConvert.readByte(stream);
		textureSlot = new TexType(stream);
		operation = new TexTransform(stream);

		return success;
	}
}