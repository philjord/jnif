package nif.niobject.bhk;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiTimeController;

public class bhkBlendController extends NiTimeController
{
	/**
	 <niobject name="bhkBlendController" abstract="0" inherit="NiTimeController" ver1="20.0.0.5">

	 Unknown. Is apparently only used in skeleton.nif files.
	 
	 <add name="Unknown Int" type="uint">Seems to be always zero.</add>
	 </niobject>
	 */

	public int unknownInt;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt = ByteConvert.readInt(stream);

		return success;
	}
}