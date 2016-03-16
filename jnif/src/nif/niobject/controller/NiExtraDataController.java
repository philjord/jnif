package nif.niobject.controller;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class NiExtraDataController extends NiSingleInterpController
{
	/**
	 <niobject name="NiExtraDataController" abstract="1" inherit="NiSingleInterpController" ver1="10.2.0.0">
	 An controller for extra data.
	 </niobject> 
	 
	 */

	public String controllerData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}