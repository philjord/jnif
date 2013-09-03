package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiVisController extends NiBoolInterpController
{
	/**
	 <niobject name="NiVisController" abstract="0" inherit="NiBoolInterpController">

	 Time controller for visibility.
	 
	 <add name="Data" type="Ref" template="NiVisData" ver2="10.1.0.0">Visibility controller data object index.</add>
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}