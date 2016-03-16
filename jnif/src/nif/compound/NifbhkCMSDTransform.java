package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NifbhkCMSDTransform
{
	/**
	 * 
	<compound name="bhkCMSDTransform">
	A set of transformation data: translation and rotation
	    <add name="Translation" type="Vector4">A vector that moves the chunk by the specified amount. W is not used.</add>
	    <add name="Rotation" type="QuaternionXYZW">Rotation. Reference point for rotation is bhkRigidBody translation.</add>
	</compound>
	 */

	public NifVector4 Translation;

	public NifQuaternionXYZW Rotation;

	public NifbhkCMSDTransform(ByteBuffer stream) throws IOException
	{
		Translation = new NifVector4(stream);
		Rotation = new NifQuaternionXYZW(stream);
	}
}
