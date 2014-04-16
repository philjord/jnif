package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiBSBoneLODController extends NiBoneLODController
{
	/**
	 <niobject name="NiBSBoneLODController" abstract="0" inherit="NiBoneLODController">
	 A simple LOD controller for bones.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}