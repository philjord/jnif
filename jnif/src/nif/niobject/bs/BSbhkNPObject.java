package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;
 

public class BSbhkNPObject extends NiExtraData
{
	public int NumBytes;
	public String verNum;

	public byte[] Data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		NumBytes = ByteConvert.readInt(stream);// don't include in read off total mind you
		
		//string of versionNum
		ByteConvert.readShorts(10, stream);
		ByteConvert.readInts(5, stream);

		
		Data = ByteConvert.readBytes(256, stream);

		System.out.println("" + new String(Data));
		
		verNum = new String(ByteConvert.readBytes(14, stream));
		System.out.println("verNum " + verNum + " " + nifVer.fileName);

		ByteConvert.readBytes(10, stream);

		//BSClothExtraData only
		if (this instanceof BSClothExtraData)
			ByteConvert.readBytes(16, stream);

		//64
		String temp;
		//__classnames__
		temp = new String(ByteConvert.readBytes(18, stream));//0 padded to 18
		if (!temp.trim().equals("__classnames__"))
			System.out.println("Not __classnames__! " + temp + " " + this + " " + nifVer.fileName);

		ByteConvert.readInt(stream);
		ByteConvert.readBytes(2, stream);

		ByteConvert.readInts(6, stream);//identical values?

		ByteConvert.readBytes(16, stream);//identical values? ÿ		
		//64		

		//__types__
		temp = new String(ByteConvert.readBytes(18, stream));//0 padded to 18
		if (!temp.trim().equals("__types__"))
			System.out.println("Not __types__! " + temp + " " + this + " " + nifVer.fileName);

		ByteConvert.readInt(stream);
		ByteConvert.readBytes(2, stream);

		ByteConvert.readInts(6, stream);//identical values? blanks

		ByteConvert.readBytes(16, stream);//identical values? ÿ

		//64		

		//__data__
		temp = new String(ByteConvert.readBytes(18, stream));//0 padded to 18
		if (!temp.trim().equals("__data__"))
			System.out.println("Not __data__! " + temp + " " + this + " " + nifVer.fileName);

		ByteConvert.readInt(stream);
		ByteConvert.readBytes(2, stream);

		ByteConvert.readInts(6, stream);//identical values? blanks

		ByteConvert.readBytes(16, stream);//identical values? ÿ

		//64

		//now a bunch of int tab name where name is zero terminated? 8 names total

		//total 226	
		ByteConvert.readShort(stream);
		ByteConvert.readShort(stream);

		//DUMP this!
		temp = new String(ByteConvert.readBytes(1, stream));
		if (!temp.equals("\t"))
			System.out.println("Not tab! " + temp);

		//hkClass
		if (this instanceof BSClothExtraData)
			Data = ByteConvert.readBytes(NumBytes - 272-5, stream);
		else
			Data = ByteConvert.readBytes(NumBytes - 256-5, stream);

		//System.out.println("" + new String(Data));

		//ByteConvert.readBytes(NumBytes - 44, stream);

		return success;
	}

}
