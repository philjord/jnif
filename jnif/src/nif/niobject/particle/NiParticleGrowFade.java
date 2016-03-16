package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiParticleGrowFade extends NiParticleModifier
{
	/**
	 
	 <niobject name="NiParticleGrowFade" abstract="0" inherit="NiParticleModifier">

	 This particle system modifier controls the particle size. If it is present the particles start with size 0.0 . 
	 Then they grow to their original size and stay there until they fade to zero size again at the end of their lifetime cycle.
	 
	 <add name="Grow" type="float">
	 The time from the beginning of the particle lifetime during which the particle grows.
	 </add>
	 <add name="Fade" type="float">
	 The time from the end of the particle lifetime during which the particle fades.
	 </add>
	 </niobject>		
	 */

	public float grow;

	public float fade;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		grow = ByteConvert.readFloat(stream);
		fade = ByteConvert.readFloat(stream);

		return success;
	}
}