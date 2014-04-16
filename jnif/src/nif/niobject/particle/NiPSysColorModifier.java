package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiColorData;

public class NiPSysColorModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysColorModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Particle modifier that adds keyframe data to modify color/alpha values of particles over time.
	 
	 <add name="Data" type="Ref" template="NiColorData">Refers to NiColorData object.</add>
	 </niobject>
	 */

	public NifRef data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifRef(NiColorData.class, stream);

		return success;
	}
}
