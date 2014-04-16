package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSSegmentedTriangle
{
	/**
	 
	 <compound name="BSSegmentedTriangle">

	 Bethesda-specific node.
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 </compound>
	 */

	public int unknownInt1;

	public int unknownInt2;

	public byte unknownByte1;

	public BSSegmentedTriangle(InputStream stream) throws IOException
	{
		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);

		unknownByte1 = ByteConvert.readByte(stream);

	}
}