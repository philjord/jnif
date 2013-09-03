package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.enums.KeyType;

public class NifRotationKeyArray
{
	/**
	 <compound name="RotationKeyArray" istemplate="1">

	 Rotation key array.
	 
	 <add name="Num Keys" type="uint">Number of keys.</add>
	 <add name="Key Type" type="KeyType" cond="Num Keys != 0">Key type (1, 2, 3, or 4).</add>
	 <add name="Keys" type="QuatKey" arg="Key Type" template="TEMPLATE" arr1="Num Keys">The rotation keys.</add>
	 </compound>
	 */
	public int numKeys;

	public KeyType type;

	public NifQuatKey[] keys;

	public NifRotationKeyArray(InputStream stream) throws IOException
	{
		numKeys = ByteConvert.readInt(stream);
		type = new KeyType(stream);
		for (int i = 0; i < numKeys; i++)
		{
			keys[i] = new NifQuatKey(type, stream);
		}
	}
}
