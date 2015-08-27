package nif.niobject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiExtraData;
import nif.niobject.NiObject;

public abstract class NiObjectNET extends NiObject
{
	/**
	 <niobject name="NiObjectNET" abstract="1" inherit="NiObject">

	 An object that can be controlled by a controller.
	 
	 <add name="Name" type="string">
	 Name of this controllable object, used to refer to the object in .kf files.
	 </add>
	 <add name="Has Old Extra Data" type="uint" ver2="2.3">Extra data for pre-3.0 versions.</add>
	 <add name="Old Extra Prop Name" cond="Has Old Extra Data" ver2="2.3" type="string">(=NiStringExtraData)</add>
	 <add name="Old Extra Internal Id" cond="Has Old Extra Data" ver2="2.3" type="uint">ref</add>
	 <add name="Old Extra String" cond="Has Old Extra Data" ver2="2.3" type="string">Extra string data.</add>
	 <add name="Unknown Byte" type="byte" ver2="2.3">Always 0.</add>
	 <add name="Extra Data" type="Ref" template="NiExtraData" ver1="3.0" ver2="4.2.2.0">Extra data object index. (The first in a chain)</add>
	 <add name="Num Extra Data List" type="uint" ver1="10.0.1.0">
	 The number of Extra Data objects referenced through the list.
	 </add>
	 <add name="Extra Data List" type="Ref" template="NiExtraData" arr1="Num Extra Data List" ver1="10.0.1.0">List of extra data indices.</add>
	 <add name="Controller" type="Ref" template="NiTimeController" ver1="3.0">Controller object index. (The first in a chain)</add>
	 </niobject>
	 */
	public String name;
	
	public NifRef extraData;

	public int numExtraDataList;

	public NifRef[] extraDataList;

	public NifRef controller;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		name = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			extraData = new NifRef(NiExtraData.class, stream);
		}
		
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			numExtraDataList = ByteConvert.readInt(stream);

			extraDataList = new NifRef[numExtraDataList];
			for (int i = 0; i < numExtraDataList; i++)
			{
				extraDataList[i] = new NifRef(NiExtraData.class, stream);
			}
		}
		controller = new NifRef(NiTimeController.class, stream);

		return success;
	}

	public String toString()
	{
		return "[" + this.getClass().getSimpleName() + "] " + name;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		super.addDisplayRows(list);

		list.add(new Object[]
		{ "NiObjectNET", "name", "" + name });
		list.add(new Object[]
		{ "NiObjectNET", "numExtraDataList", "" + numExtraDataList });
		list.add(new Object[]
		{ "NiObjectNET", "extraDataList", "ArrayOf NifRef " + extraDataList.length });
		list.add(new Object[]
		{ "NiObjectNET", "controller", "" + controller });
	}

}