package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector4;

public class NiRoom extends NiNode
{
	/**
	 
	 <niobject name="NiRoom" inherit="NiNode">

	 Grouping node for nodes in a Portal
	 
	 <add name="Num Walls" type="int">Number of walls in a room?</add>
	 <add name="Wall Plane" type="Vector4" arr1="Num Walls">Face normal and unknown value.</add>
	 <add name="Num In Portals" type="int">Number of doors into room</add>
	 <add name="In Portals" type="Ptr" template="NiPortal" arr1="Num In Portals">Number of portals into room</add>
	 <add name="Num Portals 2" type="int">Number of doors out of room</add>
	 <add name="Portals 2" type="Ptr" template="NiPortal" arr1="Num Portals 2">Number of portals out of room</add>
	 <add name="Num Items" type="int">Number of unknowns</add>
	 <add name="Items" type="Ptr" template="NiAVObject" arr1="Num Items">All geometry associated with room.</add>
	 </niobject>
	 
	 */

	public int numWalls;

	public NifVector4[] wallPlane;

	public int numInPortals;

	public NifPtr[] inPortals;

	public int numInPortals2;

	public NifPtr[] portals2;

	public int numItems;

	public NifPtr[] items;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numWalls = ByteConvert.readInt(stream);
		wallPlane = new NifVector4[numWalls];
		for (int i = 0; i < numWalls; i++)
		{
			wallPlane[i] = new NifVector4(stream);
		}
		numInPortals = ByteConvert.readInt(stream);
		inPortals = new NifPtr[numInPortals];
		for (int i = 0; i < numInPortals; i++)
		{
			inPortals[i] = new NifPtr(NiPortal.class, stream);
		}
		numInPortals2 = ByteConvert.readInt(stream);
		portals2 = new NifPtr[numInPortals2];
		for (int i = 0; i < numInPortals2; i++)
		{
			portals2[i] = new NifPtr(NiPortal.class, stream);
		}
		numItems = ByteConvert.readInt(stream);
		items = new NifPtr[numItems];
		for (int i = 0; i < numItems; i++)
		{
			items[i] = new NifPtr(NiAVObject.class, stream);
		}

		return success;
	}
}