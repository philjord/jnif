package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.enums.OblivionLayer;

public class NifOblivionColFilter
{
	/**
	 <compound name="OblivionColFilter">

	 Oblivion's ColFilter property for Havok.
	 
	 <add name="Layer" type="OblivionLayer">Sets mesh color in Oblivion Construction Set.</add>
	 <add name="Col Filter" type="byte">
	 The first bit sets the LINK property and controls whether this body is physically linked to others. The next bit turns collision off. 
	 Then, the next bit sets the SCALED property in Oblivion. The next five bits make up the number of this part in a linked body list.
	 </add>
	 <add name="Unknown Short" type="ushort">Unknown.</add>
	 </compound> 				
	 */

	public OblivionLayer layer;

	public byte colFilter;

	public short unknownShort;

	public NifOblivionColFilter(InputStream stream) throws IOException
	{
		layer = new OblivionLayer(stream);
		colFilter = ByteConvert.readByte(stream);
		unknownShort = ByteConvert.readShort(stream);
	}
}
