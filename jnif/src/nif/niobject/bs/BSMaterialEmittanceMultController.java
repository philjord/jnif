package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSMaterialEmittanceMultController extends NiFloatInterpController
{
	/**
	 
	 <niobject name="BSMaterialEmittanceMultController" inherit="NiFloatInterpController" ver1="20.2.0.7" userver="11">
	 Bethesda-Specific node.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}