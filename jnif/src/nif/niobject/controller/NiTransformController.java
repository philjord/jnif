package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiTransformController extends NiKeyframeController
{
	/**
	 <niobject name="NiTransformController" abstract="0" inherit="NiKeyframeController" ver1="10.2.0.0">

	 NiTransformController replaces the NiKeyframeController.
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}