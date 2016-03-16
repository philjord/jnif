package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifColor4;

public abstract class NiPSysEmitter extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysEmitter" abstract="1" inherit="NiPSysModifier" ver1="10.1.0.0">

	 A particle emitter?
	 
	 <add name="Speed" type="float">Speed / Inertia of particle movement.</add>
	 <add name="Speed Variation" type="float">Adds an amount of randomness to Speed.</add>
	 <add name="Declination" type="float">Declination / First axis.</add>
	 <add name="Declination Variation" type="float">Declination randomness / First axis.</add>
	 <add name="Planar Angle" type="float">Planar Angle / Second axis.</add>
	 <add name="Planar Angle Variation" type="float">Planar Angle randomness / Second axis .</add>
	 <add name="Initial Color" type="Color4">Defines color of a birthed particle.</add>
	 <add name="Initial Radius" type="float">Size of a birthed particle.</add>
	 <add name="Radius Variation" type="float" ver1="20.0.0.4">Particle Radius randomness.</add>
	 <add name="Life Span" type="float">Duration until a particle dies.</add>
	 <add name="Life Span Variation" type="float">Adds randomness to Life Span.</add>
	 </niobject>
	 */

	public float speed;

	public float speedVariation;

	public float declination;

	public float declinationVariation;

	public float planarAngle;

	public float planarAngleVariation;

	public NifColor4 initialColor;

	public float initialRadius;

	public float radiusVariation;

	public float lifeSpan;

	public float lifeSpanVariation;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		speed = ByteConvert.readFloat(stream);
		speedVariation = ByteConvert.readFloat(stream);
		declination = ByteConvert.readFloat(stream);
		declinationVariation = ByteConvert.readFloat(stream);
		planarAngle = ByteConvert.readFloat(stream);
		planarAngleVariation = ByteConvert.readFloat(stream);
		initialColor = new NifColor4(stream);
		initialRadius = ByteConvert.readFloat(stream);
		radiusVariation = ByteConvert.readFloat(stream);
		lifeSpan = ByteConvert.readFloat(stream);
		lifeSpanVariation = ByteConvert.readFloat(stream);
		
		if (nifVer.isBP())
		{
			ByteConvert.readBytes(1, stream);
		}

		return success;
	}
}
