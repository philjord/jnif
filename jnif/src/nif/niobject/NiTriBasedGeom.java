package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class NiTriBasedGeom extends NiGeometry
{
	/**
	 <niobject name="NiTriBasedGeom" abstract="1" inherit="NiGeometry">

	 Describes a mesh, built from triangles.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}