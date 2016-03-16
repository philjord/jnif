package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifKey;
import nif.enums.KeyType;

public class NiVisData extends NiObject
{
	/**
	 
	 <niobject name="NiVisData" abstract="0" inherit="NiObject">

	 Visibility data for a controller.
	 
	 <add name="Num Vis Keys" type="uint">The number of visibility keys that follow.</add>
	 <add name="Vis Keys" type="Key" arg="1" template="byte" arr1="Num Vis Keys">The visibility keys.</add>
	 </niobject>
	 
	 */

	public int numVisKeys;

	public NifKey[] visKeys;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numVisKeys = ByteConvert.readInt(stream);
		visKeys = new NifKey[numVisKeys];
		for (int i = 0; i < numVisKeys; i++)
		{
			KeyType type = new KeyType();
			type.type = 1;
			visKeys[i] = new NifKey(type, Byte.class, stream, nifVer);
		}

		return success;
	}
}