package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.SortingMode;

public class NiSortAdjustNode extends NiNode
{
	/**
	 <niobject name="NiSortAdjustNode" abstract="0" inherit="NiNode" ver1="10.2.0.0">

	 Unknown node.  Found in Loki.
	 
	 <add name="Sorting Mode" type="SortingMode" default="SORTING_INHERIT">Sorting</add>
	 <add name="Unknown Int 2" type="int" default="-1" ver2="10.2.0.0">Unknown.</add>
	 </niobject>
	 */

	public SortingMode sortingMode;

	public int unknownInt2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		sortingMode = new SortingMode(stream);

		if (nifVer.LOAD_VER <= NifVer.VER_10_2_0_0)
		{
			unknownInt2 = ByteConvert.readInt(stream);
		}

		return success;
	}
}