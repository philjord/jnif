package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifOblivionColFilter;
import nif.compound.NifVector3;
import nif.enums.HavokMaterial;
import nif.niobject.NiTriStripsData;

public class bhkNiTriStripsShape extends bhkShapeCollection
{
	/**
	 <niobject name="bhkNiTriStripsShape" abstract="0" inherit="bhkShapeCollection">

	 A shape constructed from a bunch of strips.
	 
	 <add name="Material" type="HavokMaterial">The shape's material.</add>
	 <add name="Unknown Float 1" type="float" default="0.1">Unknown.</add>
	 <add name="Unknown Int 1" type="uint" default="0x004ABE60">Unknown.</add>
	 <add name="Unknown Ints 1" type="uint" arr1="4">Unknown.</add>
	 <add name="Unknown Int 2" type="uint" default="1">Unknown</add>
	 <add name="Scale" type="Vector3" default="1.0f, 1.0f, 1.0f">Scale. Usually (1.0, 1.0, 1.0).</add>
	 <add name="Unknown Int 3" type="uint">Unknown.</add>
	 <add name="Num Strips Data" type="uint">The number of strips data objects referenced.</add>
	 <add name="Strips Data" type="Ref" template="NiTriStripsData" arr1="Num Strips Data">
	 Refers to a bunch of NiTriStripsData objects that make up this shape.
	 </add>
	 <add name="Num Data Layers" type="uint">
	 Number of Havok Layers, equal to Number of strips data objects.
	 </add>
	 <add name="Data Layers" type="OblivionColFilter" arr1="Num Data Layers">Havok Layers for each strip data.</add>
	 </niobject>
	 */
	public HavokMaterial material;

	public float[] unknown7Floats1;

	public NifVector3 scale;

	public int unknownInt2;

	public int numStripsData;

	public NifRef[] stripsData;

	public int numDataLayers;

	public NifOblivionColFilter[] dataLayers;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		material = new HavokMaterial(stream);

		unknown7Floats1 = ByteConvert.readFloats(7, stream);

		scale = new NifVector3(stream);

		unknownInt2 = ByteConvert.readInt(stream);
		numStripsData = ByteConvert.readInt(stream);
		stripsData = new NifRef[numStripsData];
		for (int i = 0; i < numStripsData; i++)
		{
			stripsData[i] = new NifRef(NiTriStripsData.class, stream);
		}
		numDataLayers = ByteConvert.readInt(stream);
		dataLayers = new NifOblivionColFilter[numDataLayers];
		for (int i = 0; i < numDataLayers; i++)
		{
			dataLayers[i] = new NifOblivionColFilter(stream);
		}

		return success;
	}
}