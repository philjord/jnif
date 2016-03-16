package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSWArray extends NiExtraData
{
	/**
	 
	 <niobject name="BSWArray" inherit="NiExtraData">

	 Bethesda-specific node.
	 
	 <add name="Num Items" type="int">Unknown</add>
	 <add name="Items" type="int" arr1="Num Items">Unknown</add>
	 </niobject>
	 
	 */

	public int numItems;

	public int[] items;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numItems = ByteConvert.readInt(stream);
		items = new int[numItems];
		for (int i = 0; i < numItems; i++)
		{
			items[i] = ByteConvert.readInt(stream);
		}

		return success;
	}
}