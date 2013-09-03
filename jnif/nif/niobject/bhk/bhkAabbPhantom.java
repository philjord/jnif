package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class bhkAabbPhantom extends bhkShapePhantom
{
	/**
	 <niobject name="bhkAabbPhantom" inherit="bhkShapePhantom">

	 Bethesda-specific node.
	 
	 <add name="Unknown Ints 1" type="int" arr1="15"/>
	 </niobject>
	 */

	public int[] unknownInts1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInts1 = ByteConvert.readInts(15, stream);

		return success;
	}
}