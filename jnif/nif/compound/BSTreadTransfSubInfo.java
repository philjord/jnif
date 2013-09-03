package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSTreadTransfSubInfo
{
	/**
	 <compound name="BSTreadTransfSubInfo">

	 Bethesda-specific node.
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Int 3" type="int">Unknown</add>
	 <add name="Unknown Int 4" type="int">Unknown</add>
	 <add name="Unknown Int 5" type="int">Unknown</add>
	 <add name="Unknown Int 6" type="int">Unknown</add>
	 <add name="Unknown Int 7" type="int">Unknown</add>
	 <add name="Unknown Int 8" type="int">Unknown</add>
	 </compound>	
	 */

	public int unknownInt1;

	public int unknownInt2;

	public int unknownInt3;

	public int unknownInt4;

	public int unknownInt5;

	public int unknownInt6;

	public int unknownInt7;

	public int unknownInt8;

	public BSTreadTransfSubInfo(InputStream stream) throws IOException
	{

		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownInt3 = ByteConvert.readInt(stream);
		unknownInt4 = ByteConvert.readInt(stream);
		unknownInt5 = ByteConvert.readInt(stream);
		unknownInt6 = ByteConvert.readInt(stream);
		unknownInt7 = ByteConvert.readInt(stream);
		unknownInt8 = ByteConvert.readInt(stream);
	}
}
