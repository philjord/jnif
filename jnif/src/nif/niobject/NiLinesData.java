package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiLinesData extends NiGeometryData
{
	/**
	 <niobject name="NiLinesData" inherit="NiGeometryData" ver1="4.2.2.0">

	 Wireframe geometry data.
	 
	 <add name="Lines" type="bool" arr1="Num Vertices">Is vertex connected to other (next?) vertex?</add>
	 </niobject>
	 */

	public boolean[] lines;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		lines = ByteConvert.readBooleans(numVertices, stream, nifVer);

		return success;
	}
}