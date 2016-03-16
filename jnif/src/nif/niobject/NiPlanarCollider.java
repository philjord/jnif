package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiParticleModifier;

public class NiPlanarCollider extends NiParticleModifier
{
	/**
	 <niobject name="NiPlanarCollider" abstract="0" inherit="NiParticleModifier">

	 Unknown.
	 
	 <add name="Unknown Short" type="ushort" ver1="10.0.1.0">Usually 0?</add>
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown Float 2" type="float">Unknown.</add>
	 <add name="Unknown Short 2" type="ushort" ver1="4.2.2.0" ver2="4.2.2.0">Unknown.</add>
	 <add name="Unknown Float 3" type="float">Unknown.</add>
	 <add name="Unknown Float 4" type="float">Unknown.</add>
	 <add name="Unknown Float 5" type="float">Unknown.</add>
	 <add name="Unknown Float 6" type="float">Unknown.</add>
	 <add name="Unknown Float 7" type="float">Unknown.</add>
	 <add name="Unknown Float 8" type="float">Unknown.</add>
	 <add name="Unknown Float 9" type="float">Unknown.</add>
	 <add name="Unknown Float 10" type="float">Unknown.</add>
	 <add name="Unknown Float 11" type="float">Unknown.</add>
	 <add name="Unknown Float 12" type="float">Unknown.</add>
	 <add name="Unknown Float 13" type="float">Unknown.</add>
	 <add name="Unknown Float 14" type="float">Unknown.</add>
	 <add name="Unknown Float 15" type="float">Unknown.</add>
	 <add name="Unknown Float 16" type="float">Unknown.</add>
	 </niobject>		
	 */

	public float unknownFloat1;

	public float unknownFloat2;

	public float unknownFloat3;

	public float unknownFloat4;

	public float unknownFloat5;

	public float unknownFloat6;

	public float unknownFloat7;

	public float unknownFloat8;

	public float unknownFloat9;

	public float unknownFloat10;

	public float unknownFloat11;

	public float unknownFloat12;

	public float unknownFloat13;

	public float unknownFloat14;

	public float unknownFloat15;

	public float unknownFloat16;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownFloat4 = ByteConvert.readFloat(stream);
		unknownFloat5 = ByteConvert.readFloat(stream);
		unknownFloat6 = ByteConvert.readFloat(stream);
		unknownFloat7 = ByteConvert.readFloat(stream);
		unknownFloat8 = ByteConvert.readFloat(stream);
		unknownFloat9 = ByteConvert.readFloat(stream);
		unknownFloat10 = ByteConvert.readFloat(stream);
		unknownFloat11 = ByteConvert.readFloat(stream);
		unknownFloat12 = ByteConvert.readFloat(stream);
		unknownFloat13 = ByteConvert.readFloat(stream);
		unknownFloat14 = ByteConvert.readFloat(stream);
		unknownFloat15 = ByteConvert.readFloat(stream);
		unknownFloat16 = ByteConvert.readFloat(stream);

		return success;
	}
}