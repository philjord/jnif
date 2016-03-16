package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class BSFurnitureMarkerNode extends BSFurnitureMarker
{
	/**
	 	<niobject name="BSFurnitureMarkerNode" inherit="BSFurnitureMarker">
	    Furniture Marker for actors
	    </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
