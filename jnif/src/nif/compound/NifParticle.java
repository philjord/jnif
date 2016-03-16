package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifParticle
{
	/**
	 <compound name="Particle">

	 particle array entry
	 
	 <add name="Velocity" type="Vector3">Particle velocity</add>
	 <add name="Unknown Vector" type="Vector3">Unknown</add>
	 <add name="Lifetime" type="float">The particle's age.</add>
	 <add name="Lifespan" type="float">Maximum age of the particle.</add>
	 <add name="Timestamp" type="float">Timestamp of the last update.</add>
	 <add name="Unknown Short" type="ushort" default="0">Unknown short</add>
	 <add name="Vertex ID" type="ushort">Particle/vertex index matches array index</add>
	 </compound>	
	 */

	public NifVector3 velocity;

	public NifVector3 unknownVector;

	public float lifetime;

	public float lifespan;

	public float timestamp;

	public short unknownShort;

	public short vertexID;

	public NifParticle(ByteBuffer stream) throws IOException
	{
		velocity = new NifVector3(stream);
		unknownVector = new NifVector3(stream);
		lifetime = ByteConvert.readFloat(stream);
		lifespan = ByteConvert.readFloat(stream);
		timestamp = ByteConvert.readFloat(stream);
		unknownShort = ByteConvert.readShort(stream);
		vertexID = ByteConvert.readShort(stream);
	}
}
