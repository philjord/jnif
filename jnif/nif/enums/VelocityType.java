package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class VelocityType
{
	/**
	 * Controls the way the a particle mesh emitter determines the starting speed and direction of the particles that are emitted.
	    
	 */
	public static final int VELOCITY_USE_NORMALS = 0;//Uses the normals of the meshes to determine staring velocity.

	public static final int VELOCITY_USE_RANDOM = 1;//Starts particles with a random velocity.

	public static final int VELOCITY_USE_DIRECTION = 2;//Uses the emission axis to determine initial particle direction?

	public int type;

	public VelocityType(InputStream stream) throws IOException
	{
		type = ByteConvert.readInt(stream);
	}
}
