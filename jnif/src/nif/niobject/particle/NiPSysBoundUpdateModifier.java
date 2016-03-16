package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysBoundUpdateModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysBoundUpdateModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Unknown particle system modifier.
	 
	 <add name="Update Skip" type="ushort">Unknown.</add>
	 </niobject>
	 */

	public short updateSkip;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		updateSkip = ByteConvert.readShort(stream);

		return success;
	}
}
