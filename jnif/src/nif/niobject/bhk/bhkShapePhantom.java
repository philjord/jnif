package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public abstract class bhkShapePhantom extends bhkWorldObject
{
	/*
	 
	A Havok phantom that uses a Havok shape object for its collision volume instead of just a bounding box.
	
	Attributes
	Name 	Type 	Arg 	T 	Arr1 	Arr2 	Cond 	Func 	Default 	Description 	From	To
		
	*/

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}