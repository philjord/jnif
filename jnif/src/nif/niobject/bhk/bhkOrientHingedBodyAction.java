package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class bhkOrientHingedBodyAction extends bhkSerializable
{
	/**
	 <niobject name="bhkOrientHingedBodyAction" abstract="0" inherit="bhkSerializable" ver1="20.0.0.5">

	 Bethesda-Specific node.
	 
	 <add name="Unknown Ints 1" type="int" arr1="17"/>
	 </niobject>
	 */

	public int[] unknownInts1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInts1 = ByteConvert.readInts(17, stream);

		return success;
	}
}