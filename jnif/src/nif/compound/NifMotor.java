package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifMotor
{
	public float unknownFloat1;

	public float unknownFloat2;

	public float unknownFloat3;

	public float unknownFloat4;

	public float unknownFloat5;

	public float unknownFloat6;

	public NifMotor(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		//TODO: not like nif.xml empirical - tell jonwd7 see F:\game_media\Fallout3\meshes\dlc04\creatures\hillfolk1\skeleton.nif
		boolean readMore = ByteConvert.readBool(stream, nifVer);
		if (readMore)
		{
			unknownFloat3 = ByteConvert.readFloat(stream);
			unknownFloat4 = ByteConvert.readFloat(stream);
		}
		unknownFloat5 = ByteConvert.readFloat(stream);
		unknownFloat6 = ByteConvert.readFloat(stream);
	}
}
