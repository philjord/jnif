package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysBoxEmitter extends NiPSysVolumeEmitter
{
	/**
	 <niobject name="NiPSysBoxEmitter" abstract="0" inherit="NiPSysVolumeEmitter" ver1="10.1.0.0">

	 Particle emitter that uses points within a defined Box shape to emit from..
	 
	 <add name="Width" type="float">Defines the Width of the box area.</add>
	 <add name="Height" type="float">Defines the Height of the box area.</add>
	 <add name="Depth" type="float">Defines the Depth of the box area.</add>
	 </niobject>
	 */

	public float width;

	public float height;

	public float depth;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		width = ByteConvert.readFloat(stream);
		height = ByteConvert.readFloat(stream);
		depth = ByteConvert.readFloat(stream);

		return success;
	}
}
