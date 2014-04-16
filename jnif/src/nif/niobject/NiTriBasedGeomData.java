package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public abstract class NiTriBasedGeomData extends NiGeometryData
{
	/**
	 
	 <niobject name="NiTriBasedGeomData" abstract="1" inherit="NiGeometryData">

	 Describes a mesh, built from triangles.
	 
	 <add name="Num Triangles" type="ushort">Number of triangles.</add>
	 </niobject>	
	 */

	public int numTriangles;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numTriangles = ByteConvert.readUnsignedShort(stream);
		return success;
	}

}