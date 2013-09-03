package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSMultiBoundSphere extends BSMultiBoundData
{
	/**
	 
	 
	<niobject name="BSMultiBoundSphere" inherit="BSMultiBoundData">
	    Bethesda-specific node.
	    <add name="Unknown Int 1" type="int">Unknown Flag</add>
	    <add name="Unknown Int 2" type="int">Unknown Flag</add>
	    <add name="Unknown Int 3" type="int">Unknown Flag</add>
	    <add name="Radius" type="float">Radius</add>
	</niobject>
	 */

	public int unknownInt1;

	public int unknownInt2;

	public int unknownInt3;

	public float radius;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownInt3 = ByteConvert.readInt(stream);
		radius = ByteConvert.readFloat(stream);

		return success;
	}
}