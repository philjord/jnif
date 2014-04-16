package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiColorData;

public class NiParticleColorModifier extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiParticleColorModifier" abstract="0" inherit="NiParticleModifier">

	 Unknown.
	 
	 <add name="Color Data" type="Ref" template="NiColorData">Color data index.</add>
	 </niobject>
	 */

	public NifRef colorData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		colorData = new NifRef(NiColorData.class, stream);

		return success;
	}
}