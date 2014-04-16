package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public abstract class BSShaderLightingProperty extends BSShaderProperty
{
	/**
	 
	 <niobject name="BSShaderLightingProperty" abstract="1" inherit="BSShaderProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific property.
	 
	 <add name="Unknown Int 3" type="int" default="3">Unknown</add>
	 </niobject>
	 */

	public int unknownInt3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt3 = ByteConvert.readInt(stream);

		return success;
	}
}