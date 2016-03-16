package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiDirectionalLight extends NiLight
{
	/**
	 <niobject name="NiDirectionalLight" abstract="0" inherit="NiLight">
	 Directional light source.
	 </niobject>
	 */
	public byte unknownByte;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.isBP() && nifVer.LOAD_USER_VER != 9)
		{
			unknownByte = ByteConvert.readByte(stream);
		}

		return success;
	}
}