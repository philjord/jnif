package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSRefractionStrengthController extends NiFloatInterpController
{
	/**
	 
	 <niobject name="BSRefractionStrengthController" inherit="NiFloatInterpController" ver1="20.2.0.7" userver="11">
	 Bethesda-Specific node.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}