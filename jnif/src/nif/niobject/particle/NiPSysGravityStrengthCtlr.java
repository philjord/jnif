package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysGravityStrengthCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 <niobject name="NiPSysGravityStrengthCtlr" abstract="0" inherit="NiPSysModifierFloatCtlr" ver1="20.0.0.5">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}