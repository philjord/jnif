package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysSpawnModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysSpawnModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Unknown particle modifier.
	 
	 <add name="Num Spawn Generations" type="ushort">Unknown.</add>
	 <add name="Percentage Spawned" type="float">Unknown.</add>
	 <add name="Min Num to Spawn" type="ushort">Unknown.</add>
	 <add name="Max Num to Spawn" type="ushort">Unknown.</add>
	 <add name="Spawn Speed Chaos" type="float">Unknown.</add>
	 <add name="Spawn Dir Chaos" type="float">Unknown.</add>
	 <add name="Life Span" type="float">Unknown.</add>
	 <add name="Life Span Variation" type="float">Unknown.</add>
	 </niobject>
	 */
	public short numSpawnGenerations;

	public float percentageSpawned;

	public short minNumtoSpawn;

	public short maxNumtoSpawn;

	public float spawnSpeedChaos;

	public float spawnDirChaos;

	public float lifeSpan;

	public float lifeSpanVariation;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numSpawnGenerations = ByteConvert.readShort(stream);
		percentageSpawned = ByteConvert.readFloat(stream);
		minNumtoSpawn = ByteConvert.readShort(stream);
		maxNumtoSpawn = ByteConvert.readShort(stream);
		spawnSpeedChaos = ByteConvert.readFloat(stream);
		spawnDirChaos = ByteConvert.readFloat(stream);
		lifeSpan = ByteConvert.readFloat(stream);
		lifeSpanVariation = ByteConvert.readFloat(stream);
		return success;
	}
}
