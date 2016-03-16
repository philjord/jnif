package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPhysXMaterialDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXMaterialDesc" abstract="0" inherit="NiObject" ver1="20.1.0.7">

	 Unknown node.
	 
	 <add name="Unknown Int" type="uint" arr1="12">Unknown</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 <add name="Unknown Byte 2" type="byte">Unknown</add>
	 </niobject>
	 */

	public int unknownInt;

	public byte unknownByte1;

	public byte unknownByte2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt = ByteConvert.readInt(stream);
		unknownByte1 = ByteConvert.readByte(stream);
		unknownByte2 = ByteConvert.readByte(stream);

		return success;
	}
}