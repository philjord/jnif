package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysEmitterPlanarAngleVarCtlr extends NiPSysModifierFloatCtlr 
{
	/**
	  * TODO: I have no definition!
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
