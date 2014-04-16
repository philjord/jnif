package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiTriStrips extends NiTriBasedGeom
{

	/**
	 <niobject name="NiTriStrips" abstract="0" inherit="NiTriBasedGeom">

	 A shape node that refers to data organized into strips of triangles
	 
	 </niobject>
	 */
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}
