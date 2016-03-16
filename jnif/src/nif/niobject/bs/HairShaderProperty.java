package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class HairShaderProperty extends BSShaderProperty
{
	/**
	 
	 <niobject name="HairShaderProperty" abstract="0" inherit="BSShaderProperty" ver1="20.2.0.7" userver="11">
	 Bethesda-specific node.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}