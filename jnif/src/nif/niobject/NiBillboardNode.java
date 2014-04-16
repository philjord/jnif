package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.BillboardMode;

public class NiBillboardNode extends NiNode
{
	/**
	 
	 <niobject name="NiBillboardNode" abstract="0" inherit="NiNode">

	 These nodes will always be rotated to face the camera creating a billboard effect for any attached objects.

	 In pre-10.1.0.0 the Flags field is used for BillboardMode.
	 Here is what alphax says about these Flags after checking SceneImmerse:

	 0x0000 - 0x0010 is ALWAYS_FACE_CAMERA (somewhat misleadingly named, but that is what the format calls it),
	 0x0020 is ROTATE_ABOUT_UP,
	 0x0040 is RIGID_FACE_CAMERA (always face the viewport), and
	 0x0060 is ALWAYS_FACE_CENTER.
	 That is where "left shift the corresponding value from v10 NIFs 5 bits" falls down - I do not know if v4 NIFs support the others, 
	 or if they actually mean anything, but these seem the most useful. The rotation of the node does seem to affect the axis of constraint.
	 
	 <add name="Billboard Mode" type="BillboardMode" ver1="10.1.0.0">The way the billboard will react to the camera.</add>
	 </niobject>
	 */

	public BillboardMode billboardMode;

	public byte unknownByte;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		billboardMode = new BillboardMode(stream);

		if (nifVer.LOAD_VER == NifVer.VER_20_3_0_9 && nifVer.LOAD_USER_VER > 10)
		{
			unknownByte = ByteConvert.readByte(stream);
		}

		return success;
	}
}