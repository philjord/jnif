package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiNode;

public class NiLookAtController extends NiTimeController
{
	/**
	 <niobject name="NiLookAtController" abstract="0" inherit="NiTimeController" ver1="4.1.0.12">

	 Unknown. Start time is 3.4e+38 and stop time is -3.4e+38.
	 
	 <add name="Unknown1" type="ushort" ver1="10.1.0.0">Unknown.</add>
	 <add name="Look At Node" type="Ref" template="NiNode">Link to the node to look at?</add>
	 </niobject>
	 */

	public short unknown1;

	public NifRef lookAtNode;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknown1 = ByteConvert.readShort(stream);
		lookAtNode = new NifRef(NiNode.class, stream);

		return success;
	}
}