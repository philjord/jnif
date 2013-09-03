package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiMaterialColorController extends NiPoint3InterpController
{
	/**
	 <niobject name="NiMaterialColorController" abstract="0" inherit="NiPoint3InterpController">
	 Time controller for material color.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}