package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;

public class NiKeyframeController extends NiSingleInterpController
{
	/**
	 <niobject name="NiKeyframeController" abstract="0" inherit="NiSingleInterpController">

	 A time controller object for animation key frames.
	 
	 <add name="Data" type="Ref" template="NiKeyframeData" ver2="10.1.0.0">Keyframe controller data index.</add>
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}