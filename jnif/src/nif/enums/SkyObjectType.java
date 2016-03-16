package nif.enums;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class SkyObjectType
{
	/**
	 <enum name="SkyObjectType" storage="uint">
	        Skyrim, sets what sky function this object fulfills in BSSkyShaderProperty.
	        <option value="0" name="BSSM_SKY_TEXTURE">BSSM_Sky_Texture</option>
	        <option value="1" name="BSSM_SKY_SUNGLARE">BSSM_Sky_Sunglare</option>
	        <option value="2" name="BSSM_SKY">BSSM_Sky</option>
	        <option value="3" name="BSSM_SKY_CLOUDS">BSSM_Sky_Clouds</option>
	        <option value="5" name="BSSM_SKY_STARS">BSSM_Sky_Stars</option>
	        <option value="7" name="BSSM_SKY_MOON_STARS_MASK">BSSM_Sky_Moon_Stars_Mask</option>
	    </enum>
	 */

	public int value;

	public SkyObjectType(ByteBuffer stream) throws IOException
	{
		value = ByteConvert.readInt(stream);
	}

}
