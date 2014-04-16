package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.NifColor4;
import nif.compound.NifParticle;
import nif.compound.NifVector3;
import nif.niobject.NiObject;
import nif.niobject.controller.NiTimeController;

public class NiParticleSystemController extends NiTimeController
{
	/**
	 <niobject name="NiParticleSystemController" abstract="0" inherit="NiTimeController">

	 A generic particle system time controller object.
	 
	 <add name="Old Speed" type="uint" ver2="3.1">Particle speed in old files</add>
	 <add name="Speed" type="float" ver1="3.3.0.13">Particle speed</add>
	 <add name="Speed Random" type="float">Particle random speed modifier</add>
	 <add name="Vertical Direction" type="float">

	 vertical emit direction [radians]
	 0.0 : up
	 1.6 : horizontal
	 3.1416 : down
	 
	 </add>
	 <add name="Vertical Angle" type="float">emitter's vertical opening angle [radians]</add>
	 <add name="Horizontal Direction" type="float">horizontal emit direction</add>
	 <add name="Horizontal Angle" type="float">emitter's horizontal opening angle</add>
	 <add name="Unknown Normal?" type="Vector3">Unknown.</add>
	 <add name="Unknown Color?" type="Color4">Unknown.</add>
	 <add name="Size" type="float">Particle size</add>
	 <add name="Emit Start Time" type="float">Particle emit start time</add>
	 <add name="Emit Stop Time" type="float">Particle emit stop time</add>
	 <add name="Unknown Byte" type="byte" ver1="4.0.0.2">Unknown byte, (=0)</add>
	 <add name="Old Emit Rate" type="uint" ver2="3.1">Particle emission rate in old files</add>
	 <add name="Emit Rate" type="float" ver1="3.3.0.13">Particle emission rate (particles per second)</add>
	 <add name="Lifetime" type="float">Particle lifetime</add>
	 <add name="Lifetime Random" type="float">Particle lifetime random modifier</add>
	 <add name="Emit Flags" type="ushort" ver1="4.0.0.2">
	 Bit 0: Emit Rate toggle bit (0 = auto adjust, 1 = use Emit Rate value)
	 </add>
	 <add name="Start Random" type="Vector3">Particle random start translation vector</add>
	 <add name="Emitter" type="Ptr" template="NiObject">
	 This index targets the particle emitter object (TODO: find out what type of object this refers to).
	 </add>
	 <add name="Unknown Short 2?" type="ushort" ver1="4.0.0.2">? short=0 ?</add>
	 <add name="Unknown Float 13?" type="float" ver1="4.0.0.2">? float=1.0 ?</add>
	 <add name="Unknown Int 1?" type="uint" ver1="4.0.0.2">? int=1 ?</add>
	 <add name="Unknown Int 2?" type="uint" ver1="4.0.0.2">? int=0 ?</add>
	 <add name="Unknown Short 3?" type="ushort" ver1="4.0.0.2">? short=0 ?</add>
	 <!--
	 <add name="Particle" type="Particle" ver2="3.1">The particle (older NIF versions only have a single particle per controller?)</add> 
	 -->
	 <add name="Particle Velocity" type="Vector3" ver2="3.1">Particle velocity</add>
	 <add name="Particle Unknown Vector" type="Vector3" ver2="3.1">Unknown</add>
	 <add name="Particle Lifetime" type="float" ver2="3.1">The particle's age.</add>
	 <add name="Particle Link" type="Ref" template="NiObject" ver2="3.1"/>
	 <add name="Particle Timestamp" type="uint" ver2="3.1">Timestamp of the last update.</add>
	 <add name="Particle Unknown Short" type="ushort" ver2="3.1">Unknown short</add>
	 <add name="Particle Vertex Id" type="ushort" ver2="3.1">Particle/vertex index matches array index</add>
	 <add name="Num Particles" type="ushort" ver1="4.0.0.2">
	 Size of the following array. (Maximum number of simultaneous active particles)
	 </add>
	 <add name="Num Valid" type="ushort" ver1="4.0.0.2">
	 Number of valid entries in the following array. (Number of active particles at the time the system was saved)
	 </add>
	 <add name="Particles" type="Particle" arr1="Num Particles" ver1="4.0.0.2">Individual particle modifiers?</add>
	 <add name="Unknown Link" type="Ref" template="NiObject" ver1="4.0.0.2">unknown int (=0xffffffff)</add>
	 <add name="Particle Extra" type="Ref" template="NiParticleModifier">
	 Link to some optional particle modifiers (NiGravity, NiParticleGrowFade, NiParticleBomb, ...)
	 </add>
	 <add name="Unknown Link 2" type="Ref" template="NiObject">Unknown int (=0xffffffff)</add>
	 <add name="Trailer" type="byte" ver1="4.0.0.2">Trailing null byte</add>
	 <add name="Color Data" type="Ref" template="NiColorData" ver2="3.1"/>
	 <add name="Unknown Float 1" type="float" ver2="3.1"/>
	 <add name="Unknown Floats 2" arr1="Particle Unknown Short" type="float" ver2="3.1"/>
	 </niobject>
	 
	 */

	public float speed;

	public float speedRandom;

	public float verticalDirection;

	public float verticalAngle;

	public float horizontalDirection;

	public float horizontalAngle;

	public NifVector3 unknownNormal;

	public NifColor4 unknownColor;

	public float size;

	public float emitStartTime;

	public float emitStopTime;

	public byte unknownByte;

	public float emitRate;

	public float lifetime;

	public float lifetimeRandom;

	public short emitFlags;

	public NifVector3 startRandom;

	public NifPtr emitter;

	public short unknownShort2;

	public float unknownFloat13;

	public int unknownInt1;

	public int unknownInt2;

	public short unknownShort3;

	public short numParticles;

	public short numValid;

	public NifParticle[] particles;

	public NifRef unknownLink;

	public NifRef particleExtra;

	public NifRef unknownLink2;

	public byte trailer;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		speed = ByteConvert.readFloat(stream);
		speedRandom = ByteConvert.readFloat(stream);
		verticalDirection = ByteConvert.readFloat(stream);
		verticalAngle = ByteConvert.readFloat(stream);
		horizontalDirection = ByteConvert.readFloat(stream);
		horizontalAngle = ByteConvert.readFloat(stream);
		unknownNormal = new NifVector3(stream);
		unknownColor = new NifColor4(stream);
		size = ByteConvert.readFloat(stream);
		emitStartTime = ByteConvert.readFloat(stream);
		emitStopTime = ByteConvert.readFloat(stream);
		unknownByte = ByteConvert.readByte(stream);
		emitRate = ByteConvert.readFloat(stream);
		lifetime = ByteConvert.readFloat(stream);
		lifetimeRandom = ByteConvert.readFloat(stream);
		emitFlags = ByteConvert.readShort(stream);
		startRandom = new NifVector3(stream);
		emitter = new NifPtr(NiObject.class, stream);
		unknownShort2 = ByteConvert.readShort(stream);
		unknownFloat13 = ByteConvert.readFloat(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownShort3 = ByteConvert.readShort(stream);
		numParticles = ByteConvert.readShort(stream);
		numValid = ByteConvert.readShort(stream);
		particles = new NifParticle[numParticles];

		for (int i = 0; i < numParticles; i++)
		{
			particles[i] = new NifParticle(stream);
		}
		unknownLink = new NifRef(NiObject.class, stream);
		particleExtra = new NifRef(NiParticleModifier.class, stream);
		unknownLink2 = new NifRef(NiObject.class, stream);
		trailer = ByteConvert.readByte(stream);

		return success;
	}
}