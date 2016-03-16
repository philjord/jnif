package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysModifierActiveCtlr extends NiPSysModifierBoolCtlr
{
	/**
	 <niobject name="NiPSysModifierActiveCtlr" abstract="0" inherit="NiPSysModifierBoolCtlr" ver1="20.0.0.5">

	 Unknown.
	 
	 <add name="Data" type="Ref" template="NiVisData" ver2="10.1.0.0">This controller's data.</add>
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}