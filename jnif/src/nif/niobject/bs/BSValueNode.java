package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiNode;

public class BSValueNode extends NiNode
{
	/**
	 
	 <niobject name="BSValueNode" inherit="NiNode" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific node. Found on fxFire effects
	 
	 <add name="Value" type="int">Value</add>
	 <add name="Unknown byte" type="byte">Unknown</add>
	 </niobject>
	 */

	public int value;

	public byte unknownByte;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		value = ByteConvert.readInt(stream);
		unknownByte = ByteConvert.readByte(stream);

		return success;
	}
}