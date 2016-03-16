package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class bhkBvTreeShape extends bhkShape
{

	/**
	 
	 <niobject name="bhkBvTreeShape" abstract="1" inherit="bhkShape">

	 A tree-like Havok data structure stored in an assembly-like binary code?
	 
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}