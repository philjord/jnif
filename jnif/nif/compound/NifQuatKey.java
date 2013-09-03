package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.enums.KeyType;

public class NifQuatKey
{
	/**
	 T is always quaternions??????????
	 
	 
	 <compound name="QuatKey" niflibtype="Key" istemplate="1">

	 A special version of the key type used for quaternions.  Never has tangents.
	 
	 <add name="Time" type="float" ver2="10.1.0.0">Time the key applies.</add>
	 <add name="Time" type="float" cond="ARG != 4" ver1="10.1.0.106">Time the key applies.</add>
	 <add name="Value" type="TEMPLATE" cond="ARG != 4">Value of the key.</add>
	 <add name="TBC" type="TBC" cond="ARG == 3">The TBC of the key.</add>
	 </compound>
	 */

	public KeyType type;

	public float time;

	public NifQuaternion value;

	public NifTBC tBC;

	public NifQuatKey(KeyType type, InputStream stream) throws IOException
	{
		this.type = type;
		if (type.type != 4)
		{
			time = ByteConvert.readFloat(stream);
			value = new NifQuaternion(stream);
		}
		if (type.type == 3)
		{
			tBC = new NifTBC(stream);
		}
	}
}
