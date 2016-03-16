package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysSphericalCollider extends NiPSysCollider
{
	/**
	 
	 <niobject name="NiPSysSphericalCollider" abstract="0" inherit="NiPSysCollider" ver1="10.2.0.0">

	 Particle Collider object which particles will interact with.
	 
	 <add name="Radius" type="float">Defines the radius of the sphere object.</add>
	 </niobject>
	 */

	public float radius;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		radius = ByteConvert.readFloat(stream);

		return success;
	}
}