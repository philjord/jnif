package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifKey.NifKeyByte;
import nif.compound.NifKeyGroup.NifKeyGroupFloat;
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

	public NifKeyGroupFloat floatKeys;

	public int numVisibiltyKeys;

	public NifKeyByte[] visibilityKeys;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		floatKeys = new NifKeyGroupFloat(stream, nifVer);
		numVisibiltyKeys = ByteConvert.readInt(stream);
		visibilityKeys = new NifKeyByte[numVisibiltyKeys];
		for (int i = 0; i < numVisibiltyKeys; i++)
		{
			KeyType type = new KeyType();
			type.type = 1;
			visibilityKeys[i] = new NifKeyByte(type, stream, nifVer);
		}

		return success;
	}
}