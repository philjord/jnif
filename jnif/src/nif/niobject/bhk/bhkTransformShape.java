package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifMatrix44;
import nif.enums.HavokMaterial;

public class bhkTransformShape extends bhkShape
{
	/**
	 
	 <niobject name="bhkTransformShape" abstract="0" inherit="bhkShape">

	 Transforms a shape.
	 
	 <add name="Shape" type="Ref" template="bhkShape">The shape that this object transforms.</add>
	 <add name="Material" type="HavokMaterial">The shape's material.</add>
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown 8 Bytes" type="byte" arr1="8">Unknown.</add>
	 <add name="Transform" type="Matrix44">A transform matrix.</add>
	 </niobject>
	 
	 */

	public NifRef shape;

	public HavokMaterial material;

	public float unknownFloat1;

	public byte[] unknown8Bytes;

	public NifMatrix44 transform;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		shape = new NifRef(bhkShape.class, stream);
		material = new HavokMaterial(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknown8Bytes = ByteConvert.readBytes(8, stream);
		transform = new NifMatrix44(stream);
		return success;
	}
}