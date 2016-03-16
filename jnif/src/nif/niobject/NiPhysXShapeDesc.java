package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifQuaternion;

public class NiPhysXShapeDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXShapeDesc" abstract="0" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Quat 1" type="Quaternion">Unknown</add>
	 <add name="Unknown Quat 2" type="Quaternion">Unknown</add>
	 <add name="Unknown Quat 3" type="Quaternion">Unknown</add>
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Short 2" type="short">Unknown</add>
	 <add name="Unknown Float 1" type="float">Unknown</add>
	 <add name="Unknown Float 2" type="float">Unknown</add>
	 <add name="Unknown Float 3" type="float">Unknown</add>
	 <add name="Unknown Int 3" type="int">Unknown</add>
	 <add name="Unknown Int 4" type="int">Unknown</add>
	 <add name="Unknown Int 5" type="int">Unknown</add>
	 <add name="Unknown Int 7" type="int">Unknown</add>
	 <add name="Unknown Int 8" type="int">Unknown</add>
	 <add name="Unknown Bytes 1" type="byte" arr1="8" ver1="20.3.0.6">Unknown. Wrong, but better than nothing.</add>
	 <add name="Mesh Description" type="Ref" template="NiPhysXMeshDesc">PhysX Mesh Description</add>
	 </niobject>
	 */

	public int unknownInt1;

	public NifQuaternion unknownQuat1;

	public NifQuaternion unknownQuat2;

	public NifQuaternion unknownQuat3;

	public short unknownShort1;

	public int unknownInt2;

	public short unknownShort2;

	public float unknownFloat1;

	public float unknownFloat2;

	public float unknownFloat3;

	public int unknownInt3;

	public int unknownInt4;

	public int unknownInt5;

	public int unknownInt7;

	public int unknownInt8;

	public byte[] unknownBytes1;

	public NifRef meshDescription;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);
		unknownQuat1 = new NifQuaternion(stream);
		unknownQuat2 = new NifQuaternion(stream);
		unknownQuat3 = new NifQuaternion(stream);
		unknownShort1 = ByteConvert.readShort(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownShort2 = ByteConvert.readShort(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownInt3 = ByteConvert.readInt(stream);
		unknownInt4 = ByteConvert.readInt(stream);
		unknownInt5 = ByteConvert.readInt(stream);
		unknownInt7 = ByteConvert.readInt(stream);
		unknownInt8 = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_3_0_6)
		{
			unknownBytes1 = ByteConvert.readBytes(8, stream);
		}
		meshDescription = new NifRef(NiPhysXMeshDesc.class, stream);

		return success;
	}
}