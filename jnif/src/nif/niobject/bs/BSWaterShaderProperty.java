package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifTexCoord;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.SkyrimWaterShaderFlags;
import nif.niobject.NiProperty;

public class BSWaterShaderProperty extends NiProperty
{
	/**
	     <niobject name="BSWaterShaderProperty" inherit="NiProperty">
	        Skyrim water shader property, different from "WaterShaderProperty" seen in Fallout.
	        <add name="Shader Flags 1" type="SkyrimShaderPropertyFlags1"></add>
	        <add name="Shader Flags 2" type="SkyrimShaderPropertyFlags2"></add>
	        <add name="UV Offset" type="TexCoord">Offset UVs. Seems to be unused, but it fits with the other Skyrim shader properties.</add>
	        <add name="UV Scale" type="TexCoord" default="1.0, 1.0">Offset UV Scale to repeat tiling textures, see above.</add>
	        <add name="Water Shader Flags" type="SkyrimWaterShaderFlags">Defines attributes for the water shader (will use SkyrimWaterShaderFlags)</add>
	        <add name="Water Direction" type="byte" default="3">A bitflag, only the first/second bit controls water flow positive or negative along UVs.</add>
	        <add name="Unknown Short 3" type="ushort">Unknown, flag?</add>  
	    </niobject>
	 */

	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;

	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public SkyrimWaterShaderFlags WaterShaderFlags;

	public byte WaterDirection;

	public short UnknownShort3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);

		ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);

		UVOffSet = new NifTexCoord(stream);

		UVScale = new NifTexCoord(stream);

		WaterShaderFlags = new SkyrimWaterShaderFlags(stream);

		WaterDirection = ByteConvert.readByte(stream);

		UnknownShort3 = ByteConvert.readShort(stream);

		return success;
	}
}
