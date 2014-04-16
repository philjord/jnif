package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;

public abstract class NiPSysModifierFloatCtlr extends NiPSysModifierCtlr
{
	/**
	 <niobject name="NiPSysModifierFloatCtlr" abstract="1" inherit="NiPSysModifierCtlr" ver1="10.2.0.0">

	 A particle system modifier controller that deals with floating point data?
	 
	 <add name="Data" type="Ref" template="NiFloatData" ver2="10.1.0.0">This controller's data.</add>
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}