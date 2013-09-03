package nif.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import nif.ByteConvert;

public class NifPtr
{

	/**
	 * <basic name="Ptr" count="0" niflibtype="*" nifskopetype="uplink" istemplate="1">

	 A signed 32-bit integer, referring to a object before this one in the hierarchy.  Examples:  Bones, gravity objects.
	 
	 </basic>
	 */
	public static Vector<NifPtr> allPtrs = new Vector<NifPtr>();

	public int ptr = -1;

	public Class<?> ptrType;

	public NifPtr(Class<?> ptrType, InputStream stream) throws IOException
	{
		this.ptrType = ptrType;
		ptr = ByteConvert.readInt(stream);
		allPtrs.add(this);
	}

	public String toString()
	{
		return "[NPPtr] " + ptr + " " + ptrType;
	}

}
