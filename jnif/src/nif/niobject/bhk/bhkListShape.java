package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.enums.HavokMaterial;

public class bhkListShape extends bhkShapeCollection
{
	/**
	 <niobject name="bhkListShape" abstract="0" inherit="bhkShapeCollection">

	 A list of shapes.

	 Do not put a bhkPackedNiTriStripsShape in the Sub Shapes. Use a
	 separate collision nodes without a list shape for those.

	 Also, shapes collected in a bhkListShape may not have the correct
	 walking noise, so only use it for non-walkable objects.
	 
	 <add name="Num Sub Shapes" type="uint">The number of sub shapes referenced.</add>
	 <add name="Sub Shapes" type="Ref" template="bhkShape" arr1="Num Sub Shapes">List of shapes.</add>
	 <add name="Material" type="HavokMaterial">The shape's material.</add>
	 <add name="Unknown Floats" type="float" arr1="6">
	 Unknown. Set to (0.0,0.0,-0.0,0.0,0.0,-0.0), where -0.0 is 0x80000000 in hex.
	 </add>
	 <add name="Num Unknown Ints" type="uint">Count.</add>
	 <add name="Unknown Ints" type="uint" arr1="Num Unknown Ints">Unknown.</add>
	 </niobject>
	 */

	public int numSubShapes;

	public NifRef[] subShapes;

	public HavokMaterial material;

	public float[] unknown6Floats;

	public int numUnknownInts;

	public int[] unknownInts;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numSubShapes = ByteConvert.readInt(stream);
		subShapes = new NifRef[numSubShapes];
		for (int i = 0; i < numSubShapes; i++)
		{
			subShapes[i] = new NifRef(bhkShape.class, stream);
		}
		material = new HavokMaterial(stream);
		unknown6Floats = ByteConvert.readFloats(6, stream);
		numUnknownInts = ByteConvert.readInt(stream);
		unknownInts = ByteConvert.readInts(numUnknownInts, stream);

		return success;
	}
}