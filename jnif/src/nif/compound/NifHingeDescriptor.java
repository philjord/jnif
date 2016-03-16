package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifHingeDescriptor
{
	/**
	 <compound name="HingeDescriptor">
	 <add name="Pivot A" type="Vector4">Pivot point around which the object will rotate.</add>
	 <add name="Perp2 Axle In A1" type="Vector4">
	 Vector in the rotation plane which defines the zero angle.
	 </add>
	 <add name="Perp2 Axle In A2" type="Vector4">
	 Vector in the rotation plane, orthogonal on the previous one, which defines the positive direction of rotation.
	 </add>
	 <add name="Pivot B" type="Vector4">Pivot A in second entity coordinate system.</add>
	 <add name="Axle B" type="Vector4">
	 Axle A (vector orthogonal on Perp2 Axles) in second entity coordinate system.
	 </add>
	 <add name="Unknown Float 1" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 2" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 3" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 4" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 5" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 6" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 7" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 8" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 9" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Int 10" type="float" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Byte 2" type="byte" ver1="20.2.0.7">Unknown</add>
	 <add name="Unknown Byte 3" type="byte" ver1="20.2.0.7">Unknown</add>
	 </compound>
	 */

	public NifVector4 pivotA;

	public NifVector4 perp2AxleInA1;

	public NifVector4 perp2AxleInA2;

	public NifVector4 pivotB;

	public NifVector4 axleB;

	public NifVector4 perp2AxleInB2;

	public float unknownFloat1;

	public float unknownInt2;

	public float unknownInt3;

	public float unknownInt4;

	public float unknownInt5;

	public float unknownInt6;

	public float unknownInt7;

	public float unknownInt8;

	public float unknownInt9;

	public float unknownInt10;

	public byte unknownByte1;

	public byte unknownByte2;

	public byte unknownByte3;

	public NifHingeDescriptor(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		pivotA = new NifVector4(stream);
		perp2AxleInA1 = new NifVector4(stream);
		perp2AxleInA2 = new NifVector4(stream);
		pivotB = new NifVector4(stream);
		axleB = new NifVector4(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			unknownFloat1 = ByteConvert.readFloat(stream);
			unknownInt2 = ByteConvert.readFloat(stream);
			unknownInt3 = ByteConvert.readFloat(stream);
			unknownInt4 = ByteConvert.readFloat(stream);
			unknownInt5 = ByteConvert.readFloat(stream);
			unknownInt6 = ByteConvert.readFloat(stream);
			unknownInt7 = ByteConvert.readFloat(stream);
			unknownInt8 = ByteConvert.readFloat(stream);
			unknownInt9 = ByteConvert.readFloat(stream);
			unknownInt10 = ByteConvert.readFloat(stream);
			unknownByte1 = ByteConvert.readByte(stream);
			unknownByte2 = ByteConvert.readByte(stream);
			unknownByte3 = ByteConvert.readByte(stream);
		}
	}
}
