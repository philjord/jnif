package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifVector3;
import nif.niobject.NiExtraData;

public class BSBound extends NiExtraData
{
	/**
	 <niobject name="BSBound" abstract="0" inherit="NiExtraData" ver1="20.0.0.5">

	 Bethesda-specific collision bounding box for skeletons.
	 
	 <add name="Center" type="Vector3">Center of the bounding box.</add>
	 <add name="Dimensions" type="Vector3">Dimensions of the bounding box from center.</add>
	 </niobject>
	 */

	public NifVector3 center;

	public NifVector3 dimensions;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		center = new NifVector3(stream);
		dimensions = new NifVector3(stream);
		return success;
	}
}