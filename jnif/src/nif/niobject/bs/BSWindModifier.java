package nif.niobject.bs;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSWindModifier extends NiPSysModifier
{
	/**
	 <niobject name="BSWindModifier" abstract="0" inherit="NiPSysModifier" ver1="20.0.0.5">

	 Particle Modifier that uses the wind value from the gamedata to alter the path of particles.
	 
	 <add name="Strength" type="float">The amount of force wind will have on particles.</add>
	 </niobject>
	 */

	public float strength;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		strength = ByteConvert.readFloat(stream);

		return success;
	}
}
