package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.enums.BoundVolumeType;

public class NifBoundingVolume
{
	/**
	 <compound name="BoundingVolume">
	 <add name="Collision Type" type="BoundVolumeType">Type of collision data.</add>
	 <add name="Sphere" type="SphereBV" cond="Collision Type == 0">Sphere</add>
	 <add name="Box" type="BoxBV" cond="Collision Type == 1">Box</add>
	 <add name="Capsule" type="CapsuleBV" cond="Collision Type == 2">Capsule</add>
	 <add name="Union" type="UnionBV" cond="Collision Type == 4">Union</add>
	 <add name="HalfSpace" type="HalfSpaceBV" cond="Collision Type == 5">Half Space</add>
	 </compound>
	 */

	public BoundVolumeType collisionType;

	public NifSphereBV sphere;

	public NifBoxBV box;

	public NifCapsuleBV capsule;

	public NifUnionBV union;

	public NifHalfSpaceBV halfSpace;

	public NifBoundingVolume(ByteBuffer stream) throws IOException
	{
		collisionType = new BoundVolumeType(stream);
		if (collisionType.type == BoundVolumeType.SPHERE_BV)
		{
			sphere = new NifSphereBV(stream);
		}
		else if (collisionType.type == BoundVolumeType.BOX_BV)
		{
			box = new NifBoxBV(stream);
		}
		else if (collisionType.type == BoundVolumeType.CAPSULE_BV)
		{
			capsule = new NifCapsuleBV(stream);
		}
		else if (collisionType.type == BoundVolumeType.UNION_BV)
		{
			union = new NifUnionBV(stream);
		}
		else if (collisionType.type == BoundVolumeType.HALFSPACE_BV)
		{
			halfSpace = new NifHalfSpaceBV(stream);
		}

	}
}
