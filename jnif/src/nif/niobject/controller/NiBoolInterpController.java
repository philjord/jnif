package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public abstract class NiBoolInterpController extends NiSingleInterpController
{
	/**
	 
	 <niobject name="NiBoolInterpController" abstract="1" inherit="NiSingleInterpController" ver1="10.2.0.0">

	 A controller that interpolates floating point numbers?
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}