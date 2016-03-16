package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.NiNode;
import nif.niobject.NiObject;

public abstract class NiPSysCollider extends NiObject
{
	/**
	 <niobject name="NiPSysCollider" abstract="1" inherit="NiObject">

	 Particle system collider.
	 
	 <add name="Bounce" type="float">Defines amount of bounce the collider object has.</add>
	 <add name="Spawn on Collide" type="bool">Unknown.</add>
	 <add name="Die on Collide" type="bool">Kill particles on impact if set to yes.</add>
	 <add name="Spawn Modifier" type="Ref" template="NiPSysSpawnModifier">Link to NiPSysSpawnModifier object?</add>
	 <add name="Parent" type="Ptr" template="NiObject">Link to parent.</add>
	 <add name="Next Collider" type="Ref" template="NiObject">The next collider.</add>
	 <add name="Collider Object" type="Ptr" template="NiNode">
	 Links to a NiNode that will define where in object space the collider is located/oriented.
	 </add>
	 </niobject>
	 
	 */

	public float bounce;

	public boolean spawnonCollide;

	public boolean dieonCollide;

	public NifRef spawnModifier;

	public NifPtr parent;

	public NifRef nextCollider;

	public NifRef colliderObject;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		bounce = ByteConvert.readFloat(stream);
		spawnonCollide = ByteConvert.readBool(stream, nifVer);
		dieonCollide = ByteConvert.readBool(stream, nifVer);
		spawnModifier = new NifRef(NiPSysSpawnModifier.class, stream);
		parent = new NifPtr(NiObject.class, stream);
		nextCollider = new NifRef(NiPSysCollider.class, stream);
		colliderObject = new NifRef(NiNode.class, stream);

		return success;
	}
}