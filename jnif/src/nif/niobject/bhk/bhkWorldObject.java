package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.enums.OblivionLayer;

public abstract class bhkWorldObject extends bhkSerializable
{
	/**
	 
	 <niobject name="bhkWorldObject" abstract="1" inherit="bhkSerializable">

	 Havok objects that have a position in the world?
	 
	 <add name="Shape" type="Ref" template="bhkShape"> Link to the body for this collision object.</add>
	 <add name="Layer" type="OblivionLayer" default="1">Sets mesh color in Oblivion Construction Set.</add>
	 <add name="Col Filter" type="byte" default="0">
	 The first bit sets the LINK property and controls whether this body is physically linked to others. The next bit turns collision off. Then, the next bit sets the SCALED property in Oblivion. The next five bits make up the number of this part in a linked body list.
	 </add>
	 <add name="Unknown Short" type="ushort">Unknown.</add>
	 </niobject>
	 
	 */

	public NifRef shape;

	public OblivionLayer layer;

	public byte colFilter;

	public short unknownShort;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		shape = new NifRef(bhkShape.class, stream);
		layer = new OblivionLayer(stream);
		colFilter = ByteConvert.readByte(stream);
		unknownShort = ByteConvert.readShort(stream);
		return success;
	}
}