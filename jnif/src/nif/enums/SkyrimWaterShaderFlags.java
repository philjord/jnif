package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SkyrimWaterShaderFlags
{
	/**
	 <bitflags name="SkyrimWaterShaderFlags" storage="byte">
        Skyrim water shader property flags
        <option value="0" name="SWSF1_UNKNOWN0">Unknown</option>
        <option value="1" name="SWSF1_Bypass_Refraction_Map">Bypasses refraction map when set to 1</option>
        <option value="2" name="SWSF1_Water_Toggle">Main water Layer on/off</option>
        <option value="3" name="SWSF1_UNKNOWN3">Unknown</option>
        <option value="4" name="SWSF1_UNKNOWN4">Unknown</option>
        <option value="5" name="SWSF1_UNKNOWN5">Unknown</option>
        <option value="6" name="SWSF1_Highlight_Layer_Toggle">Reflection layer 2 on/off. (is this scene reflection?)</option>
        <option value="7" name="SWSF1_Enabled">Water layer on/off</option>
    </bitflags>
	 */
	public byte flags;

	public SkyrimWaterShaderFlags(ByteBuffer stream) throws IOException
	{
		flags = ByteConvert.readByte(stream);
	}

	public boolean isBitSet(int bitToMask)
	{
		return (flags & (0x1 << bitToMask)) != 0;
	}
}
