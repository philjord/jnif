package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysInitialRotSpeedCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 

	    <niobject name="NiPSysInitialRotSpeedCtlr" inherit="NiPSysModifierFloatCtlr">
	        Particle system controller for emitter initial rotation speed.
	    </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
