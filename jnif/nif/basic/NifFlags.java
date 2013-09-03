package nif.basic;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifFlags
{
	/**
	 * <basic name="Flags" count="0" niflibtype="unsigned short" nifskopetype="flags">

	 A 16-bit integer, used for bit flags.  Function varies by object type.
	 
	 </basic>
	 */

	public short flags = -1;

	public NifFlags(InputStream stream) throws IOException
	{
		flags = ByteConvert.readShort(stream);
	}

}
