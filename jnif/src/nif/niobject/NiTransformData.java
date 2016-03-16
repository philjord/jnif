package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiTransformData extends NiKeyframeData
{
	/**
	 
	 <niobject name="NiTransformData" abstract="0" inherit="NiKeyframeData" ver1="10.2.0.0">
	 Mesh animation keyframe data.
	 </niobject>
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}