package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysCylinderEmitter extends NiPSysVolumeEmitter
{
	/**
	 <niobject name="NiPSysCylinderEmitter" abstract="0" inherit="NiPSysVolumeEmitter" ver1="10.1.0.0">

	 Particle emitter that uses points within a defined Cylinder shape to emit from.
	 
	 <add name="Radius" type="float">Radius of the cylinder shape.</add>
	 <add name="Height" type="float">Height of the cylinders shape.</add>
	 </niobject>
	 */

	public float radius;

	public float height;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		radius = ByteConvert.readFloat(stream);
		height = ByteConvert.readFloat(stream);

		return success;
	}
}
