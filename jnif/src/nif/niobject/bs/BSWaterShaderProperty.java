package nif.niobject.bs;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifTexCoord;
import nif.enums.SkyrimShaderPropertyFlags1;
import nif.enums.SkyrimShaderPropertyFlags2;
import nif.enums.WaterShaderPropertyFlags;
import nif.niobject.NiProperty;

public class BSWaterShaderProperty extends NiProperty
{
	/**
	    <niobject name="BSWaterShaderProperty" inherit="BSShaderProperty" module="BSMain" versions="#SKY_AND_LATER#">
        Skyrim water shader property, different from "WaterShaderProperty" seen in Fallout.
        <field name="Shader Flags 1" type="SkyrimShaderPropertyFlags1" default="0x80000008" vercond="!#BS_GTE_132#" />
        <field name="Shader Flags 2" type="SkyrimShaderPropertyFlags2" default="0x21" vercond="!#BS_GTE_132#" />
        <field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
        <field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
        <field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
        <field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
        <field name="UV Offset" type="TexCoord">Offset UVs. Seems to be unused, but it fits with the other Skyrim shader properties.</field>
        <field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see above.</field>
        <field name="Water Shader Flags" type="WaterShaderPropertyFlags" default="0xC4" />
    </niobject>
	 */

	public SkyrimShaderPropertyFlags1 ShaderFlags1;

	public SkyrimShaderPropertyFlags2 ShaderFlags2;
	public int NumSF1;
	public int NumSF2;
	public int[] SF1;
	public int[] SF2;
	
	public NifTexCoord UVOffSet;

	public NifTexCoord UVScale;

	public WaterShaderPropertyFlags WaterShaderFlags;

	public byte WaterDirection;

	public short UnknownShort3;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		 
		
		if(!nifVer.BS_GTE_132()) {
			//<field name="Shader Flags 1" type="SkyrimShaderPropertyFlags1" default="0x80000008" vercond="!#BS_GTE_132#" />
			ShaderFlags1 = new SkyrimShaderPropertyFlags1(stream);
			//<field name="Shader Flags 2" type="SkyrimShaderPropertyFlags2" default="0x21" vercond="!#BS_GTE_132#" />
			ShaderFlags2 = new SkyrimShaderPropertyFlags2(stream);
		}
		
		//<field name="Num SF1" type="uint" vercond="#BS_GTE_132#" />
		if(nifVer.BS_GTE_132())
			NumSF1 = ByteConvert.readInt(stream);
        //<field name="Num SF2" type="uint" vercond="#BS_GTE_152#" />
		if(nifVer.BS_GTE_152())
			NumSF2 = ByteConvert.readInt(stream);
        //<field name="SF1" type="BSShaderCRC32" length="Num SF1" vercond="#BS_GTE_132#" />
		if(nifVer.BS_GTE_132())
			SF1 = ByteConvert.readInts(NumSF1, stream);
        //<field name="SF2" type="BSShaderCRC32" length="Num SF2" vercond="#BS_GTE_152#" />
		if(nifVer.BS_GTE_152())
			SF2 = ByteConvert.readInts(NumSF2, stream);
		
		//<field name="UV Offset" type="TexCoord">Offset UVs. Seems to be unused, but it fits with the other Skyrim shader properties.</field>
		UVOffSet = new NifTexCoord(stream);
		
		//<field name="UV Scale" type="TexCoord" default="#VEC2_ONE#">Offset UV Scale to repeat tiling textures, see above.</field>
		UVScale = new NifTexCoord(stream);
		
		 //<field name="Water Shader Flags" type="WaterShaderPropertyFlags" default="0xC4" />
		WaterShaderFlags = new WaterShaderPropertyFlags(stream);
	

		return success;
	}
}
