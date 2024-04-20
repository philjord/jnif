package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class BSShaderNoLightingProperty extends BSShaderLightingProperty
{
	/**
	 
	 <niobject name="BSShaderNoLightingProperty" abstract="0" inherit="BSShaderLightingProperty" ver1="20.2.0.7" userver="11">

	 Bethesda-specific property.
	 
	 <add name="File Name" type="SizedString">The texture glow map.</add>
	 <add name="Unknown Float 2" type="float" default="1.0" vercond="(User Version == 11) && (User Version 2 > 26)">Unknown</add>
	 <add name="Unknown Float 3" type="float" default="0.0" vercond="(User Version == 11) && (User Version 2 > 26)">

	 Normally what appears to be a junk value (0xB33BBD2E). Appears to be related to glow when Unk Flags is (0x82000148).
	 
	 </add>
	 <add name="Unknown Float 4" type="float" default="1.0" vercond="(User Version == 11) && (User Version 2 > 26)">Unknown</add>
	 <add name="Unknown Float 5" type="float" default="0.0" vercond="(User Version == 11) && (User Version 2 > 26)">Unknown</add>
	 </niobject>
	 */

	public String fileName;

	public float unknownFloat2;

	public float unknownFloat3;

	public float unknownFloat4;

	public float unknownFloat5;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		fileName = ByteConvert.readSizedString(stream);

		if ((nifVer.LOAD_USER_VER == 11||nifVer.LOAD_USER_VER == 12) && nifVer.BS_Version > 26)
		{
			unknownFloat2 = ByteConvert.readFloat(stream);
			unknownFloat3 = ByteConvert.readFloat(stream);
			unknownFloat4 = ByteConvert.readFloat(stream);
			unknownFloat5 = ByteConvert.readFloat(stream);
		}

		return success;
	}
}