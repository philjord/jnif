package nif.niobject.controller;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.particle.NiParticleSystemController;

public class NiBSPArrayController extends NiParticleSystemController
{
	/**
	 <niobject name="NiBSPArrayController" abstract="0" inherit="NiParticleSystemController">

	 A particle system controller, used by BS in conjunction with NiBSParticleNode.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}