package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class NiFloatInterpController extends NiSingleInterpController
{
	/**
	 <niobject name="NiFloatInterpController" abstract="1" inherit="NiSingleInterpController" ver1="10.2.0.0">

	 A controller that interpolates floating point numbers?
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}