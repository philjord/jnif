package nif.niobject;

import java.io.InputStream;

import nif.NifVer;
import nif.niobject.controller.NiObjectNET;

public class NiSequenceStreamHelper extends NiObjectNET
{
	/**
	 <niobject name="NiSequenceStreamHelper" abstract="0" inherit="NiObjectNET">

	 Keyframe animation root node, in .kf files.
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{

		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}