package nif.niobject.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.enums.BSLightingShaderType;
import nif.enums.BSShaderType;
import nif.niobject.NiExtraData;
import nif.niobject.NiObject;
import nif.niobject.bs.BSLightingShaderProperty;

public abstract class NiObjectNET extends NiObject
{
	/**
	  <niobject name="NiObjectNET" abstract="true" inherit="NiObject" module="NiMain">
        Abstract base class for NiObjects that support names, extra data, and time controllers.
        <field name="Shader Type" type="BSLightingShaderType" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SKY# #AND# #NI_BS_LTE_FO4#" onlyT="BSLightingShaderProperty">Configures the main shader path</field>
        <field name="Name" type="string">Name of this controllable object, used to refer to the object in .kf files.</field>
        <field name="Legacy Extra Data" type="LegacyExtraData" until="2.3" />
        <field name="Extra Data" type="Ref" template="NiExtraData" since="3.0" until="4.2.2.0">Extra data object index. (The first in a chain)</field>
        <field name="Num Extra Data List" type="uint" since="10.0.1.0">The number of Extra Data objects referenced through the list.</field>
        <field name="Extra Data List" type="Ref" template="NiExtraData" length="Num Extra Data List" since="10.0.1.0">List of extra data indices.</field>
        <field name="Controller" type="Ref" template="NiTimeController" since="3.0">Controller object index. (The first in a chain)</field>
	 */
	public String name;
	
	public NifRef extraData;

	public int numExtraDataList;

	public NifRef[] extraDataList;

	public NifRef controller;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		//<field name="Shader Type" type="BSLightingShaderType" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SKY# #AND# #NI_BS_LTE_FO4#" onlyT="BSLightingShaderProperty">Configures the main shader path</field>
		if (this instanceof BSLightingShaderProperty && nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.BS_GTE_SKY() && nifVer.NI_BS_LTE_FO4()) {
			((BSLightingShaderProperty)this).ShaderType = BSLightingShaderType.load(stream);// TODO: actually BSLightingShaderType(stream);
		}

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

	@Override
	public String toString()
	{
		return "[" + this.getClass().getSimpleName() + "] " + name;
	}

	@Override
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