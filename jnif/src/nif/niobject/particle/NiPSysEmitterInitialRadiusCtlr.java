package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysEmitterInitialRadiusCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 <niobject name="NiPSysEmitterInitialRadiusCtlr" abstract="0" inherit="NiPSysModifierFloatCtlr" ver1="10.1.0.0">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}