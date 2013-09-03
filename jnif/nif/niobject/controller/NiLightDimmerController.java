package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiLightDimmerController extends NiFloatInterpController
{
	/**
	 <niobject name="NiLightDimmerController" abstract="0" inherit="NiFloatInterpController" ver1="20.0.0.4">
	 Unknown controller.
	 </niobject>
	 
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
