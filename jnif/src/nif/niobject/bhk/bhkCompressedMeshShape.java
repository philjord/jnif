package nif.niobject.bhk;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.compound.NifVector4;
import nif.enums.HavokMaterial;
import nif.niobject.NiAVObject;

public class bhkCompressedMeshShape extends bhkShape
{
	/**
	 *  <niobject name="bhkCompressedMeshShape" inherit="bhkShape">
	    Compressed collision mesh.
	    <add name="Target" type="Ptr" template="NiAVObject">Points to root node?</add>
	    <add name="Material" type="HavokMaterial">The shape&#039;s material.</add>
	    <add name="Unknown Float 1" type="float">Unknown.</add>
	    <add name="Unknown 4 Bytes" type="byte" arr1="4">Unknown.</add>
		<add name="Unknown Floats 1" type="Vector4">Unknown</add>
	    <add name="Unknown Floats 2" type="Vector4">Unknown</add>
	    <add name="Unknown Float  1" type="float">Unknown</add>
		<add name="Data" type="Ref" template="bhkCompressedMeshShapeData">The collision mesh data.</add>
	</niobject>

	 */
	public NifPtr target = null;

	public HavokMaterial material = null;

	public float UnknownFloat1 = 0;

	public byte[] Unknown4Bytes = null;

	public NifVector4 UnknownFloats1 = null;

	public NifVector4 UnknownFloats2 = null;

	public float UnknownFloat2 = 0;

	public NifRef data = null;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		target = new NifPtr(NiAVObject.class, stream);
		material = new HavokMaterial(stream);
		UnknownFloat1 = ByteConvert.readFloat(stream);
		Unknown4Bytes = ByteConvert.readBytes(4, stream);
		UnknownFloats1 = new NifVector4(stream);
		UnknownFloats2 = new NifVector4(stream);
		UnknownFloat2 = ByteConvert.readFloat(stream);
		data = new NifRef(bhkCompressedMeshShapeData.class, stream);

		return success;
	}
}
