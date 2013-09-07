package nif.niobject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nif.ByteConvert;
import nif.NifVer;

public class NiSwitchNode extends NiNode
{
	/**
	 
	 <niobject name="NiSwitchNode" abstract="0" inherit="NiNode">

	 A node used to switch between branches, such as for LOD levels?
	 
	 <add name="Unknown Flags 1" type="ushort" ver1="10.1.0.0">Flags</add>
	 <add name="Unknown Int 1" type="int">Index?</add>
	 </niobject> 
	 
	 */

	public short unknownFlags1;

	public int unknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownFlags1 = ByteConvert.readShort(stream);
		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		super.addDisplayRows(list);

		list.add(new Object[]
		{ "NiSwitchNode", "unknownFlags1", "" + unknownFlags1 });

		list.add(new Object[]
		{ "NiSwitchNode", "unknownInt1", "" + unknownInt1 });

	}
}