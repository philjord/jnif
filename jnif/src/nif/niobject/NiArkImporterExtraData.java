package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiArkImporterExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiArkImporterExtraData" inherit="NiExtraData">

	 Unknown node.
	 
	 <add name="Unknown Int 1" type="int"/>
	 <add name="Unknown Int 2" type="int" ver2="4.1.0.12"/>
	 <add name="Importer Name" type="string">
	 Contains a string like "Gamebryo_1_1" or "4.1.0.12"
	 </add>
	 <add name="Unknown Bytes" type="byte" arr1="13"/>
	 <add name="Unknown Floats" type="float" arr1="7"/>
	 </niobject>
	 */

	public int unknownInt1;

	public String importerName;

	public byte[] unknownBytes;

	public float[] unknownFloats;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);
		importerName = ByteConvert.readSizedString(stream);
		unknownBytes = ByteConvert.readBytes(13, stream);
		unknownFloats = ByteConvert.readFloats(7, stream);

		return success;
	}
}