package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifOblivionSubShape;
import nif.compound.NifVector3;

public class bhkPackedNiTriStripsShape extends bhkShapeCollection
{
	/**
	 
	 <niobject name="bhkPackedNiTriStripsShape" abstract="0" inherit="bhkShapeCollection" ver1="20.0.0.5">

	 A shape constructed from strips data.
	 
	 <add name="Num Sub Shapes" type="ushort" ver2="20.0.0.5">Number of subparts.</add>
	 <add name="Sub Shapes" type="OblivionSubShape" arr1="Num Sub Shapes" ver2="20.0.0.5">The subparts.</add>
	 <add name="Unknown Int 1" type="uint" default="0">Unknown.</add>
	 <add name="Unknown Int 2" type="uint" default="0x014E9DD8">
	 Unknown. Looks like a memory pointer and may be garbage.
	 </add>
	 <add name="Unknown Float 1" type="float" default="0.1">Unknown. Same as Unknown Float 3</add>
	 <add name="Unknown Int 3" type="uint" default="0">
	 Unknown. Sometimes 0.0f or -1.0f but sometimes really large number.  Suspect its garbage.
	 </add>
	 <add name="Scale Copy?" type="Vector3" default="1.0f, 1.0f, 1.0f">Unknown. Same as scale below?</add>
	 <add name="Unknown Float 2" type="float" default="0.0">
	 Unknown. Usually 0.0 but sometimes 1.0.  Same as Unknown Float 4
	 </add>
	 <add name="Unknown Float 3" type="float" default="0.1">Unknown. Same as Unknown Float 1</add>
	 <add name="Scale" type="Vector3" default="1.0f, 1.0f, 1.0f">Scale.</add>
	 <add name="Unknown Float 4" type="float" default="0.0">
	 Unknown. Usually 0.0 but sometimes 1.0.  Same as Unknown Float 2
	 </add>
	 <add name="Data" type="Ref" template="hkPackedNiTriStripsData">A link to the shape's NiTriStripsData.</add>
	 </niobject>
	 */

	public short numSubparts;

	public NifOblivionSubShape[] subparts;

	public float[] unknown9Floats1;

	public NifVector3 scale;

	public float unknownFloats4;

	public NifRef data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			numSubparts = ByteConvert.readShort(stream);
			subparts = new NifOblivionSubShape[numSubparts];
			for (int i = 0; i < numSubparts; i++)
			{
				subparts[i] = new NifOblivionSubShape(stream);
			}
		}
		unknown9Floats1 = ByteConvert.readFloats(9, stream);

		scale = new NifVector3(stream);
		unknownFloats4 = ByteConvert.readFloat(stream);

		data = new NifRef(hkPackedNiTriStripsData.class, stream);
		return success;
	}
}