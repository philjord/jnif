package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiPSysAgeDeathModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysAgeDeathModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Unknown particle modifier.
	 
	 <add name="Spawn on Death" type="bool">Unknown.</add>
	 <add name="Spawn Modifier" type="Ref" template="NiPSysSpawnModifier">Link to NiPSysSpawnModifier object?</add>
	 </niobject>
	 */

	public boolean spawnOnDeath;

	public NifRef spawnModifier;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		spawnOnDeath = ByteConvert.readBool(stream, nifVer);
		spawnModifier = new NifRef(NiPSysSpawnModifier.class, stream);

		return success;
	}
}
