package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifParticleDesc
{
	/**
	 <compound name="ParticleDesc">

	 Particle Description.
	 
	 <add name="Translation" type="Vector3">Unknown.</add>
	 <add name="Unknown Floats 1" type="float" arr1="3" ver2="10.2.0.0">Unknown.</add>
	 <add name="Unknown Float 1" type="float" default="0.9">Unknown.</add>
	 <add name="Unknown Float 2" type="float" default="0.9">Unknown.</add>
	 <add name="Unknown Float 3" type="float" default="3.0">Unknown.</add>
	 <add name="Unknown Int 1" type="int">Unknown.</add>
	 </compound>	
	 */

	public NifVector3 translation;

	public float unknownFloat1;

	public float unknownFloat2;

	public float unknownFloat3;

	public int unknownInt;

	public NifParticleDesc(InputStream stream) throws IOException
	{
		translation = new NifVector3(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownInt = ByteConvert.readInt(stream);
	}
}
