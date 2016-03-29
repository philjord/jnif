package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSbhkNPObject extends NiExtraData
{
	public int NumBytes;
	public String verNum;

	public byte[] Data;

//	private HKXFile hkxFile;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumBytes = ByteConvert.readInt(stream);// don't include in read off total mind you
		Data = ByteConvert.readBytes(NumBytes, stream);

/*		ByteBuffer bb = ByteBuffer.wrap(Data);
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		HKXReader reader = new HKXReader(bb, descriptorFactory, enumResolver);
		try
		{
			hkxFile = reader.read();
		}
		catch (InvalidPositionException e)
		{
			e.printStackTrace();
		}*/

		return success;
	}

}
