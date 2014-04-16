package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiParticleModifier;

public class NiSphericalCollider extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiSphericalCollider" abstract="0" inherit="NiParticleModifier" ver1="4.0.0.2">

	 Unknown.
	 
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown Short 1" type="ushort">Unknown.</add>
	 <add name="Unknown Float 2" type="float">Unknown.</add>
	 <add name="Unknown Short 2" type="ushort" ver2="4.2.0.2">Unknown.</add>
	 <add name="Unknown Float 3" type="float" ver1="4.2.1.0">Unknown.</add>
	 <add name="Unknown Float 4" type="float">Unknown.</add>
	 <add name="Unknown Float 5" type="float">Unknown.</add>
	 </niobject>
	 */

	public float unknownFloat1;

	public short unknownShort1;

	public float unknownFloat2;

	public float unknownFloat3;

	public float unknownFloat4;

	public float unknownFloat5;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownShort1 = ByteConvert.readShort(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownFloat4 = ByteConvert.readFloat(stream);
		unknownFloat5 = ByteConvert.readFloat(stream);

		return success;
	}
}