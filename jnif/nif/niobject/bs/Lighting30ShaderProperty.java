package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class Lighting30ShaderProperty extends BSShaderPPLightingProperty
{
	/**
	 
	 <niobject name="Lighting30ShaderProperty" abstract="0" inherit="BSShaderPPLightingProperty" ver1="20.2.0.7" userver="11">
	 Bethesda-specific node.
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}