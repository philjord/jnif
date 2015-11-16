package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiLightRadiusController extends NiFloatInterpController
{
	 

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
