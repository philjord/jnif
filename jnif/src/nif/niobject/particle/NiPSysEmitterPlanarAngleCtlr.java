package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiPSysEmitterPlanarAngleCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 <niobject name="NiPSysEmitterPlanarAngleCtlr" inherit="NiPSysModifierFloatCtlr">
	        Particle system controller for emitter planar angle.
	    </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
