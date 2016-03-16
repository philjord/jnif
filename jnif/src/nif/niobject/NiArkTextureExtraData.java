package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifArkTexture;

public class NiArkTextureExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiArkTextureExtraData" inherit="NiExtraData">

	 Unknown node.
	 
	 <add name="Unknown Ints 1" type="int" arr1="2"/>
	 <add name="Unknown Byte" type="byte"/>
	 <add name="Unknown Int 2" type="int" ver2="4.1.0.12"/>
	 <add name="Num Textures" type="int"/>
	 <add name="Textures" type="ArkTexture" arr1="Num Textures"/>
	 </niobject>
	 */

	public int[] unknownInts1;

	public byte unknownByte;

	public int numMaterials;

	public NifArkTexture[] textures;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownInts1 = ByteConvert.readInts(2, stream);
		unknownByte = ByteConvert.readByte(stream);
		numMaterials = ByteConvert.readInt(stream);
		textures = new NifArkTexture[numMaterials];
		for (int i = 0; i < numMaterials; i++)
		{
			textures[i] = new NifArkTexture(stream, nifVer);
		}

		return success;
	}
}