package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifBoundingVolume;
import nif.enums.CollisionMode;
import nif.enums.PropagationMode;

public class NiCollisionData extends NiCollisionObject
{
	/**
	 
	 <niobject name="NiCollisionData" abstract="0" inherit="NiCollisionObject" ver1="10.0.1.0">

	 Collision box.
	 
	 <add name="Propagation Mode" type="PropagationMode">Propagation Mode</add>
	 <add name="Collision Mode" type="CollisionMode" ver1="10.1.0.0">Collision Mode</add>
	 <add name="Use ABV" type="byte">Use Alternate Bounding Volume.</add>
	 <add name="Bounding Volume" type="BoundingVolume" cond="Use ABV == 1">Collision data.</add>
	 </niobject>
	 
	 */

	public PropagationMode propagationMode;

	public CollisionMode collisionMode;

	public byte useABV;

	public NifBoundingVolume boundingVolume;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		propagationMode = new PropagationMode(stream);
		collisionMode = new CollisionMode(stream);
		useABV = ByteConvert.readByte(stream);
		if (useABV == 1)
		{
			boundingVolume = new NifBoundingVolume(stream);
		}

		return success;
	}
}