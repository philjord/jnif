package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifFurniturePosition
{
	/**
	 

	<compound name="FurniturePosition">
	    Describes a furniture position?
	    <add name="Offset" type="Vector3">Offset of furniture marker.</add>
	    <add name="Orientation" type="ushort" vercond="User Version &lt;= 11">Furniture marker orientation.</add>
	    <add name="Position Ref 1" type="byte" vercond="User Version &lt;= 11">Refers to a furnituremarkerxx.nif file. Always seems to be the same as Position Ref 2.</add>
	    <add name="Position Ref 2" type="byte" vercond="User Version &lt;= 11">Refers to a furnituremarkerxx.nif file. Always seems to be the same as Position Ref 1.</add>
	    <add name="Heading" type="float" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Similar to Orientation, in float form.</add>
	    <add name="Animation Type" type="ushort" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Entry Properties" type="ushort" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown/unused in nif?</add>
	</compound>
	 */

	public NifVector3 offset;

	public short orientation;

	public byte positionRef1;

	public byte positionRef2;

	public float Heading;

	public short AnimationType;

	public short EntryProperties;

	public NifFurniturePosition(InputStream stream, NifVer nifVer) throws IOException
	{

		offset = new NifVector3(stream);

		if (nifVer.LOAD_USER_VER <= 11)
		{
			orientation = ByteConvert.readShort(stream);
			positionRef1 = ByteConvert.readByte(stream);
			positionRef2 = ByteConvert.readByte(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			Heading = ByteConvert.readFloat(stream);
			AnimationType = ByteConvert.readShort(stream);
			EntryProperties = ByteConvert.readShort(stream);
		}

	}
}
