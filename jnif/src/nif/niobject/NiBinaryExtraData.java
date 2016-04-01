package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.compound.NifByteArray;

public class NiBinaryExtraData extends NiExtraData
{
	/**
	 <niobject name="NiBinaryExtraData" abstract="0" inherit="NiExtraData" ver1="10.1.0.0">

	 Binary extra data object. Used to store normals and binormals in Oblivion.
	 
	 <add name="Binary Data" type="ByteArray">The binary data.</add>
	 </niobject>
	 */

	
	//Note once this is converted to somethign else, it will be set to null!
	public NifByteArray binaryData;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		binaryData = new NifByteArray(stream);
		return success;
	}
}