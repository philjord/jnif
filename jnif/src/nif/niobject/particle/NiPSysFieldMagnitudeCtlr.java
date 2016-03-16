package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysFieldMagnitudeCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 <niobject name="NiPSysFieldMagnitudeCtlr" inherit="NiPSysModifierFloatCtlr">

	 Particle system controller, used for ???.
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}