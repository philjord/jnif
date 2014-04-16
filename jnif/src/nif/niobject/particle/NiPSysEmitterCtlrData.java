package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifKey;
import nif.compound.NifKeyGroup;
import nif.enums.KeyType;
import nif.niobject.NiObject;

public class NiPSysEmitterCtlrData extends NiObject
{
	/**
	 
	 <niobject name="NiPSysEmitterCtlrData" abstract="0" inherit="NiObject" ver1="10.1.0.0">

	 Particle system emitter controller data.
	 
	 <add name="Float Keys?" type="KeyGroup" template="float">Unknown.</add>
	 <add name="Num Visibility Keys?" type="uint">Number of keys.</add>
	 <add name="Visibility Keys?" type="Key" arg="1" template="byte" arr1="Num Visibility Keys?">Unknown.</add>
	 </niobject>
	 
	 */

	public NifKeyGroup floatKeys;

	public int numVisibiltyKeys;

	public NifKey[] visibilityKeys;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		floatKeys = new NifKeyGroup(Float.class, stream, nifVer);
		numVisibiltyKeys = ByteConvert.readInt(stream);
		visibilityKeys = new NifKey[numVisibiltyKeys];
		for (int i = 0; i < numVisibiltyKeys; i++)
		{
			KeyType type = new KeyType();
			type.type = 1;
			visibilityKeys[i] = new NifKey(type, Byte.class, stream, nifVer);
		}

		return success;
	}
}