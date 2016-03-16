package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysSphereEmitter extends NiPSysVolumeEmitter
{
	/**
	 <niobject name="NiPSysSphereEmitter" abstract="0" inherit="NiPSysVolumeEmitter" ver1="20.0.0.4">

	 Particle emitter that uses points within a sphere shape to emit from.
	 
	 <add name="Radius" type="float">The radius of the sphere shape</add>
	 </niobject>
	 */

	public float radius;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		radius = ByteConvert.readFloat(stream);

		return success;
	}
}
