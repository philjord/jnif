package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class WaterShaderPropertyFlags
{
	/**
	 <bitflags name="WaterShaderPropertyFlags" storage="uint" prefix="BSWSP" versions="#SKY_AND_LATER#">
        Skyrim water shader property flags
        <option bit="0" name="DISPLACEMENT" />
        <option bit="1" name="LOD" />
        <option bit="2" name="DEPTH" />
        <option bit="3" name="ACTOR_IN_WATER" />
        <option bit="4" name="ACTOR_IN_WATER_IS_MOVING" />
        <option bit="5" name="UNDERWATER" />
        <option bit="6" name="REFLECTIONS" />
        <option bit="7" name="REFRACTIONS" />
        <option bit="8" name="VERTEX_UV" />
        <option bit="9" name="VERTEX_ALPHA_DEPTH" />
        <option bit="10" name="PROCEDURAL" />
        <option bit="11" name="FOG" />
        <option bit="12" name="UPDATE_CONSTANTS" />
        <option bit="13" name="CUBEMAP" />
    </bitflags>
	 */
	public int flags;

	public WaterShaderPropertyFlags(ByteBuffer stream) throws IOException
	{
		flags = ByteConvert.readInt(stream);
	}

	public boolean isBitSet(int bitToMask)
	{
		return (flags & (0x1 << bitToMask)) != 0;
	}
}
