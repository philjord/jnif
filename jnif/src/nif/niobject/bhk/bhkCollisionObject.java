package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class bhkCollisionObject extends bhkNiCollisionObject
{
	/*
	 
	 <niobject name="bhkCollisionObject" abstract="0" inherit="bhkNiCollisionObject">
	 Havok related collision object?
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}