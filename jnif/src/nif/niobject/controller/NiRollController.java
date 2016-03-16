package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiFloatData;

public class NiRollController extends NiSingleInterpController
{
	/**
	 <niobject name="NiRollController" abstract="0" inherit="NiSingleInterpController">

	 Unknown.
	 
	 <add name="Data" type="Ref" template="NiFloatData">The data for the controller.</add>
	 </niobject>
	 */

	public NifRef data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data = new NifRef(NiFloatData.class, stream);
		return success;
	}
}