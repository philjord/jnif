package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;
import nif.niobject.controller.NiTimeController;

public class NiPSysResetOnLoopCtlr extends NiTimeController
{
	/**
	 <niobject name="NiPSysResetOnLoopCtlr" abstract="0" inherit="NiTimeController" ver1="10.2.0.0">
	 Unknown.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}