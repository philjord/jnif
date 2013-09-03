package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector3;
import nif.enums.DecayType;
import nif.enums.SymmetryType;
import nif.niobject.NiNode;

public class NiPSysBombModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysBombModifier" abstract="0" inherit="NiPSysModifier" ver1="20.0.0.5">

	 Particle modifier that uses a NiNode to use as a "Bomb Object" to alter the path of particles.
	 
	 <add name="Bomb Object" type="Ptr" template="NiNode">Link to a NiNode for bomb to function.</add>
	 <add name="Bomb Axis" type="Vector3">Orientation of bomb object.</add>
	 <add name="Decay" type="float">Falloff rate of the bomb object.</add>
	 <add name="Delta V" type="float">DeltaV /  Strength?</add>
	 <add name="Decay Type" type="DecayType">Decay type</add>
	 <add name="Symmetry Type" type="SymmetryType">Shape/symmetry of the bomb object.</add>
	 </niobject>
	 */
	public NifPtr bombObject;

	public NifVector3 bombAxis;

	public float decay;

	public float deltaV;

	public DecayType decayType;

	public SymmetryType symmetryType;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		bombObject = new NifPtr(NiNode.class, stream);
		bombAxis = new NifVector3(stream);
		decay = ByteConvert.readFloat(stream);
		deltaV = ByteConvert.readFloat(stream);
		decayType = new DecayType(stream);
		symmetryType = new SymmetryType(stream);

		return success;
	}
}
