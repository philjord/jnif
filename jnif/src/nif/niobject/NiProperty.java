package nif.niobject;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.niobject.controller.NiObjectNET;

public abstract class NiProperty extends NiObjectNET
{
	/**
	    <niobject name="NiProperty" abstract="true" inherit="NiObjectNET" module="NiMain">
        Abstract base class representing all rendering properties. Subclasses are attached to NiAVObjects to control their rendering.
    </niobject>
	 */

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}