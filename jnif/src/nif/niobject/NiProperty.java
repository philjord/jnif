package nif.niobject;

import java.io.InputStream;

import nif.NifVer;
import nif.niobject.controller.NiObjectNET;

public abstract class NiProperty extends NiObjectNET
{
	/**
	 <niobject name="NiProperty" abstract="1" inherit="NiObjectNET">
	 A generic property object.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}