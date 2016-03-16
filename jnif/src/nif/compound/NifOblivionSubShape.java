package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.enums.HavokMaterial;
import nif.enums.OblivionLayer;

public class NifOblivionSubShape
{
	/**
	 <compound name="OblivionSubShape">

	 Havok Information for packed TriStrip shapes.
	 
	 <add name="Layer" type="OblivionLayer">Sets mesh color in Oblivion Construction Set.</add>
	 <add name="Col Filter" type="byte">
	 The first bit sets the LINK property and controls whether this body is physically linked to others. The next bit turns collision off.
	  Then, the next bit sets the SCALED property in Oblivion. The next five bits make up the number of this part in a linked body list.
	 </add>
	 <add name="Wielding Type?" type="ushort">Vertex Wielding Type?</add>
	 <add name="Num Vertices" type="uint">The number of vertices that form this sub shape.</add>
	 <add name="Material" type="HavokMaterial">The material of the subshape.</add>
	 </compound>			
	 */

	public OblivionLayer layer;

	public byte colFilter;

	public short wieldingType;

	public int numVertices;

	public HavokMaterial material;

	public NifOblivionSubShape(ByteBuffer stream) throws IOException
	{
		layer = new OblivionLayer(stream);
		colFilter = ByteConvert.readByte(stream);
		wieldingType = ByteConvert.readShort(stream);
		numVertices = ByteConvert.readInt(stream);
		material = new HavokMaterial(stream);
	}
}
