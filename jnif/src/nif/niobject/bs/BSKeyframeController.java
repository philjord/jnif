package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiKeyframeData;
import nif.niobject.controller.NiKeyframeController;

public class BSKeyframeController extends NiKeyframeController
{
	/**
	 <niobject name="BSKeyframeController" abstract="0" inherit="NiKeyframeController" ver1="10.0.1.2">

	 An extended keyframe controller.
	 
	 <add name="Data 2" type="Ref" template="NiKeyframeData">A link to more keyframe data.</add>
	 </niobject>
	 */

	public NifRef data2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		data2 = new NifRef(NiKeyframeData.class, stream);

		return success;
	}
}
