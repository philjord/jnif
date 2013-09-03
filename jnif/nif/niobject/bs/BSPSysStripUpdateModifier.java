package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysStripUpdateModifier extends NiPSysModifier
{
	/**
	 
	 <niobject name="BSPSysStripUpdateModifier" inherit="NiPSysModifier" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific (mesh?) Particle System Modifier.
	 
	 <add name="Update Delta Time" type="float">Unknown</add>
	 </niobject>
	 */

	public float updateDeltaTime;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		updateDeltaTime = ByteConvert.readFloat(stream);

		return success;
	}
}