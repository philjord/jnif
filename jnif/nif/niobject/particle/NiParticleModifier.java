package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.NiObject;

public abstract class NiParticleModifier extends NiObject
{
	//NOITE!!!!! NOTE!!! possibly named AParticleModifier in the files!!
	/**
	 <niobject name="NiParticleModifier" abstract="1" inherit="NiObject">

	 A particle system modifier.
	 
	 <add name="Next Modifier" type="Ref" template="NiParticleModifier">Next particle modifier.</add>
	 <add name="Controller" type="Ptr" template="NiParticleSystemController" ver1="4.0.0.2">Points to the particle system controller parent.</add>
	 </niobject>	
	 */

	public NifRef nextModifier;

	public NifPtr controller;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		nextModifier = new NifRef(NiParticleModifier.class, stream);
		controller = new NifPtr(NiParticleSystemController.class, stream);
		return success;
	}
}