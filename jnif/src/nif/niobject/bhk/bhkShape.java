package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class bhkShape extends bhkSerializable
{
	/**
	 
	 <niobject name="bhkShape" abstract="1" inherit="bhkSerializable">
	 A Havok Shape?
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}