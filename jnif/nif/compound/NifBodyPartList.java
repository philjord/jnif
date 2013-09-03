package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifBodyPartList
{
	/**
	 <compound name="BodyPartList">

	 Body part list for DismemberSkinInstance
	 
	 <add name="Unknown Short" type="short">Unknown</add>
	 <add name="Body Part" type="short">Unknown</add>
	 </compound>
	 */

	public short unknownShort;

	public short bodyPart;

	public NifBodyPartList(InputStream stream) throws IOException
	{
		unknownShort = ByteConvert.readShort(stream);
		bodyPart = ByteConvert.readShort(stream);
	}

}
