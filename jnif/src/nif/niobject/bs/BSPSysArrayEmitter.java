package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.particle.NiPSysVolumeEmitter;

public class BSPSysArrayEmitter extends NiPSysVolumeEmitter
{
	/**
	 <niobject name="BSPSysArrayEmitter" abstract="0" inherit="NiPSysVolumeEmitter" ver1="20.0.0.5">

	 Particle emitter that uses a node, its children and subchildren to emit from. 
	  Emission will be evenly spread along points from nodes leading to their direct parents/children only.
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
