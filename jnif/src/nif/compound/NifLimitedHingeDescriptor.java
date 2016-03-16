package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifLimitedHingeDescriptor
{
	/**
	 <compound name="LimitedHingeDescriptor">
	 <add name="Pivot A" type="Vector4">Pivot point around which the object will rotate.</add>
	 <add name="Axle A" type="Vector4">Axis of rotation.</add>
	 <add name="Perp2 Axle In A1" type="Vector4">
	 Vector in the rotation plane which defines the zero angle.
	 </add>
	 <add name="Perp2 Axle In A2" type="Vector4">
	 Vector in the rotation plane, orthogonal on the previous one, which defines the positive direction of rotation. This is always the vector product of Axle A and Perp2 Axle In A1.
	 </add>
	 <add name="Pivot B" type="Vector4">Pivot A in second entity coordinate system.</add>
	 <add name="Axle B" type="Vector4">Axle A in second entity coordinate system.</add>
	 <add name="Perp2 Axle In B2" type="Vector4">
	 Perp2 Axle In A2 in second entity coordinate system.
	 </add>
	 <add name="Min Angle" type="float">Minimum rotation angle.</add>
	 <add name="Max Angle" type="float">Maximum rotation angle.</add>
	 <add name="Max Friction" type="float">Maximum friction, typically either 0 or 10.</add>
	 <add name="Unknown Float 1" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 2" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Float 3" type="float" ver1="20.2.0.7">Unknown</add>
	 </compound>	
	 */

	public NifVector4 pivotA;

	public NifVector4 axleA;

	public NifVector4 perp2AxleInA1;

	public NifVector4 perp2AxleInA2;

	public NifVector4 pivotB;

	public NifVector4 axleB;

	public NifVector4 perp2AxleInB1;

	public NifVector4 perp2AxleInB2;

	public float minAngle;

	public float maxAngle;

	public float maxFriction;

	public boolean enableMotor;

	public NifMotor nifMotor;

	public NifLimitedHingeDescriptor(ByteBuffer stream, NifVer nifVer) throws IOException
	{

		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5 || (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER2 == 16))
		{
			pivotA = new NifVector4(stream);
			axleA = new NifVector4(stream);
			perp2AxleInA1 = new NifVector4(stream);
			perp2AxleInA2 = new NifVector4(stream);
			pivotB = new NifVector4(stream);
			axleB = new NifVector4(stream);
			perp2AxleInB2 = new NifVector4(stream);
		}
		else if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER2 >= 16)
		{
			axleA = new NifVector4(stream);
			perp2AxleInA1 = new NifVector4(stream);
			perp2AxleInA2 = new NifVector4(stream);
			pivotA = new NifVector4(stream);
			axleB = new NifVector4(stream);
			perp2AxleInB1 = new NifVector4(stream);
			perp2AxleInB2 = new NifVector4(stream);
			pivotB = new NifVector4(stream);
		}

		minAngle = ByteConvert.readFloat(stream);
		maxAngle = ByteConvert.readFloat(stream);
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
