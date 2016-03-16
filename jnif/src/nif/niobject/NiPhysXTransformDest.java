package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;

public class NiPhysXTransformDest extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXTransformDest" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Byte 1" type="byte">Unknown. =1?</add>
	 <add name="Unknown Byte 2" type="byte">Unknown. =0</add>
	 <add name="Node" type="Ptr" template="NiNode">Affected node?</add>
	 </niobject>
	 */

	public byte unknownByte1;

	public byte unknownByte2;

	public NifPtr node;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownByte1 = ByteConvert.readByte(stream);
		unknownByte2 = ByteConvert.readByte(stream);

		node = new NifPtr(NiNode.class, stream);

		return success;
	}
}