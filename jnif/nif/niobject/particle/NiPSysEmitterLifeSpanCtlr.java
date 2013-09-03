package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public class NiPSysEmitterLifeSpanCtlr extends NiPSysModifierFloatCtlr
{
	/**
	 <niobject name="NiPSysEmitterLifeSpanCtlr" abstract="0" inherit="NiPSysModifierFloatCtlr" ver1="20.0.0.5">
	 Unknown.
	 </niobject>
	 */
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}