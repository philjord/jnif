package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiArkShaderExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiArkShaderExtraData" inherit="NiExtraData">

	 Unknown node.
	 
	 <add name="Unknown Int" type="int"/>
	 <add name="Unknown String" type="string"/>
	 </niobject>
	 */

	public int unknownInt;

	public String unknownString;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt = ByteConvert.readInt(stream);
		unknownString = ByteConvert.readSizedString(stream);

		return success;
	}
}