package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiControllerSequence;
import nif.niobject.NiDefaultAVObjectPalette;

public class NiControllerManager extends NiTimeController
{
	/**
	 <niobject name="NiControllerManager" abstract="0" inherit="NiTimeController" ver1="20.0.0.5">

	 Unknown. Root of all controllers?
	 
	 <add name="Cumulative" type="bool">
	 Designates whether animation sequences are cumulative?
	 </add>
	 <add name="Num Controller Sequences" type="uint">The number of controller sequence objects.</add>
	 <add name="Controller Sequences" type="Ref" template="NiControllerSequence" arr1="Num Controller Sequences">Refers to a list of NiControllerSequence object.</add>
	 <add name="Object Palette" type="Ref" template="NiDefaultAVObjectPalette">Refers to a NiDefaultAVObjectPalette.</add>
	 </niobject>
	 */

	public boolean cumulative;

	public int numControllerSequences;

	public NifRef[] controllerSequences;

	public NifRef objectPalette;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		cumulative = ByteConvert.readBool(stream, nifVer);
		numControllerSequences = ByteConvert.readInt(stream);
		controllerSequences = new NifRef[numControllerSequences];
		for (int i = 0; i < numControllerSequences; i++)
		{
			controllerSequences[i] = new NifRef(NiControllerSequence.class, stream);
		}
		objectPalette = new NifRef(NiDefaultAVObjectPalette.class, stream);

		return success;
	}
}
