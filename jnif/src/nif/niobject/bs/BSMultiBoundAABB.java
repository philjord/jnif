package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifVector3;

public class BSMultiBoundAABB extends BSMultiBoundData
{
	/**
	 
	<niobject name="BSMultiBoundAABB" inherit="BSMultiBoundData">
	    Bethesda-specific node.
	    <add name="Position" type="Vector3">Position of the AABB's center</add>
	    <add name="Extent" type="Vector3">Extent of the AABB in all directions</add>
	</niobject>
	 */

	public NifVector3 Position;

	public NifVector3 Extent;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		Position = new NifVector3(stream);
		Extent = new NifVector3(stream);
		return success;
	}
}