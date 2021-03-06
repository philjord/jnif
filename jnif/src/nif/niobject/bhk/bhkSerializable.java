package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class bhkSerializable extends bhkRefObject
{
	/*
	 
	Havok objects that can be saved and loaded from disk?
	 
	Attributes
	Name 	Type 	Arg 	T 	Arr1 	Arr2 	Cond 	Func 	Default 	Description
		
	*/

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}