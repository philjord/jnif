package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSMeshLODTriShape extends BSTriShape
{
	public int LOD0Size;
	public int LOD1Size;
	public int LOD2Size;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		LOD0Size = ByteConvert.readInt(stream);
		LOD1Size = ByteConvert.readInt(stream);
		LOD2Size = ByteConvert.readInt(stream);

		return success;
	}
}
