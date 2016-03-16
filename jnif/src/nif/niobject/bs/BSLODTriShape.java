package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiTriBasedGeom;

public class BSLODTriShape extends NiTriBasedGeom
{
	/**
	 * 	<niobject name="BSLODTriShape" inherit="NiTriBasedGeom">
	    A variation on NiTriShape, for visibility control over vertex groups.
			<add name="Level 0 Size" type="uint">Unknown</add>
			<add name="Level 1 Size" type="uint">Unknown</add>
			<add name="Level 2 Size" type="uint">Unknown</add>
		</niobject>
	 */

	public float Level0Size;

	public float Level1Size;

	public float Level2Size;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		Level0Size = ByteConvert.readInt(stream);
		Level1Size = ByteConvert.readInt(stream);
		Level2Size = ByteConvert.readInt(stream);

		return success;
	}
}
