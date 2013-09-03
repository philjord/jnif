package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysTurbulenceFieldModifier extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysTurbulenceFieldModifier" inherit="NiPSysFieldModifier">

	 Particle system modifier, used for controlling the particle velocity in drag space warp.
	 
	 <add name="Unknown Float 2" type="float">Unknown</add>
	 </niobject>
	 */

	public float unknownFloat2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloat2 = ByteConvert.readFloat(stream);

		return success;
	}
}
