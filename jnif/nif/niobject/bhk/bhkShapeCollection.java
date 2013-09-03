package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public abstract class bhkShapeCollection extends bhkShape
{

	/**
	 <niobject name="bhkShapeCollection" abstract="1" inherit="bhkShape">

	 Havok collision object that uses multiple shapes?
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}