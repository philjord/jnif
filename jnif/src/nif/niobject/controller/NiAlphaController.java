package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiFloatData;

public class NiAlphaController extends NiFloatInterpController
{
	/**
	 <niobject name="NiAlphaController" abstract="0" inherit="NiFloatInterpController">

	 Time controller for transparency.
	 
	 <add name="Data" type="Ref" template="NiFloatData" ver2="10.1.0.0">Alpha controller data index.</add>
	 </niobject>
	 
	 */

	public NifRef Data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			Data = new NifRef(NiFloatData.class, stream);
		}
		return success;
	}
}