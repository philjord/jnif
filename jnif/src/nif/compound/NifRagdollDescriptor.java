package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifRagdollDescriptor
{
	/**
	 <compound name="RagdollDescriptor">
	
	 This constraint defines a cone in which an object can rotate. The shape of the cone can be controlled in two (orthogonal) directions.
	 
	 <add name="Pivot A" type="Vector4">Point around which the object will rotate.</add>
	 <add name="Plane A" type="Vector4">
	 Defines the orthogonal directions in which the shape can be controlled (namely in this direction, and in the direction orthogonal on this one and Twist A).
	 </add>
	 <add name="Twist A" type="Vector4">
	 Central directed axis of the cone in which the object can rotate. Orthogonal on Plane A.
	 </add>
	 <add name="Pivot B" type="Vector4">Pivot A in second entity coordinate system.</add>
	 <add name="Plane B" type="Vector4">Plane A in second entity coordinate system.</add>
	 <add name="Twist B" type="Vector4">Twist A in second entity coordinate system.</add>
	 <add name="Cone Max Angle" type="float">
	 Maximum angle the object can rotate around the vector orthogonal on Plane A and Twist A relative to the Twist A vector. Note that Cone Min Angle is not stored, but is simply minus this angle.
	 </add>
	 <add name="Plane Min Angle" type="float">
	 Minimum angle the object can rotate around Plane A, relative to Twist A.
	 </add>
	 <add name="Plane Max Angle" type="float">
	 Maximum angle the object can rotate around Plane A, relative to Twist A.
	 </add>
	 <add name="Twist Min Angle" type="float">
	 Minimum angle the object can rotate around Twist A, relative to Plane A.
	 </add>
	 <add name="Twist Max Angle" type="float">
	 Maximum angle the object can rotate around Twist A, relative to Plane A.
	 </add>
	 <add name="Max Friction" type="float">Maximum friction, typically 0 or 10.</add>
	 <add name="Unknown Float 1" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 2" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 3" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 4" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 5" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 6" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 7" type="float" ver1="20.2.0.7">Unknown</add>
	 </compound>	
	 */

	public NifVector4 pivotA;

	public NifVector4 planeA;

	public NifVector4 twistA;

	public NifVector4 motorA;

	public NifVector4 pivotB;

	public NifVector4 planeB;

	public NifVector4 twistB;

	public NifVector4 motorB;

	public float coneMinAngle;

	public float planeMaxAngle;

	public float planeMinAngle;

	public float twistMinAngle;

	public float twistMaxAngle;

	public float maxFriction;

	public boolean enableMotor;

	public NifMotor nifMotor;

	public NifRagdollDescriptor(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5 || (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER2 == 16))
		{
			pivotA = new NifVector4(stream);
			planeA = new NifVector4(stream);
			twistA = new NifVector4(stream);
			pivotB = new NifVector4(stream);
			planeB = new NifVector4(stream);
			twistB = new NifVector4(stream);
		}
		else if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER2 >= 16)
		{
			twistA = new NifVector4(stream);
			planeA = new NifVector4(stream);
			motorA = new NifVector4(stream);
			pivotA = new NifVector4(stream);
			twistB = new NifVector4(stream);
			planeB = new NifVector4(stream);
			motorB = new NifVector4(stream);
			pivotB = new NifVector4(stream);
		}

		coneMinAngle = ByteConvert.readFloat(stream);
		planeMaxAngle = ByteConvert.readFloat(stream);
		planeMinAngle = ByteConvert.readFloat(stream);
		twistMinAngle = ByteConvert.readFloat(stream);
		twistMaxAngle = ByteConvert.readFloat(stream);
		maxFriction = ByteConvert.readFloat(stream);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER2 > 16)
		{
			enableMotor = ByteConvert.readBool(stream, nifVer);
			if (enableMotor)
			{
				nifMotor = new NifMotor(stream, nifVer);
			}
		}

	}
}
