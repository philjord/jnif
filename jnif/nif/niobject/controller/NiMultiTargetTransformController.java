package nif.niobject.controller;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.niobject.NiNode;

public class NiMultiTargetTransformController extends NiInterpController
{
	/**
	 <niobject name="NiMultiTargetTransformController" abstract="0" inherit="NiInterpController" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Num Extra Targets" type="ushort">The number of target pointers that follow.</add>
	 <add name="Extra Targets" type="Ptr" template="NiAVObject" arr1="Num Extra Targets">NiNode Targets to be controlled.</add>
	 </niobject>
	 */

	public short numExtraTargets;

	public NifPtr[] extraTargets;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numExtraTargets = ByteConvert.readShort(stream);
		extraTargets = new NifPtr[numExtraTargets];
		for (int i = 0; i < numExtraTargets; i++)
		{
			extraTargets[i] = new NifPtr(NiNode.class, stream);
		}

		return success;
	}
}