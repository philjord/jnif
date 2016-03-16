package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.KeyType;

public class NifKeyGroup
{
	/**
	 KeyGroup <T> :: (None)
	 
	 T can be
	 byte
	 float
	 NPColor4
	 NPVector3
	 NPString
	 
	 <compound name="KeyGroup" istemplate="1">

	 Array of vector keys (anything that can be interpolated, except rotations).
	 
	 <add name="Num Keys" type="uint">Number of keys in the array.</add>
	 <add name="Interpolation" type="KeyType" cond="Num Keys != 0">The key type.</add>
	 <add name="Keys" type="Key" arg="Interpolation" template="TEMPLATE" arr1="Num Keys">The keys.</add>
	 </compound>
	 */

	public int numKeys;

	public KeyType interpolation;

	public NifKey[] keys;

	public Class<?> T;

	public NifKeyGroup(Class<?> T, ByteBuffer stream, NifVer nifVer) throws IOException
	{
		this.T = T;
		numKeys = ByteConvert.readInt(stream);
		if (numKeys != 0)
		{
			interpolation = new KeyType(stream);
			keys = new NifKey[numKeys];
			for (int i = 0; i < numKeys; i++)
			{
				keys[i] = new NifKey(interpolation, T, stream, nifVer);
			}
		}
	}
}
