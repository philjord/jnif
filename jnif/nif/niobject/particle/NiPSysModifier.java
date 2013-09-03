package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.niobject.NiObject;

public abstract class NiPSysModifier extends NiObject
{
	/**
	 <niobject name="NiPSysModifier" abstract="1" inherit="NiObject" ver1="10.1.0.0">

	 Generic particle system modifier object.
	 
	 <add name="Name" type="string">The object name.</add>
	 <add name="Order" type="uint">
	 Modifier ID in the particle modifier chain (always a multiple of 1000)?
	 </add>
	 <add name="Target" type="Ptr" template="NiParticleSystem">NiParticleSystem parent of this modifier.</add>
	 <add name="Active" type="bool">
	 Whether the modifier is currently in effect?  Usually true.
	 </add>
	 </niobject>
	 */
	public String name;

	public int order;

	public NifPtr target;

	public boolean active;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		name = ByteConvert.readIndexString(stream, nifVer);
		order = ByteConvert.readInt(stream);
		target = new NifPtr(NiParticleSystem.class, stream);
		active = ByteConvert.readBool(stream, nifVer);

		return success;
	}
}
