package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;
import nif.enums.FieldType;
import nif.niobject.particle.NiParticleModifier;

public class NiGravity extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiGravity" abstract="0" inherit="NiParticleModifier">

	 A particle modifier; applies a gravitational field on the particles.
	 
	 <add name="Unknown Float 1" type="float" ver1="4.0.0.2">Unknown.</add>
	 <add name="Force" type="float">The strength/force of this gravity.</add>
	 <add name="Type" type="FieldType">The force field's type.</add>
	 <add name="Position" type="Vector3">
	 The position of the mass point relative to the particle system. (TODO: check for versions <= 3.1)
	 </add>
	 <add name="Direction" type="Vector3">The direction of the applied acceleration.</add>
	 </niobject>	
	 */

	public float unknownFloat1;

	public float force;

	public FieldType type;

	public NifVector3 position;

	public NifVector3 direction;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownFloat1 = ByteConvert.readFloat(stream);
		force = ByteConvert.readFloat(stream);
		type = new FieldType(stream);
		position = new NifVector3(stream);
		direction = new NifVector3(stream);
		return success;
	}
}