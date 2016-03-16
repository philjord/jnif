package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifNodeGroup;

public class NiBoneLODController extends NiTimeController
{
	/**
	 <niobject name="NiBoneLODController" abstract="0" inherit="NiTimeController" ver1="4.2.2.0">

	 Level of detail controller for bones.  Priority is arranged from low to high.
	 
	 <add name="Unknown Int 1" type="uint">Unknown.</add>
	 <add name="Num Node Groups" type="uint">Number of node groups.</add>
	 <add name="Num Node Groups 2" type="uint">Number of node groups.</add>
	 <add name="Node Groups" type="NodeGroup" arr1="Num Node Groups">
	 A list of node groups (each group a sequence of bones).
	 </add>
	 <add name="Num Shape Groups" type="uint" ver1="4.2.2.0" userver="0">Number of shape groups.</add>
	 <add name="Shape Groups 1" type="SkinShapeGroup" arr1="Num Shape Groups" ver1="4.2.2.0" userver="0">List of shape groups.</add>
	 <add name="Num Shape Groups 2" type="uint" ver1="4.2.2.0" userver="0">The size of the second list of shape groups.</add>
	 <add name="Shape Groups 2" type="Ref" template="NiTriBasedGeom" arr1="Num Shape Groups 2" ver1="4.2.2.0" userver="0">Group of NiTriShape indices.</add>
	 </niobject>
	 */

	public int unknownInt1;

	public int numNodeGroups;

	public int numNodeGroups2;

	public NifNodeGroup[] nodeGroups;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownInt1 = ByteConvert.readInt(stream);
		numNodeGroups = ByteConvert.readInt(stream);
		numNodeGroups2 = ByteConvert.readInt(stream);

		nodeGroups = new NifNodeGroup[numNodeGroups];
		for (int i = 0; i < numNodeGroups; i++)
		{
			nodeGroups[i] = new NifNodeGroup(stream);
		}
		return success;
	}
}