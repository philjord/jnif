package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;
import nif.niobject.controller.NiTimeController;

public class NiPSysUpdateCtlr extends NiTimeController
{
	/**
	 <niobject name="NiPSysUpdateCtlr" abstract="0" inherit="NiTimeController">

	 Particle system controller, used for ???.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}