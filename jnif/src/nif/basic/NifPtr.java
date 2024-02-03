package nif.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

import nif.ByteConvert;
import nif.NifFileReader;

public class NifPtr
{

	/**
	 * <basic name="Ptr" count="0" niflibtype="*" nifskopetype="uplink" istemplate="1">
	
	 A signed 32-bit integer, referring to a object before this one in the hierarchy.  Examples:  Bones, gravity objects.
	 
	 </basic>
	 */
	public static Vector<NifPtr> allPtrs = new Vector<NifPtr>();

	public static int maxRefId = Integer.MAX_VALUE;

	public int ptr = -1;

	public Class<?> ptrType;

	public NifPtr(Class<?> ptrType, ByteBuffer stream) throws IOException
	{
		this.ptrType = ptrType;
		ptr = ByteConvert.readInt(stream);
		
		if(NifFileReader.REVIEW_REFS_POST_LOAD) {
			allPtrs.add(this);
			if (ptr < -1 || ptr > maxRefId)
			{
				throw new RuntimeException("Bad ptr value " + ptr);
			}
		}
	}

	@Override
	public String toString()
	{
		return "[NPPtr] " + ptr + " " + ptrType;
	}

}
