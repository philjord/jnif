package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifRef;
import nif.niobject.NiCollisionObject;
import nif.niobject.NiObject;

public abstract class bhkNiCollisionObject extends NiCollisionObject
{
	/**
	 
	 <niobject name="bhkNiCollisionObject" abstract="1" inherit="NiCollisionObject">

	 Havok related collision object?
	 
	 <add name="Flags" type="ushort" default="1">

	 Set to "1" for most objects. Bits: 0=Active 2=Notify 3=Set Local 6=Reset
	 
	 </add>
	 <add name="Body" type="Ref" template="NiObject">Links to the collision object data</add>
	 </niobject>
	 
	 */

	public NifFlags flags;

	public NifRef body;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags =new NifFlags(stream);
		body = new NifRef(NiObject.class, stream);

		return success;
	}
}