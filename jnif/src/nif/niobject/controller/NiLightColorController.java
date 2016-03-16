package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;

public class NiLightColorController extends NiPoint3InterpController
{
	/**
	 <niobject name="NiLightColorController" abstract="0" inherit="NiPoint3InterpController" ver1="4.2.0.2">
	 Light color animation controller.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}