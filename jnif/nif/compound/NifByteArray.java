package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifByteArray
{
	/*
	An array of bytes.
	Implementation
	
	* NifSkope: bytearray
	
	Attribute of...
	
	* NiBinaryExtraData
	* NiPixelData
	
	Attributes
	Name 	Type 	Arg 	T 	Arr1 	Arr2 	Cond 	Func 	Default 	Description
	Data Size 	uint 								The number of bytes in this array
	Data 	byte 			Data Size 					The bytes which make up the array
	*/

	public int dataSize;

	public byte[] data;

	public NifByteArray(InputStream stream) throws IOException
	{
		dataSize = ByteConvert.readInt(stream);
		data = ByteConvert.readBytes(dataSize, stream);
	}
}
