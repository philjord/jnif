package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSSegmentedTriangle;
import nif.niobject.NiTriShape;

public class BSSegmentedTriShape extends NiTriShape
{
	/**
	 
	 <niobject name="BSSegmentedTriShape" inherit="NiTriShape">

	 Bethesda-specific node.
	 
	 <add name="Num Seg Triangles" type="int">Unknown</add>
	 <add name="Seg Triangles" type="BSSegmentedTriangle" arr1="Num Seg Triangles">Unknown</add>
	 </niobject>
	 */

	public int numSegTriangles;

	public BSSegmentedTriangle[] segTriangles;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numSegTriangles = ByteConvert.readInt(stream);
		segTriangles = new BSSegmentedTriangle[numSegTriangles];
		for (int i = 0; i < numSegTriangles; i++)
		{
			segTriangles[i] = new BSSegmentedTriangle(stream);
		}

		return success;
	}
}