package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public abstract class NiInterpController extends NiTimeController
{
	/**
	 <niobject name="NiInterpController" abstract="1" inherit="NiTimeController">

	 A controller capable of interpolation?
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}