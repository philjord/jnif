package nif.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

import nif.ByteConvert;
import nif.NifFileReader;

public class NifRef
{
	

	/**
	 * <basic name="Ref" count="0" niflibtype="Ref" nifskopetype="link" istemplate="1">
	
	 A signed 32-bit integer, used to refer to another object; -1 means no reference. These should always point down the hierarchy. 
	 Other types are used for indexes that point to objects higher up.
	 
	 </basic>
	 */
	//all refs for checking post load	
	public static Vector<NifRef> allRefs = new Vector<NifRef>();
	public static int maxRefId = Integer.MAX_VALUE;

	public int ref = -1;

	public Class<?> refType;

	public NifRef(Class<?> refType, ByteBuffer stream) throws IOException
	{
		this.refType = refType;
		ref = ByteConvert.readInt(stream);

		// really basic checks TODO: reenable after decode FO4
		//		if (ref < -1 || ref > 20000)
		//			new Throwable("Bad ref " + ref + " " + refType).printStackTrace();

		if(NifFileReader.REVIEW_REFS_POST_LOAD) {
			allRefs.add(this);
			if (ref < -1 || ref > maxRefId)
			{
				throw new RuntimeException("Bad ref value " + ref);
			}
		}
	}

	@Override
	public String toString()
	{
		return "[NifRef] " + ref + " " + refType;
	}
}
