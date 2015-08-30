package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiKeyframeData;

public class NiKeyframeController extends NiSingleInterpController
{
	/**
	 <niobject name="NiKeyframeController" abstract="0" inherit="NiSingleInterpController">

	 A time controller object for animation key frames.
	 
	 <add name="Data" type="Ref" template="NiKeyframeData" ver2="10.1.0.0">Keyframe controller data index.</add>
	 </niobject>
	 */
	
	public NifRef data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			data = new NifRef(NiKeyframeData.class, stream);
		}
		return success;
	}
}