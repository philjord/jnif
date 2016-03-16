package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiSingleInterpController;

public abstract class NiPSysModifierCtlr extends NiSingleInterpController
{
	/**
	 <niobject name="NiPSysModifierCtlr" abstract="1" inherit="NiSingleInterpController" ver1="10.2.0.0">

	 A particle system modifier controller.
	 
	 <add name="Modifier Name" type="string">Refers to modifier object by its name?</add>
	 </niobject>
	 */

	public String modifierName;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		modifierName = ByteConvert.readIndexString(stream, nifVer);

		return success;
	}
}