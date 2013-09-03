package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;

public class NiRoomGroup extends NiNode
{
	/**
	 
	 <niobject name="NiRoomGroup" inherit="NiNode">

	 Grouping node for nodes in a Portal
	 
	 <add name="Shell Link" type="Ptr" template="NiNode">Outer Shell Geometry Node?</add>
	 <add name="Num Rooms" type="int">Number of rooms in this group</add>
	 <add name="Rooms" type="Ptr" template="NiRoom" arr1="Num Rooms">Rooms associated with this group.</add>
	 </niobject>
	 
	 */

	public NifPtr shellLink;

	public int numRooms;

	public NifPtr[] rooms;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		shellLink = new NifPtr(NiNode.class, stream);
		numRooms = ByteConvert.readInt(stream);
		rooms = new NifPtr[numRooms];
		for (int i = 0; i < numRooms; i++)
		{
			rooms[i] = new NifPtr(NiRoom.class, stream);
		}

		return success;
	}
}