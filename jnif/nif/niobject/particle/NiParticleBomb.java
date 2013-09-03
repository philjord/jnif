package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;
import nif.enums.DecayType;
import nif.enums.SymmetryType;

public class NiParticleBomb extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiParticleBomb" abstract="0" inherit="NiParticleModifier" ver1="3.03">

	 A particle modifier.
	 
	 <add name="Decay?" type="float">Unknown.</add>
	 <add name="Duration?" type="float">Unknown.</add>
	 <add name="DeltaV?" type="float">Unknown.</add>
	 <add name="Start?" type="float">Unknown.</add>
	 <add name="Decay Type?" type="DecayType">Unknown.</add>
	 <add name="Symmetry Type?" type="SymmetryType" ver1="4.1.0.12">Unknown.</add>
	 <add name="Position?" type="Vector3">
	 The position of the mass point relative to the particle system?
	 </add>
	 <add name="Direction?" type="Vector3">The direction of the applied acceleration?</add>
	 </niobject>
	 */

	public float decay;

	public float duration;

	public float deltaV;

	public float start;

	public DecayType decayType;

	public SymmetryType symmetryType;

	public NifVector3 position;

	public NifVector3 direction;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		decay = ByteConvert.readFloat(stream);
		duration = ByteConvert.readFloat(stream);
		deltaV = ByteConvert.readFloat(stream);
		start = ByteConvert.readFloat(stream);
		decayType = new DecayType(stream);
		symmetryType = new SymmetryType(stream);
		position = new NifVector3(stream);
		direction = new NifVector3(stream);

		return success;
	}
}