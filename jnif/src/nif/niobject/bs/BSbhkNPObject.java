package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;
import nif.niobject.hkx.reader.HKXContents;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.InvalidPositionException;

public class BSbhkNPObject extends NiExtraData
{
	public int NumBytes;

	public HKXContents hkxContents;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumBytes = ByteConvert.readInt(stream);// don't include in read off total mind you
		ByteBuffer bb = ByteBuffer.wrap(ByteConvert.readBytes(NumBytes, stream));
		bb.order(ByteOrder.LITTLE_ENDIAN);
		
		HKXReader reader = new HKXReader(bb);
		try
		{
			hkxContents = reader.read();
		}
		catch (InvalidPositionException e)
		{
			e.printStackTrace();
		}

		return success;
	}

}
