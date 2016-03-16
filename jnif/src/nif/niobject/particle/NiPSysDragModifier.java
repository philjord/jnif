package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector3;
import nif.niobject.NiObject;

public class NiPSysDragModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysDragModifier" abstract="0" inherit="NiPSysModifier" ver1="20.0.0.5">

	 Unknown.
	 
	 <add name="Parent" type="Ptr" template="NiObject">Parent reference.</add>
	 <add name="Drag Axis" type="Vector3">The drag axis.</add>
	 <add name="Percentage" type="float">Drag percentage.</add>
	 <add name="Range" type="float">The range.</add>
	 <add name="Range Falloff" type="float">The range falloff.</add>
	 </niobject>
	 file:///C:/Emergent/Gamebryo-LightSpeed-Binary/Documentation/HTML/Reference/NiParticle/NiPSDragForce.htm
	 */
	public NifPtr parent;

	public NifVector3 dragAxis;

	public float percentage;

	public float range;

	public float rangeFalloff;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		parent = new NifPtr(NiObject.class, stream);
		dragAxis = new NifVector3(stream);
		percentage = ByteConvert.readFloat(stream);
		range = ByteConvert.readFloat(stream);
		rangeFalloff = ByteConvert.readFloat(stream);

		return success;
	}
}
