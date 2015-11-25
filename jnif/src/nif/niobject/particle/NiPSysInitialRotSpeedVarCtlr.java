package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public class NiPSysInitialRotSpeedVarCtlr extends NiPSysModifierCtlr
{
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
