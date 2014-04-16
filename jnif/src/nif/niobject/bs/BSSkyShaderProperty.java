package nif.niobject.bs;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifTexCoord;
import nif.enums.SkyObjectType;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.niobject.NiProperty;

public class BSSkyShaderProperty extends NiProperty
{
	/**
	 
	    <niobject name="BSSkyShaderProperty" inherit="NiProperty">
	        Skyrim Sky shader block.
	        <add name="Shader Flags 1" type="SkyrimShaderPropertyFlags1"></add>
	        <add name="Shader Flags 2" type="SkyrimShaderPropertyFlags2"></add>
	        <add name="UV Offset" type="TexCoord">Offset UVs. Seems to be unused, but it fits with the other Skyrim shader properties.</add>
	        <add name="UV Scale" type="TexCoord" default="1.0, 1.0">Offset UV Scale to repeat tiling textures, see above.</add>
	        <add name="Source Texture" type="SizedString">points to an external texture.</add>
	        <add name="Sky Object Type" type="SkyObjectType">Sky Object Type</add>
	    </niobject>
	 */

	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;

	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public String SourceTexture;

	public SkyObjectType SkyObjectType;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);

		ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);

		UVOffSet = new NifTexCoord(stream);

		UVScale = new NifTexCoord(stream);

		SourceTexture = ByteConvert.readSizedString(stream);

		SkyObjectType = new SkyObjectType(stream);

		return success;
	}
}
