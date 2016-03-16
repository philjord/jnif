package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifQuaternion;

public class NiPhysXActorDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXActorDesc" abstract="0" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Quat 1" type="Quaternion">Unknown</add>
	 <add name="Unknown Quat 2" type="Quaternion">Unknown</add>
	 <add name="Unknown Quat 3" type="Quaternion">Unknown</add>
	 <add name="Unknown Int 3" type="int">Unknown</add>
	 <add name="Unknown Int 4" type="float">Unknown</add>
	 <add name="Unknown Int 5" type="int">Unknown</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 <add name="Unknown Byte 2" type="byte">Unknown</add>
	 <add name="Unknown Int 6" type="int">Unknown</add>
	 <add name="Shape Description" type="Ref" template="NiPhysXShapeDesc">PhysX Shape Description</add>
	 <add name="Unknown Ref 1" type="Ref" template="NiObject">Unknown</add>
	 <add name="Unknown Ref 2" type="Ref" template="NiObject">Unknown</add>
	 <add name="Unknown Refs 3" type="Ref" template="NiObject" arr1="Unknown Int 6">Unknown</add>
	 </niobject>
	 */

	public int unknownInt1;

	public int unknownInt2;

	public NifQuaternion unknownQuat1;

	public NifQuaternion unknownQuat2;

	public NifQuaternion unknownQuat3;

	public int unknownInt3;

	public int unknownInt4;

	public int unknownInt5;

	public byte unknownByte1;

	public byte unknownByte2;

	public int unknownInt6;

	public NifRef unknownRef1;

	public NifRef unknownRef2;

	public NifRef[] unknownRefs3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);

		unknownInt2 = ByteConvert.readInt(stream);

		unknownQuat1 = new NifQuaternion(stream);

		unknownQuat2 = new NifQuaternion(stream);

		unknownQuat3 = new NifQuaternion(stream);

		unknownInt3 = ByteConvert.readInt(stream);

		unknownInt4 = ByteConvert.readInt(stream);

		unknownInt5 = ByteConvert.readInt(stream);

		unknownByte1 = ByteConvert.readByte(stream);

		unknownByte2 = ByteConvert.readByte(stream);

		unknownInt6 = ByteConvert.readInt(stream);

		unknownRef1 = new NifRef(NiObject.class, stream);

		unknownRef2 = new NifRef(NiObject.class, stream);

		unknownRefs3 = new NifRef[unknownInt6];
		for (int i = 0; i < unknownInt6; i++)
		{
			unknownRefs3[i] = new NifRef(NiObject.class, stream);
		}

		return success;
	}
}