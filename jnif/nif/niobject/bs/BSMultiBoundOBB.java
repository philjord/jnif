package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.compound.NifMatrix33;
import nif.compound.NifVector3;

public class BSMultiBoundOBB extends BSMultiBoundData
{
	/**
	 

	    <niobject name="BSMultiBoundOBB" inherit="BSMultiBoundData">
	        Oriented bounding box.
	        <add name="Center" type="Vector3">Center of the box.</add>
	        <add name="Size" type="Vector3">Size of the box along each axis.</add>
	        <add name="Rotation" type="Matrix33">Rotation of the bounding box.</add>
	    </niobject>
	 */

	public NifVector3 Center;

	public NifVector3 Size;

	public NifMatrix33 Rotation;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		Center = new NifVector3(stream);
		Size = new NifVector3(stream);
		Rotation = new NifMatrix33(stream);

		return success;
	}
}
