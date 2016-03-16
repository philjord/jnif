package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector3;
import nif.niobject.NiNode;

public class NiPSysGravityModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysGravityModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Adds gravity to a particle system, when linked to a NiNode to use as a Gravity Object.
	 
	 <add name="Gravity Object" type="Ptr" template="NiNode">Refers to a NiNode for gravity location.</add>
	 <add name="Gravity Axis" type="Vector3">Orientation of gravity.</add>
	 <add name="Decay" type="float">Falloff range.</add>
	 <add name="Strength" type="float">The strength of gravity.</add>
	 <add name="Force Type" type="ForceType">Planar or Spherical type</add>
	 <add name="Turbulence" type="float">Adds a degree of randomness.</add>
	 <add name="Turbulence Scale" type="float" default="1.0">Range for turbulence.</add>
	 <add name="Unknown Byte" type="byte" ver1="20.2.0.7" userver="11">Unknown</add>
	 </niobject>
	 */

	public static int FORCE_PLANAR = 0;

	public static int FORCE_SPHERICAL = 1;

	public NifPtr gravityObject;

	public NifVector3 gravityAxis;

	public float decay;

	public float strength;

	public int forceType;

	public float turbulence;

	public float turbulenceScale;

	public byte unknownByte;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		gravityObject = new NifPtr(NiNode.class, stream);
		gravityAxis = new NifVector3(stream);
		decay = ByteConvert.readFloat(stream);
		strength = ByteConvert.readFloat(stream);
		forceType = ByteConvert.readInt(stream);
		turbulence = ByteConvert.readFloat(stream);
		turbulenceScale = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && (nifVer.LOAD_USER_VER == 11||nifVer.LOAD_USER_VER == 12))
		{
			unknownByte = ByteConvert.readByte(stream);
		}
		return success;
	}
}
