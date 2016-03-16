package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class NiTriBasedGeom extends NiGeometry
{
	/**
	 <niobject name="NiTriBasedGeom" abstract="1" inherit="NiGeometry">

	 Describes a mesh, built from triangles.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
	

}