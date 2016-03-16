package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifKey;
import nif.enums.KeyType;

public class NiTextKeyExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiTextKeyExtraData" abstract="0" inherit="NiExtraData">

	 Extra data, used to name different animation sequences.
	 
	 <add name="Unknown Int 1" type="uint" ver2="4.2.2.0">
	 Unknown.  Always equals zero in all official files.
	 </add>
	 <add name="Num Text Keys" type="uint">The number of text keys that follow.</add>
	 <add name="Text Keys" type="Key" arg="1" template="string" arr1="Num Text Keys">
	 List of textual notes and at which time they take effect. Used for designating the start and stop of animations and the triggering of sounds.
	 </add>
	 </niobject>
	 */
	public int unknownInt;

	public int numTextKeys;

	public NifKey[] textKeys;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			unknownInt = ByteConvert.readInt(stream);
		}
		numTextKeys = ByteConvert.readInt(stream);
		textKeys = new NifKey[numTextKeys];
		for (int i = 0; i < numTextKeys; i++)
		{
			KeyType type = new KeyType();
			type.type = 1;
			textKeys[i] = new NifKey(type, String.class, stream, nifVer);
		}
		return success;
	}
}