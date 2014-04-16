package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifPtr;

public class NiCollisionObject extends NiObject
{
	/**
	 
	 <niobject name="NiCollisionObject" abstract="0" inherit="NiObject" ver1="20.0.0.5">

	 This is the most common collision object found in NIF files. It acts as a real object that
	 is visible and possibly (if the body allows for it) interactive. The node itself
	 is simple, it only has three properties.
	 For this type of collision object, bhkRigidBody or bhkRigidBodyT is generally used.
	 
	 <add name="Target" type="Ptr" template="NiAVObject">
	 Index of the AV object referring to this collision object.
	 </add>
	 </niobject>
	 
	 */

	public NifPtr target;

	//	public short unknownShort;

	//	public NifRef body;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		target = new NifPtr(NiAVObject.class, stream);

		//		unknownShort = ByteConvert.readShort(stream);
		//		body = new NifRef(NiObject.class, stream);

		return success;
	}
}