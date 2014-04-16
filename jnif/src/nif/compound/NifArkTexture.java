package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiTexturingProperty;

public class NifArkTexture
{
	/**
	 <compound name="ArkTexture">

	 A texture reference used by NiArkTextureExtraData.
	 
	 <add name="Texture Name" type="string"/>
	 <add name="Unknown Int 3" type="int"/>
	 <add name="Unknown Int 4" type="int"/>
	 <add name="Texturing Property" type="Ref" template="NiTexturingProperty"/>
	 <add name="Unknown Bytes" type="byte" arr1="9"/>
	 </compound>
	 */

	public String textureName;

	public int unknownInt3;

	public int unknownInt4;

	public NifRef texturingProperty;

	public byte[] unknownBytes;

	public NifArkTexture(InputStream stream, NifVer nifVer) throws IOException
	{
		textureName = ByteConvert.readIndexString(stream, nifVer);
		unknownInt3 = ByteConvert.readInt(stream);
		unknownInt4 = ByteConvert.readInt(stream);
		texturingProperty = new NifRef(NiTexturingProperty.class, stream);
		unknownBytes = ByteConvert.readBytes(9, stream);
	}
}
