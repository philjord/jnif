package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.basic.NifPtr;
import nif.niobject.NiAVObject;

public class NifAVObject
{
	/**
	 <compound name="AVObject">

	 Used in NiDefaultAVObjectPalette.
	 
	 <add name="Name" type="SizedString">Object name.</add>
	 <add name="AV Object" type="Ptr" template="NiAVObject">Object reference.</add>
	 </compound>
	 */

	public String name;

	public NifPtr object;

	public NifAVObject(InputStream stream) throws IOException
	{
		name = ByteConvert.readSizedString(stream);
		object = new NifPtr(NiAVObject.class, stream);
	}
}
