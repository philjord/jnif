package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiPSysPositionModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysPositionModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">
	 Unknown particle system modifier.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
