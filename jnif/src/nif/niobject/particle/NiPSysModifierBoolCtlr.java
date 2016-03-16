package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class NiPSysModifierBoolCtlr extends NiPSysModifierCtlr
{
	/**
	 <niobject name="NiPSysModifierBoolCtlr" abstract="1" inherit="NiPSysModifierCtlr" ver1="10.2.0.0">

	 A particle system modifier controller that deals with boolean data?
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}