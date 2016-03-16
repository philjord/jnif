package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiLightRadiusController extends NiFloatInterpController
{
	 

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}
