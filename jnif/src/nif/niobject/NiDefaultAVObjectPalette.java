package nif.niobject;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifAVObject;

public class NiDefaultAVObjectPalette extends NiAVObjectPalette
{
	/**
	 <niobject name="NiDefaultAVObjectPalette" abstract="0" inherit="NiAVObjectPalette">

	 Unknown. Refers to a list of objects. Used by NiControllerManager.
	 
	 <add name="Unknown Int" type="uint">Unknown.</add>
	 <add name="Num Objs" type="uint">Number of objects.</add>
	 <add name="Objs" type="AVObject" arr1="Num Objs">The objects.</add>
	 </niobject>
	 */

	public int unknownInt;

	public int numObjs;

	public NifAVObject[] objs;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt = ByteConvert.readInt(stream);
		numObjs = ByteConvert.readInt(stream);
		objs = new NifAVObject[numObjs];
		for (int i = 0; i < numObjs; i++)
		{
			objs[i] = new NifAVObject(stream);
		}

		return success;
	}
}