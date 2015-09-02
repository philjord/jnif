package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiVisData;

public class NiVisController extends NiBoolInterpController
{
	/**
	 <niobject name="NiVisController" abstract="0" inherit="NiBoolInterpController">

	 Time controller for visibility.
	 
	 <add name="Data" type="Ref" template="NiVisData" ver2="10.1.0.0">Visibility controller data object index.</add>
	 </niobject>
	 
	 */

	public NifRef Data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			Data = new NifRef(NiVisData.class, stream);
		}
		return success;
	}
}