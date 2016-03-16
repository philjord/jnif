package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class bhkSimpleShapePhantom extends bhkShapePhantom
{
	/*
	 
	Unknown shape.
	Attributes
	Name 	Type 	Arg 	T 	Arr1 	Arr2 	Cond 	Func 	Default 	Description
	Unkown Floats 	float 			7 					Unknown.
	Unknown Floats 2 	float 			3 	5 				Unknown. (1,0,0,0,0) x 3.
	Unknown Float 	float 								Unknown.
	*/

	public float[] unknownFloats;

	public float[] unknownFloats2;

	public float unknownFloat;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloats = ByteConvert.readFloats(7, stream);
		unknownFloats = ByteConvert.readFloats(15, stream); //note 5*3 = 15
		unknownFloat = ByteConvert.readFloat(stream);
		return success;
	}
}