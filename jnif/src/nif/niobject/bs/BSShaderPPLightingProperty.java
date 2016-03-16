package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class BSShaderPPLightingProperty extends BSShaderLightingProperty
{
	/**
	 
	 <niobject name="BSShaderPPLightingProperty" abstract="0" inherit="BSShaderLightingProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific Shade node.
	 
	 <add name="Texture Set" type="Ref" template="BSShaderTextureSet">Texture Set</add>
	 <add name="Unknown Float 2" type="float" default="0.0" vercond="(User Version == 11) && (User Version 2 > 14)">Unknown</add>
	 <add name="refractionPeriod" type="float" default="0.0" vercond="(User Version == 11) && (User Version 2 > 14)">Unknown</add>
	 <add name="Unknown Float 4" type="float" default="4.0" vercond="(User Version == 11) && (User Version 2 > 24)">Unknown</add>
	 <add name="Unknown Float 5" type="float" default="1.0" vercond="(User Version == 11) && (User Version 2 > 24)">Unknown</add>
	 </niobject>
	 */

	public NifRef textureSet;

	public float unknownFloat2;

	public float refractionPeriod;

	public float unknownFloat4;

	public float unknownFloat5;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		textureSet = new NifRef(BSShaderTextureSet.class, stream);

		if ((nifVer.LOAD_USER_VER == 11 ) && nifVer.LOAD_USER_VER2 > 24)
		{
			unknownFloat2 = ByteConvert.readFloat(stream);
			refractionPeriod = ByteConvert.readFloat(stream);
			unknownFloat4 = ByteConvert.readFloat(stream);
			unknownFloat5 = ByteConvert.readFloat(stream);
		}

		return success;
	}
}