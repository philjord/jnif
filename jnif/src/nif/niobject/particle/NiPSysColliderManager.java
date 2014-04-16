package nif.niobject.particle;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;

public class NiPSysColliderManager extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysColliderManager" abstract="0" inherit="NiPSysModifier" ver1="10.2.0.0">

	 Particle modifier that adds a defined shape to act as a collision object for particles to interact with.
	 
	 <add name="Collider" type="Ref" template="NiPSysCollider">
	 Link to a NiPSysPlanarCollider or NiPSysSphericalCollider.
	 </add>
	 </niobject>
	 */

	public NifRef collider;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		collider = new NifRef(NiPSysCollider.class, stream);

		return success;
	}
}
