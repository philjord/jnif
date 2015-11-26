package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSPackedCombinedSharedGeomDataExtra extends BSExtraData
{
	public int NumBytes;
	public byte[] Data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		//NumBytes = ByteConvert.readInt(stream);
		//Data = ByteConvert.readBytes(NumBytes, stream);
		System.out.println("" + ByteConvert.readByte(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		System.out.println("" + ByteConvert.readInt(stream));
		//	byte[] buf = new byte[400];
		//	stream.read(buf);
		//	System.out.println("" + new String(buf));
		return success;
	}
}
