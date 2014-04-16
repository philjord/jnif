package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.enums.ChannelConvention;
import nif.enums.ChannelType;

public class NifChannelData
{
	/**
	 <compound name="ChannelData">

	 Channel data
	 
	 <add name="Type" type="ChannelType">Channel Type</add>
	 <add name="Convention" type="ChannelConvention">Data Storage Convention</add>
	 <add name="Bits Per Channel" type="byte">Bits per channel</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 </compound>
	 */

	public ChannelType type;

	public ChannelConvention convention;

	public byte bitsPerChannel;

	public byte unknownByte1;

	public NifChannelData(InputStream stream) throws IOException
	{
		type = new ChannelType(stream);
		convention = new ChannelConvention(stream);
		bitsPerChannel = ByteConvert.readByte(stream);
		unknownByte1 = ByteConvert.readByte(stream);

	}
}
