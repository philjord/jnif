package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifFooter
{
	/**
	 <compound name="Footer">

	 The NIF file footer.
	 
	 <add name="Num Roots" type="uint" ver1="3.3.0.13">The number of root references.</add>
	 <add name="Roots" type="Ref" template="NiObject" arr1="Num Roots" ver1="3.3.0.13">
	 List of root NIF objects. If there is a camera, for 1st person view, then this NIF object is referred to as well in this list, 
	 even if it is not a root object(usually we want the camera to be attached to the Bip Head node).
	 </add>
	 </compound>
	 */

	/*
	 * The number of root references.
	 */
	public int numRoots = 0;

	/*!
	 * List of root blocks. If there is a camera, for 1st person view, then
	 * this block is referred to as well in this list, even if it is not a
	 * root block (usually we want the camera to be attached to the Bip Head
	 * node).
	 */
	//vector < Ref < NiAVObject > > 
	public int[] roots;

	//list < uint > & link_stack
	public boolean readFromStream(ByteBuffer stream) throws IOException
	{
		numRoots = ByteConvert.readInt(stream);
		roots = new int[numRoots];
		for (int i1 = 0; i1 < numRoots; i1++)
		{
			roots[i1] = ByteConvert.readInt(stream);
		}
		return true;
	}

	public String toString()
	{
		return toString(false);
	}

	public String toString(boolean verbose)
	{
		String out = "  Num Roots:  " + numRoots + "\n";
		if (verbose)
		{
			for (int i1 = 0; i1 < numRoots; i1++)
			{
				out += "    Roots[" + i1 + "]:  " + roots[i1] + "\n";
			}
		}
		return out;
	}
}