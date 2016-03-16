package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.BillboardMode;

public class NiBillboardNode extends NiNode
{
	/**
	 
	 <niobject name="NiBillboardNode" abstract="0" inherit="NiNode">

	 These nodes will always be rotated to face the camera creating a billboard effect for any attached objects.

	 In pre-10.1.0.0 the Flags field is used for BillboardMode.
Bit 0: hidden
Bits 1-2: collision mode
Bit 3: unknown (set in most official meshes)
Bits 5-6: billboard mode

Collision modes:
00 NONE
01 USE_TRIANGLES
10 USE_OBBS
11 CONTINUE

Billboard modes:
00 ALWAYS_FACE_CAMERA
01 ROTATE_ABOUT_UP
10 RIGID_FACE_CAMERA
11 ALWAYS_FACE_CENTER 
	 <add name="Billboard Mode" type="BillboardMode" ver1="10.1.0.0">The way the billboard will react to the camera.</add>
	 </niobject>
	 */

	public BillboardMode billboardMode;

	public byte unknownByte;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			billboardMode = new BillboardMode(stream);
		}
		

		if (nifVer.isBP())
		{
			unknownByte = ByteConvert.readByte(stream);
		}

		return success;
	}
}