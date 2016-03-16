package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifFilePath;

public class NiImage extends NiObject
{
	/**
	 <niobject name="NiImage" abstract="0" inherit="NiObject" ver1="3.0" ver2="3.1">
	 <add name="External" type="byte">0 if the texture is internal to the NIF file.</add>
	 <add name="File Name" type="FilePath" cond="External != 0">The filepath to the texture.</add>
	 <add name="Image Data" type="Ref" template="NiRawImageData" cond="External == 0">Link to the internally stored image data.</add>
	 <add name="Unknown Int" type="uint" default="7">
	 Unknown.  Often seems to be 7. Perhaps m_uiMipLevels?
	 </add>
	 <add name="Unknown Float" type="float" ver1="3.1" default="128.5">Unknown.  Perhaps fImageScale?</add>
	 </niobject>	
	 */

	public byte external;

	public NifFilePath fileName;

	public NifRef imageData;

	public int unknownInt1;

	public float unknownFloat;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		external = ByteConvert.readByte(stream);
		if (external != 0)
			fileName = new NifFilePath(stream, nifVer);
		else
			imageData = new NifRef(NiRawImageData.class, stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownFloat = ByteConvert.readFloat(stream);

		return success;
	}
}